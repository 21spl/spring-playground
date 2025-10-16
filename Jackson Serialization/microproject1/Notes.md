
## 1. Version/Dependency management conflict

1. dont write version


Instead of writing this, 
```xml
<!-- https://mvnrepository.com/artifact/org.mongodb/bson -->
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>bson</artifactId>
    <version>5.5.1</version>
</dependency>

```

Instead of writing this, write the following

```xml
<!-- https://mvnrepository.com/artifact/org.mongodb/bson -->
<dependency>
    <groupId>org.mongodb</groupId>
    <artifactId>bson</artifactId>
</dependency>

```


### 2. Dependencies for bson data types

