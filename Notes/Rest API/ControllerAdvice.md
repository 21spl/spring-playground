# What is `@ControllerAdvice`?

1. ```@ControllerAdvice``` is a special Spring annotation used to define g**lobal exception handling** and **controller-level advice**.
2. We can think of it as a Centralized interceptor for our controller
   - It can catch exceptions thrown by any controller in our application
   - It can also manipulate model attributes or apply global data binding rules
3. It is a special type of ```@Component```


# 2. How @ControllerAdvice works?

1. when a request hits a controller, Spring executes the controller method

2. If the controller method throws am 