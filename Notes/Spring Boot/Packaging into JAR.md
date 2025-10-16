# Packaging Spring Boot App into JAR (using MAVEN)

Since we’re using Spring Boot + Maven, packaging is super easy because Spring Boot Maven Plugin takes care of it.

#### Step 1. Check pom.xml

Make sure we have the Spring Boot Maven Plugin inside <build>:

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

This plugin creates an executable JAR (with dependencies + main class inside).

#### Step 2. Package our app

Run:
```
./mvnw clean package
```
(or on Windows PowerShell)
```
mvnw clean package
```

This will:
- Clean old builds
- Compile our project
- Package it into a JAR

👉 The JAR will be in:
```
target/microproject1-0.0.1-SNAPSHOT.jar
```
#### Step 3. Run the JAR

Now simply run:
```
java -jar target/microproject1-0.0.1-SNAPSHOT.jar
```

This will start our Spring Boot application exactly like mvn spring-boot:run, but now it’s in a single portable JAR file