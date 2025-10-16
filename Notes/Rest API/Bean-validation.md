# Bean Validation 

In FastAPI, we use Pydantic schemas to validate and shape request data.

In Spring Boot, the equivalent idea is a **DTO (Data Transfer Object) + Bean Validation (JSR-303/JSR-380)**

Here how it works in Spring boot:

## Step 1: Create a DTO class

1. Instead of directly binding the request body to our entity, we usually define a DTO that represents the input

```java

@Data
public class UserRequest{
    @NotBlank(message = "Name is mandatory")
    private String name;

    @Email(message = "Email should be valid")
    private String email;

    @Min(value = 18, message = "Age should be atleast 18")
    private int age;
}
```

2. Annotations like ```@NotBlank```, ```@Email```, ```@Min```, etc. come from Jakarta Bean Validation.
3. **Hibernate Validator is the default implementation in Spring Boot.**
4. This is just like Pydantic field constraints(```Field(..., min_length=3)```, etc.).

## Step 2: Use DTO in Controllers

1. In the Controller, bind the request body to the DTO and add ```@Valid```


```java
@RestController
@RequestMapping("/users")
public class UserController{

    @PostMapping
    public ResponseEntity<String> createUser(@Valid, @RequestBody UserRequest userRequest){
        return ResponseEntity.ok("User created: " + userRequest.getName());
    }
}
```

2. ```@Valid``` tells Spring to apply the validation rules
3. If validation fails, Spring Boot automatically throws a ```MethodArgumentNotValidException```, which can be handled globally.


## Step 3: Global Exception Handling (Optional but Common)

1. We can create a ```@RequestControllerAdvice``` to return clean error responses.

```java

@RestControllerAdvice
public class GlobalExceptionHandler{

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex){
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        return ResponseEntity.badRequest().body(errors);
    }
}
```

2. ```@RestControllerAdvice``` is a special Spring annotation that combines:
    - ```@ControllerAdvice```: applies to all controllers globally (cross-cutting concern)
    - ```@ResponseEntity```: ensures responses are returned as JSON instead of HTML

3. ```public class GlobalExceptionHandler``` - This is just a normal class, but Spring treats as "exception handling center" because of the annotation.
4. ``` @ExceptionHandler(...)``` - tells Spring:
   - If this particular exception is thrown anywhere in Controller, run this method

5. ```MethodArgumentNotValidException.class``` - This exception is specifically thrown when ```@Valid``` fails validation on a request body.
6. ```MethodArgumentNotValidException``` is a pre-defined class in Spring Framework. It is part of **Spring Web MVC**

7. ```handleValidationErrors(MethodArgumentNotValidException ex)```:
   - This is the method that handles the exception
   - Here we create a HashMap to store error messages
   - ```ex.getBindingResult()```: gives full validation result(all errors)
   - ```.getFieldErrors()```: gets a list of field-specific error
   - ```.forEach(...)```: loop over each error.
   - Inside the loop:
     - ```error.getField()```: the field name that failed
     - ```error.getDefaultMessage()```: the message from our annotation



