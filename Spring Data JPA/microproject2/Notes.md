
# 1. Spring Framework for SQL Databases

Spring framework provides extensive support for working with SQL Databases
1. For direct JDBC access, Spring provides ```JdbcClient```
2. For ORM mapping using Hibernate, Spring provides ```JdbcTemplate```
3. Spring data provides an additional functionality:
    - Creating ```Repository``` implementations directly from intefaces and using conventions
      to generate queries from our method names

# 2. Configuring DataSource
1. Java’s ```DataSource``` interface provides a standard method of working with database connections.
   Traditionally, a ```DataSource``` uses a URL along with some credentials to establish a
   database connection.

# 3. Embedded Database Support

1. It is often convenient to develop applications by using an in-memory embedded database
2. These in-memory databases do not provide persistent storage
3. We need to populate our in-memory database when our application starts and the db throws away data
   when our application ends.
4. Spring Boot can auto-configure embedded H2, HSQL and Derby databases
5. We need not provide any connection URLs. We just need to add a dependency for these in-memory db
6. If there are multiple embedded databases on the classpath, set ```spring.datasource.embedded-database-connection```
   configuration property to control which one is used. Setting the property to ```none``` disables autoconfiguration
   of an embedded database

----------------------------------------------
**Note**:

We need a dependency on ```spring-jdbc``` for an embedded database to be auto-configured.
```spring-jdbc``` is present in ```spring-boot-starter-data-jpa```

---------------------------------------------------------------------------

# 4. Connection to a Production Database

Production database connections can also be auto-configured by using a pooling  ```DataSource```.
In JDBC, ```DataSource``` is just an object that knows how to provide us the database connection.
It is like a tap to get connections from.

#### 4.1 DataSource Configuration

```properties
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
```

Note: We should atleast specify the URL, otherwise, Spring Boot tries to auto-configure an embedded database


**💡| TIP:**
- Spring Boot can deduce the JDBC Driver class for most databases from the URL
- If we need to specify a specific class we can use : ```spring.datasource.driver-class-name``` property

#### 4.2 Need of connection pool

Opening and Closing a database connection is expensive
If every query opened a new connection, our app would be very slow.
A **connection pool** solves this by:
- keeping a set of pre-opened connections
- Reusing them across requests
So the datasource implementation we get is actually a connection pool under the hood

#### 4.3 Which connection pool does Spring Boot choose?

Spring Boot has an order of preference:
- HikariCP (default, fastest, most common in production)
- Tomcat JDBC pool
- Commons DBCP2
- Oracle UCP

If we add ```spring-boot-starter-jdbc``` or ```spring-boot-starter-data-jpa```, we automatically get HikariCP, unless we
override it

**Note: Connection pool does not depend on the server (Tomcat, Jetty, etc). The connection pool is chosen and
managed inside our spring boot application, not by the servlet container.

Therefore, even if we deploy on Tomcat Server, we don't automatically get Tomcat JDBC pool.

#### 4.4 Connection Pool Configuration

1. Following is the Generic DataSource config (applies no matter which pool is used):

```properties
spring.datasource.url=jdbc:mysql://localhost/test
spring.datasource.username=dbuser
spring.datasource.password=dbpass
```

2. Connection-pool-specific config (depends on which pool implementation is in use):

For HikariCP:
```properties
spring.datasource.hikari.maximum-pool-size=20
spring.datasource.hikari.minimum-idle=5
```
