# Spring Boot Controller class


```java
@RestController
@RequestMapping(value = "v1/organization/{organizationId}/license")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;

    @GetMapping(value="/{licenseId}")
    public ResponseEntity<License> getLicense(@PathVariable("organizationId") String organizationId, @PathVariable("licenseId") String licenseId){
        License license = licenseService.getLicense(licenseId, organizationId);
        return ResponseEntity.ok(license);
    }

    
    @PutMapping
    public ResponseEntity<String> updateLicense(
            @PathVariable("organizationId")
            String organizationId,
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.updateLicense(request,
                organizationId));
    }

    
    @PostMapping
    public ResponseEntity<String> createLicense(
            @PathVariable("organizationId") String organizationId,
            @RequestBody License request) {
        return ResponseEntity.ok(licenseService.createLicense(request,
                organizationId));
    }

    @DeleteMapping(value="/{licenseId}")
    public ResponseEntity<String> deleteLicense(
            @PathVariable("organizationId") String organizationId,
            @PathVariable("licenseId") String licenseId) {
        return ResponseEntity.ok(licenseService.deleteLicense(licenseId,
                organizationId));
    }

}

```


1. In any spring boot application, the **controller class** exposes the service endpoints 
2. After that the controller ***maps the data from an incoming HTTP request to a Java method that processes the request.***
3. All the applications we build will have the following concepts:
   - HTTP/HTTPS invocation protocol
   - HTTP verbs
   - JSON as serialization format
   - HTTP status code


### 1. HTTP/HTTPS invocation protocol

1. Our application uses HTTP/HTTPS as the invocation protocol for the service.
2. An HTTP endpoint exposes the service, and the HTTP protocol carries data to and from the service

### 2. HTTP Verbs

1. Map the behaviour of the service to standard HTTP verbs
   - POST
   - GET
   - PUT
   - DELETE
2. These verbs map to the CRUD functions in most services

### 3. JSON as the Serialization format

1. We use JSON as the serialization format for all data going to and from the service.
2. We can use XML, but many REST based applications make use of JavaScript and JSON
3. JSON is the native format for Serialization and De-Serialization of data consumed by JS based web front end
   
### 4. HTTP Status Codes

1. Using HTTP status code to communicate the status of the service call
2. The HTTP protocol uses a rich set of status codes to indicate the success or failure of a service
3. REST-based services take advantage of these HTTP status codes

HTTP is the language of the web. Using HTTP as the philosophical framework for building our services


### 5. ```@RestController``` annotation

1. ```@RestController```- Tells Spring Boot that this is a REST based service and it will automatically serialize/deserialize service requests/responses via JSON
2. This annotation is a class-level java annotation that tells the Spring container that this Java class will be used for REST-based service
3. This annotation *automatically handles the serialization of data passed into the service  as JSON*
4. ```@RestController``` annotation includes ```@ResponseBody``` annotation too
5. ***Thus, unlike the traditional Spring ```@Controller``` annotation (used in Spring MVC), @RestController doesn't require us to return a ```ReturnBody``` class from the methods present in the controller class.***


### 6. ```@RequestMapping``` annotation

1.  ```@RequestMapping(value = "v1/organization/organizationId/license")``` - Exposes all the HTTP endpoints in this class with a prefix of ```/v1/organization/{organizationId}/license```
2. This annotation can be used as both class-level and method-level annotation,
3. This annotation tells the Spring Container the HTTP endpoint that the service is going to expose to the user
4. When we use ```@RequestMapping``` as the class-level annoation, we are establishing the root of the URL for all the endpoints exposed by the controller
 

### 7. Why JSON for microservices ?

1. we can use multiple protocols to send data back and forth between HTTP-based microservices. 
2. But JSON has emerged as the de facto standard for several reasons.
3. Compared to other protocols like XML-based SOAP( Simple Object Access Protocol), JSON is extremely lightweight
4. JSON is easily read and consumed by a human being. If any problem occurs, developers can look at a chunk of JSON and to quickly process what's in it. 

### 8. Understanding GET Mapping ```@GetMapping(value="/{licenseId}")```

1. In the controller class, we have two levels of annotation:
   1. class level annotation: ```v1/organization/organizationId/license```
   2. method level annotation: ```licenseId```
2. The entire URL of the endpoint for getLicense() method is thus ```v1/organization/{organizationId}/license/{licenseId}```
3. The class level annoation is used to match all HTTP requests to the controller and then the method level annotation matches the request to exact controller method (here get method)
4. This @GetMapping() annoation can be replaced with ```@RequestMapping(value="/{licenseId}", method=RequestMethod.GET)```

### 9. Understanding ```@PathVariable``` annotation

1. This annotation maps the parameter values passed in the incoming URL as denoted by the ```{parameterName}``` to the parameters of our method
2. in the get method, the two path variables are two parameters from the URL (OrganizationId - from class level annotation and licenseId - from method level annotation)

```java
@PathVariable("organizationId") String organizationId
@PathVariable("licenseId") String licenseId
```

### 10. Understanding ```ResponseEntity``` as return type object

1. The ```ResponseEntity``` represents the entire HTTP response, including the status code, headers and the body

### 11. Understanding ```@RequestBody``` annotation

1. ```@RequestBody``` annotation (here, @RequestBody License license) maps the body of the HTTPRequest to a POJO object (here, License object)