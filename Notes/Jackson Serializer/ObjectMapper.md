# Objective

Understanding Jackson ObjectMapper class and how to serialize Java objects into JSON and deserialize JSON string into Java Objects

### 1. Dependencies


```xml
<dependency>
    <groupId>com.fasterxml.jackson.core</groupId>
    <artifactId>jackson-databind</artifactId>
    <version>2.17.2</version>
</dependency>
```

### 2. Creating the Object for Serialization - Deserialization

###### Car class with two fields as the object to serialize and deserialize

```java

public class Car{
    private String color;
    private String type;

    // getters and setters
}

```

### 3. Automatic conversion of Java Object to JSON using @RestController annotation

- Note, here we are doing everything manually.
- But Spring Boot, does this conversion automatically using @RestController annotation
- While the controller receives request in JSON body, it deserializes into the object (mentioned in controller method parameter)
- Again while sending response, the controller converts the object (return type of the controller method) into JSON
- We can only create an ObjectMapper bean to specify how to convert.


```java
@Configuration 
public class JacksonConfig {

    @Bean // Tells Spring to manage this ObjectMapper as a bean in the ApplicationContext
    public ObjectMapper objectMapper() {

        // Create a new Jackson ObjectMapper instance
        ObjectMapper objectMapper = new ObjectMapper();

        // enables pretty formatted indented JSON output
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        // Defines a readable date format for JSON Serialization
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        // ensures consistent timezone handling
        objectMapper.setTimeZone(TimeZone.getTimeZone("Asia/Kolkata"));

        return mapper;
    }
}
```

### 4. Manual conversion of Java Object to JSON - Manual Serialization


```java
ObjectMapper objectMapper = new ObjectMapper();

// define the object
Car car = new Car("Yellow", "Sedan");

objectMapper.writeValue(new File("target/car.json"), car);
```

### 5. Manual conversion of JSON to Java Object - Manual Deserialization

```java

ObjectMapper objectMapper = new ObjectMapper();
String json = "{ \"color\" : \"Black\", \"type\": \"SUV\"};

Car car = objectMapper.readValue(json, Car.class);

```

### 6. Manual conversion of file containing JSON string to Java object

```java

String json = "{ \"color\" : \"Black\", \"type\" : \"BMW\" }";
Car car = objectMapper.readValue(json, Car.class);	


Car car = objectMapper.readValue(new File("src/test/resources/json_Car.json"), Car.class);

```