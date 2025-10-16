# What is Internationalization?

1. Internationalization is an essential requirement to enable our application to adopt to different languages. 

2. The main goal here is to develop applications that offer content in multiple formats and languages


### 1. Creating ```LocaleResolver``` Bean:

1. We will be creating beans for ```LocaleResolver``` in the ***bootstrap class***
2. The intention behind these beans is to provide the application content in multiple languages or formats
3. Spring uses LocaleResolver to determine which locale(language/region) is active for current user/session
4. Here, default locale is set to US English (Locale.US)
5. If no specific locale is passed by the user/request, Spring will assume US English


```java
@Bean
public LocaleResolver localeResolver(){
    // create an object of SessionLocaleResolver
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    // sets US as the default locale
    localResolver.setDefaultLocale(Locale.US);
    return localeResolver;
}
```

### 2. Creating ResourceBundleMessageSource bean:

1. we will also create the bean for ResourceBundleMessageSource bean in the bootstrap class

```java
@Bean
public ResourceBundleMessageSource messageSource()
{
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();

    // following line ensures that if message isn't found
    // no error is thrown, instead it returns the message code
    // following lines sets some message code(explained later) as the default message
    messageSource.setUseCodeAsDefaultMessage(true);

    // sets the base name of message source file
    messageSource.getBasenames("messages");

    return messageSource;
}
```


### 3. Creating message properties files

1. To create the message source files, we create the following files in "/src/main/resources/" folder
   - messages.properties
   - messages_es.properties
   - messages_en.properties


###### messages_en.properties

```properties
license.create.message = license created %s
license.update.message = license %s updated
license.delete.message = Deleting license with id %s for the organization %s
```

###### messages_es.properties

```properties
license.create.message = licencia creada %s
licencia.update.message = licencia %s creada
license.delete.message = Eliminando licencia con
id %s para la organization %s organizationId
```

### 4. Updating the Service class and methods to call the message resource

```java

@Autowired
MessageSource messages;

// updating the createLicense method
public String createLicense(License license, String organization, Locale locale)
{
    String responseMessage = null;
    if(license!=null){
        license.setOrganzationId(organizationId);
        responseMessage = String.format(messages.getMessage("license.create.message", null, locale), license.toString());
        return responseMessage;
    }
}

// updating the updateLicense method
public String updateLicense(License license, String organizationId){
    String responseMessage = null;
    if (license != null) {
        license.setOrganizationId(organizationId);
        responseMessage = String.format(messages.getMessage(
        "license.update.message", null, null),
        license.toString());
    }
    return responseMessage;
}

```

1. In updateLicense() method of the service class, we are calling messages.getMessage("license.update.message", null, null)
2. But we never explicitly wrote a getMessage() method anywhere
3. ```messages``` is of type ```MessageSource``` interface
4. in the bean, ```ResourceBundleMessageSource messageSource()```, messageSource is an object of ```ResourceBundleMessageSource``` class
5. 

### 5. Updating the methods of controller class

1. Updating the createLicense() method of the controller class (not service class)

```java
@PostMapping
public ResponseEntity<String> createLicense(@PathVariable("organizationId") String organizationId, @RequestBody License request, 
        @RequestHeader(value="Accept-Language", required=false) Locale locale)
{

    return ResponseEntity.OK(licenseService.createLicense(request, organizationId, locale));
}
```

2. Say following is an example of request body
3. 
```JSON
{
    "id": "abc",
    "productname" : "ProSoftware",
}
```
This request body is automatically mapped to license Java object using Jackson Serializer

4. @RequestHeader(value="Accept-Language", required=false) Locale locale
- It reads the **"Accept-Language"** header from the HTTP request and maps it to ```Locale``` object locale.
- If the header is missing and *required = false*, then locale falls back to our default locale (US English) from ```SessionLocaleResolver```
- ```SessionLocaleResolver``` is an implementation of MessageSource interface and it already defines ```getMessage(...)``` function






   
    