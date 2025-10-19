# Project Goal

1. At first, we want to see the behaviour of our application without adding spring security in dependencies
2. Next, we will add spring security in dependencies to learn what changes
3. Then, we know what we can expect from the default configuration for authentication and authorization
4. Then we change the project to add functionality for user management by overriding the defaults to define
custom users and passwords
5. Apply different styles for the same configurations to understand best practices

# Part 1. Without applying Spring Security

### Step 1: Create a Controller class and a REST endpoint

```java
@RestController
public class HelloController {
 
    @GetMapping("/hello")
    public String sayHello() {
        return "Hello, World!";
    }
}
```

Now run the application. The application is running on port: 8080.
So open the browser and open the link: http://localhost:8080/hello
we will find a "hello!" written on the page

# Part 2: Adding spring-security dependency in pom.xml to get the default security

```xml
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```
1. Now when we try to access the URL through browser, we get the login form with username and password
2. Now, if we try to access the endpoint: http://localhost:8080/hello via postman, 
we get:

```json
{
  "status": 401,
  "error": "Unauthorized",
  "message": "Unauthorized",
  "path": "/hello"
}
```

3. Now observe carefully, in the terminal, the following line:

```txt
Using generated security password: ca4a6e94-416e-4c84-90bd-60d74d4d68dc
This generated password is for development use only. Your security configuration must be updated before running your application in production.
```
4. Now, in the login form if we pass "user" as username and the above password as password,
we will be able to reach the endpoint
5. Similarly, if we pass this username and password as authentication headers (Basic Authentication)
in postman and then hit the end point with GET request, we will be able to reach.



### Note: Understanding important HTTP code

1. HTTP 401 Unauthorized access is ambiguous
2. Usually it is used to represent a failed authentication not failed authorization
3. So we will use 403 Forbidden for failed authorization- This means server
has identified the caller of the request, but they don't have the privilege for the call
they are trying to make.
4. HTTP 401 is used to represent failed authentication