# 18 - Native Hibernate Bootstrap

### Objective

Build the SessionFactory manually and understand each step of the bootstrap process

This lab is broken into multiple tasks


### setup - pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	
	<groupId>com.example</groupId>
	<artifactId>nativehibernate</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>nativehibernate</name>
	
	
	<properties>
		<maven.compiler.source>21</maven.compiler.source>
		<maven.compiler.target>21</maven.compiler.target>
	</properties>

	<dependencies>

		<dependency>
			<groupId>org.hibernate.orm</groupId>
			<artifactId>hibernate-core</artifactId>
			<version>7.1.0.Final</version>
		</dependency>


		<dependency>
			<groupId>org.postgresql</groupId>
			<artifactId>postgresql</artifactId>
			<version>42.7.7</version>
		</dependency>

		<dependency>
			<groupId>org.junit.jupiter</groupId>
			<artifactId>junit-jupiter</artifactId>
			<version>5.13.4</version>
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
- write hibernate.cfg.xml for postgresql
- Include connection pool size, SQL logging, and create-drop DDL mode
- place it in resources


```xml
<?xml version="1.0" encoding="utf-8"?>
<!DOCTYPE hibernate-configuration PUBLIC
        "-//Hibernate/Hibernate Configuration DTD 3.0//EN"
        "https://hibernate.org/dtd/hibernate-configuration-3.0.dtd">


<hibernate-configuration>
    <session-factory>

        <!--Database Connection-->
        <property name="hibernate.connection.driver_class">org.postgresql.Driver</property>

        <property name="hibernate.connection.url">jdbc:postgresql://localhost:5432/learnjpa</property>

        <property name="hibernate.connection.username">wrik</property>

        <property name="hibernate.connection.password">Guitar$$2124</property>

        <!--SQL Dialect-->
        <property name="hibernate.dialect">org.hibernate.dialect.PostgreSQLDialect</property>

        <!--show sql-->
        <property name="hibernate.show_sql">true</property>

        <!--format sql-->
        <property name="hibernate.format_sql">true</property>

        <!--Schema Generation-->
        <property name="hibernate.hbm2ddl.auto">create</property>

    </session-factory>
</hibernate-configuration>
```

### Task 2 - Create a Message Entity

```java
@Entity
public class Message {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    // no arg constructor
    public Message(){}

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}
```


### Task 3

- Write a ```createSessionFactory()``` method that builds a ```SessionFactory``` using Configuration ```StandardServiceRegistryBuilder``` and ```ServiceRegistry```
- Add the Message entity class explicitly
- Print the class name of the returned factory to confirm it is a hibernate object


```java
public class NativeHibernateBootstrap {
    
    public static SessionFactory createSessionFactory(){

        // 1. Create a configuration - the settings container
        Configuration configuration = new Configuration();

        // 2. Load hibernate.cfg.xml from classpath
        // addAnnotationClass() tells Hibernate which classes are entities
        // alternative to listing them in the XML file
        configuration.configure().addAnnotatedClass(Message.class);

        // 3. Build the ServiceRegistry - Hibernate's internal service container
        // applySettings copies all properties from Configuration into it
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();

        // 4. Build the session factory - the final usable object
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

        // SessionFactory IS a Hibernate object (not a proxy)
        System.out.println("Factory class: " + sessionFactory.getClass().getName());

        // output: org.hibernate.internal.SessionFactoryImpl

        return sessionFactory;
    }
}
```


### Task 4 - Test

- Create a transaction to persist one message object
- Create another transaction to select all the message objects using CriteriaAPI

```java

public class NativeHibernateTest {
    
    // SessionFactory implements AutoCloseable
    static SessionFactory sessionFactory;

    @BeforeAll
    static void setup(){
        sessionFactory = NativeHibernateBootstrap.createSessionFactory();
    }

    @AfterAll
    static void teardown(){
        if(sessionFactory!=null) sessionFactory.close();
    }

    @Test
    void storeAndLoadWithCriteriaApi(){

        // session also implements AutoCloseable
        try(Session session = sessionFactory.openSession()){

            // transaction 1 - insert
            session.beginTransaction();

            Message msg = new Message();
            msg.setText("Hello from Native Hibernate");
            session.persist(msg);

            // persist() is the JPA-compatible method name
            session.getTransaction().commit();
            //  INSERT INTO MESSAGE (TEXT) VALUES (?)

            // transaction 2 - Select via criteria API
            session.beginTransaction();

            // CriteriaBuilder: factory for query components
            CriteriaBuilder cb = session.getCriteriaBuilder();

            // CriteriaQuery: the query definition - what type to return
            CriteriaQuery<Message> query = cb.createQuery(Message.class);

            // root: the FROM clause - "FROM Message e"
            query.from(Message.class);

            // Execute
            List<Message> messages = session.createQuery(query).getResultList();
            // SELECT ID, TEXT from MESSAGE

            session.getTransaction().commit();

            assertAll(
                () -> assertEquals("Hello from Native Hibernate", messages.get(0).getText()),
                () -> assertEquals(1, messages.size())
            );
        }
        // session auto-closed here
    }
}
```