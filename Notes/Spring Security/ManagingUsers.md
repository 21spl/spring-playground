# Managing Users in Spring Security

### 1. Objective
1. Describing a user with the ```UserDetails``` interface
2. Using ```UserDetailsService``` in the authentication flow
3. Creating a custom implementation of ```UserDetailsService```
4. Creating a custom implementation of ```UserDetailsManager```
5. Using ```JdbcUserDetailManager``` in authentication flow.

## 2. Important Interfaces to learn

| Interface | Purpose | Implementation Class |
|-----------|---------|----------------------|
| UserDetails | describes the user in spring security | |
| GrantedAuthority | Defines the actions that user can perform ||
| UserDetailsService | Retrieves user by username|
|UserDetailsManager (extends UserDetailsService) | adds functionality of adding, modifying and deleting users
|


#### Note: Why so many interfaces and chains of inheritance - looks like family tree of 100 yr long dynasty?
Interface Segregation Principle

1. Separating interfaces allows for better flexibility because the framework doesn't force us to implement behaviour which is a over do or which is not required in our application
2. If our application just require user authentication, implementing ```UserDetailsService``` is enough.
3. But if our application needs not only user authentication, but wants to provide functionality to users to update their details, the app must manage users. Then it requires both ```UserDetailsService``` and ```UserDetailsManager```


# 3. Describing the User

### 3.1 Understanding ```UserDetails``` interface

1. For Spring Security, a user definition should fulfil the contract defined by ```UserDetails``` interface
2. ```UserDetails``` interface represent the user as understood by Spring's Security
3. Therefore our custom UserEntity - entity class must implement this interface. Then only spring security framework recognizes our custom UserEntity class as user.


Following is the ```UserDetails``` interface:

```java
public interface UserDetails extends Serializable{
    String getUsername();
    String getPassword();
    
    // returns the privileges an user has as a collection of GrantedAuthority instances
    Collection<? extends GrantedAuthority> getAuthorities(); 
    
    // following 4 methods enable and disable the account for different reasons
    boolean isAccountNonExpired();
    boolean isAccountNonLocked();
    boolean isCredentialsNonExpired();
    boolean isEnabled();
}
```

Note: *All application does not need such fine grained control for account enabling and disabling. In that case, simply return true for these four methods*

### 3.2 Understanding ```GrantedAuthority``` interface

1. The authorities define what the users can do
2. If no authority is defined, all users would be equal.
3. But in most applications, we have more than one types of users - customers / developers / admin etc.
4. In those cases, authorities acts as differentiator between user types.
5. Every user must have atleast one authority
   
```java
@FunctionalInterface
public interface GrantedAuthority extends Serializable{
    String getAuthority();
}
```

6. In this functional interface, we can use lambdas to implement the ```getAuthority()``` method.

```java
GrantedAuthority authGranted = () -> "READ";
```
7. We can also use ```SimpleGrantedAuthority``` class to create an
```immutable instance``` of type ```GrantedAuthority```

```java
GrantedAuthority authGranted = new SimpleGrantedAuthority("READ");
```

### 3.3 Writing implementation of ```UserDetails``` interface

```java

public class CustomUser implements UserDetails{

    private final String username;
    private final String password;

    // constructor
    public CustomUser(String username, String password)
    {
        this.username = username;
        this.password = password;
    }

    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    // overriding the getAuthorities() method
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(() -> "READ");
    }

    // implementation of 4 enable-disable/lock-unlock/expired-alive user methods
    @Override
    public boolean isAccountNonExpired(){
        return True;
    }

    @Override
    public boolean isAccountNonLocked(){
        return True;
    }

    @Override
    public boolean isEnabled(){
        return True;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return True;
    }
}
```

### 3.4 Using Builder to create instances of UserDetails type

1. Some applications are simple and don't need a custom implementation class of UserDetails interface
2. Instead we can obtain an instance representing our custom user with the ```User``` builder class
3. The ```User``` class from ```org.springframework.security.core.userdetails``` package is a simple way to build instances of ```UserDetails``` type

```java
UserDetails u = User.withUsername("John")
                .password("12345")
                .authorities("read", "write")
                .accountExpired(false)
                .disabled(true)
                .build();
```

4. ```User.withUsername(String username)``` method returns an instance of the builder class ```UserBuilder```

# 4. Connecting UserDetails with JPA User entity class

### 4.1 Combining JPA User entity class with implementation class of UserDetails interface

```java
@Entity
public class UserEntity implements UserDetails{

    @Id
    private Long userId;
    private String username;
    private String password;
    private String authority;
    

    // overriding the methods of UserDetails interface
    
    @Override
    public String getUsername(){
        return this.username;
    }

    @Override
    public String getPassword(){
        return this.password;
    }

    @Override
    public String getAuthority(){
        return this.authority;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(() -> authority);
    }
}
```

1. getter method of entity class collides with overridden methods of UserDetails interface - getUsername(), getPassword()
2. getAuthority() - just a getter method - returns a String
3. getAuthorites() - overridden method of userDetails -  returns a Collection 
4. These makes the code dirty and has no separation of concerns
5. To make the code clean and maintain separation of concerns, we keep JPA UserEntity entity class and implementation class of UserDetails interface separate

### 4.2 Connecting UserEntity entity class and implemenation class of UserDetails interface

###### UserEntity - JPA entity class

```java
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User{

    @Id
    private int userId;
    private String username;
    private String password;
    private String authority;
}
```


###### SecurityUser - implementation of UserDetails interface

```java

public class SecurityUser implements UserDetails{

    // constructor injection
    private final UserEntity userEntity;
    public SecurityUser(UserEntity userEntity)
    {
        this.userEntity = userEntity;
    }

    @Override
    public String getUsername()
    {
        return userEntity.getUsername();
    }

    @Override
    public String getPassword()
    {
        return userEntity.getPassword();
    }

    @Override
    public Collections<? extends GrantedAuthority> getAuthorities(){
        return List.of(() -> userEntity.getAuthority());
    }

    
    // implementation of 4 enable-disable/lock-unlock/expired-alive user methods
    @Override
    public boolean isAccountNonExpired(){
        return True;
    }

    @Override
    public boolean isAccountNonLocked(){
        return True;
    }

    @Override
    public boolean isEnabled(){
        return True;
    }

    @Override
    public boolean isCredentialsNonExpired(){
        return True;
    }

}

```


# 5. Managing Users for Spring Security - ```UserDetailsService```


### 5.1 Understanding ```UserDetailsService``` interface
1. See, with respect to Spring Security, we design users  with the template our custom SecurityUser class, which implemented ```UserDetails``` interface
2. But now we have to manage those users (SecurityUser) using ```UserDetailsService``` interface
3. UserDetailsService interface has only one method: ```loadUserByUsername(String username) throws UsernameNotFoundException```

```java
public interface UserDetailsService{

    UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;
}

```

4. UsernameNotFoundException is a runtime exception. It inherits from AuthenticationException, which is the parent of all exceptions related to authentication processs
5. AuthenticationException further inherits RuntimeException class


### 5.2 Implementing ```UserDetailsService``` interface using ```InMemoryUserDetailsService```

1. Spring Security provides ```InMemoryUserDetailsService``` class, which implements ```UserDetailsService``` interface.

```java

public class InMemoryUserDetailsService implments UserDetailsService{
    // This class manages the list of users in memory
    private final List<UserDetails> users;


}

```

