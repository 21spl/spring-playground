# Lab 17 - JPA With Hibernate

Note: This lab uses plain JPA with Hibernate as the JPA provider. It does not use Spring Boot or Spring Data JPA.

### Objective

In this lab, you'll configure Hibernate from scratch, bootstrap JPA, define an entity, and explore the complete lifecycle of a persistent object using plain JPA.

### Setup - pom.xml


```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	
	<groupId>com.example</groupId>
	<artifactId>jpademo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>jpademo</name>


	<properties>
		<maven.compiler.source>21</maven.compiler.source>
		<maven.compiler.target>21</maven.compiler.target>
	</properties>

	<dependencies>
		
		<!-- Plain JPA and Hibernate implementation-->
		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>6.6.1.Final</version>
		</dependency>

		<!-- PostgreSQL Database driver-->
		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<version>42.7.5</version>
		</dependency>

		<!-- Standard JUnit5 Engine for our @Test class-->
		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<version>5.11.4</version>
			<scope>test</scope>
		</dependency>

		
	</dependencies>


	<build>
		<plugins>
			<!--Standard Maven Plugin to run JUnit test-->
			<plugin>
				<groupId>org.maven.plugins</groupId>
				<artifactId>maven-surefire-plugin</artifactId>
				<version>3.5.2</version>
			</plugin>
		</plugins>
	</build>

</project>
```

### Task 1
- Write the minimal persistence.xml that configures a persistence unit named 'jpademo' backed by hibernate, postgresql database. 
- Enable SQL logging and create-drop DDL mode


```xml
<?xml version="1.0" encoding="UTF-8"?>
<persistence
    xmlns="https://jakarta.ee/xml/ns/persistence"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
    xsi:schemaLocation="https://jakarta.ee/xml/ns/persistence
                        https://jakarta.ee/xml/ns/persistence/persistence_3_0.xsd"
    version="3.0">

    <!--1. persistence unit name-->
    <persistence-unit name="jpademo">
        <!--2. Mention the provider-->
        <provider>org.hibernate.jpa.HibernatePersistenceProvider</provider>

        <properties>
            <property name="jakarta.persistence.jdbc.driver" value="org.postgresql.Driver"/>
            <property name="jakarta.persistence.jdbc.url" value="jdbc:postgresql://localhost:5432/learnjpa"/>
            <property name="jakarta.persistence.jdbc.user" value="wrik"/>
            <property name="jakarta.persistence.jdbc.password" value="Guitar$$2124"/>

            <!--Choose dialect-->
            <property name="hibernate.dialect" value="org.hibernate.dialect.PostgreSQLDialect"/>
            <!--show the SQL-->
            <property name="hibernate.show_sql" value="true"/>
            <property name="hibernate.format_sql" value="true"/>

            <!--Drop and Recreate table on re-run-->
            <property name="hibernate.hbm2ddl.auto" value="create"/>
        </properties>
    </persistence-unit>
</persistence>
```

---

### Task 2
- Create a JUnit 5 test class that crates an `EntityManagerFactory` from the jpademo persistence unit @BeforeAll and closes it in @AfterAll.
- Assert that the factory is not null and is open

```java
public class JPATest{

    // One factory for all the tests in this class
    static EntityManagerFactory emf;


    @BeforeAll
    static void setup(){
        // reads resources/META-INF/persistence.xml and finds the jpademo persistence unit
        // Initializes hibernate, creates connection pool and runs DDL
        emf = Persistence.createEntityManagerFactory("jpademo");
    }

    @AfterAll
    static void teardown(){
        // RUNS DROP table (because create-drop), then closes connection pool
        if(emf!=null) emf.close();
    }


    @Test
    void factoryIsOpen(){
        assertNotNull(emf);
        assertTrue(emf.isOpen());
    }

    @Test
    void entityManagerCanBeCreated(){
        // EntityManager is cheap, we can create one entitymanager per test/request
        EntityManager em = emf.createEntityManager();
        assertNotNull(em);
        assertTrue(emf.isOpen());
        // Now close the entity manager
        em.close();
    }
}
```

---

### Task 3
Write a Message Entity class. It needs an id and a text field. Make sure it satisfies all the conditions of a JPA Entity.

```java
import jakarta.persistence.*;

@Entity
@Table(name="MESSAGE")
public class Message{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TEXT")
    private String text;

    // no-arg constructor
    public Message(){}

    // arg constructor for convenience
    public Message(String text){
        this.text = text;
    }

    // getters
    public Long getId(){
        return this.id;
    }

    public String getText(){
        return this.text;
    }

    // setters - id can't have setter
    public void setText(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", text=" + text + "]";
    } 
}
```

### Task 4
Understand all GenerationType options - write them in comments

```txt
Understanding other GenerationType options

1. IDENTITY - DB column is autoincremented

- best for mysql, H2 db
- DB generates the ID on INSERT, hibernate reads it back

2. SEQUENCE - DB Sequence object

- best for postgresql, oracle
- hibernate calls nextVal('seq') before INSERT

3. TABLE - a dedicated table stores the next ID value
- maximum portability - works for any db
- bad performance - extra db read

4. AUTO - hibernate autopicks the best strategy for dialect
- good for prototyping
- risk - may differ across databases
```
### Task 5

- In a single test, persist a Message, commit the transaction.
- Then open a new transaction and find it by its generated ID. Assert the retrieved text matches

```java
@Test
    void persistAndFind() {

        // ---------- Transaction 1 : Persist ----------
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx1 = em.getTransaction();
        tx1.begin();

        Message msg = new Message("Hello JPA");
        em.persist(msg);

        tx1.commit();

        Long generatedId = msg.getId();

        assertNotNull(generatedId);

        System.out.println("Generated ID = " + generatedId);

        em.close();

        // ---------- Transaction 2 : Find ----------
        EntityManager em2 = emf.createEntityManager();

        EntityTransaction tx2 = em2.getTransaction();
        tx2.begin();

        Message found = em2.find(Message.class, generatedId);

        assertNotNull(found);
        assertEquals("Hello JPA", found.getText());

        System.out.println("Found = " + found);

        tx2.commit();
        em2.close();
    }
```

### Task 6

- Load a Message, change its text inside an active transaction and commit - without calling any save() or update() method
- verify in a third transaction that the update persisted - thus observing Dirty Checking

```java
@Test
void dirtyChecking() {

    EntityManager em = emf.createEntityManager();

    // ---------- Transaction 1 : Insert ----------
    EntityTransaction tx1 = em.getTransaction();
    tx1.begin();
    Message msg = new Message("Original Text...");
    em.persist(msg);
    tx1.commit();
    Long id = msg.getId();

    // ---------- Transaction 2 : Modify ----------
    EntityTransaction tx2 = em.getTransaction();
    tx2.begin();
    Message loaded = em.find(Message.class, id);
    loaded.setText("Modified Text...");
    // No persist() call needed
    tx2.commit();

    // ---------- Transaction 3 : Verify ----------
    EntityTransaction tx3 = em.getTransaction();
    tx3.begin();
    Message verified = em.find(Message.class, id);
    assertNotNull(verified);
    assertEquals("Modified Text...", verified.getText());
    tx3.commit();
    em.close();
}
```



