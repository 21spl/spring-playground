# Lab 03 — Hand the Wiring to Spring with Java @Configuration

## 📝 Problem Statement
Add Spring to the project. Recreate the same two-service setup (**ProductService** and **InventoryService**) but let Spring's application context manage and wire the beans. Use a `@Configuration` class with `@Bean` methods. Retrieve the `ProductService` bean from the context in `main` and call `printProductInfo`.

---

## 🛠️ Project Configuration (`pom.xml`)

To compile and run this lab using standard Maven commands, update your `pom.xml` with the properties, dependencies, and execution plugins below.

### 1. Java Compiler Version
Set your project's Java source and target compliance to Java 21:
```xml
<properties>
    <maven.compiler.source>21</maven.compiler.source>
    <maven.compiler.target>21</maven.compiler.target>
</properties>
```

### 2. Spring Framework Dependency
Add the core `spring-context` library to enable the IoC container and annotations:
```xml
<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-context</artifactId>
        <version>5.3.20</version>
    </dependency>
</dependencies>
```

### 3. Execution Plugin
Include the `exec-maven-plugin` to directly execute the Java main class through Maven commands:
```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.codehaus.mojo</groupId>
            <artifactId>exec-maven-plugin</artifactId>
            <version>3.6.0</version>
        </plugin>
    </plugins>
</build>
```

---

## 💻 Implementation Details

The project recreates the structural foundation from previous labs while introducing explicit Java Configuration to handle component creation and wiring:

* **`InventoryService.java`**: Interface defining the database/stock layout contract.
* **`DefaultInventoryService.java`**: Class implementing `InventoryService`.
* **`ProductService.java`**: Service handling product actions that depends on an `InventoryService`.
* **`AppConfig.java`**: A centralized configuration class where services are explicitly declared and wired together using `@Configuration` and `@Bean`.

### Bootstrapping the Container
Inside the `Main.java` class, initialization and bean resolution happen explicitly through the Spring lifecycle:

1. **Bootstrap the Spring container**:
   ```java
   ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
   ```
2. **Retrieve the managed service bean**:
   ```java
   ProductService productService = context.getBean(ProductService.class);
   ```

---

## 🔎 Key Architecture Observations

* **Complete Decoupling**: The `main` method has zero knowledge of `InventoryService` or its concrete implementations. It interacts exclusively with the high-level `ProductService`.
* **Centralized Assembly**: The Spring IoC container handles the discovery, creation, and dependency resolution of every managed bean automatically.
* **Effortless Extensibility**: To switch to a custom or mock environment provider (e.g., `MockInventoryService`), you only modify a single instantiation line inside `AppConfig.java`. The rest of your business logic remains completely untouched.

---

## 🚀 How to Compile and Execute

Run the commands below from your project terminal root to test the application using the Maven Wrapper:

### 1. Compile Code
```bash
./mvnw clean compile
```

### 2. Execute Application
```bash
./mvnw compile exec:java -Dexec.mainClass="com.example.tacos.Main"
```

### Expected Console Output
```text
Product: SKU-101 | Stock: 42
```
