# Understanding In-Memory Authentication

## 1. ```InMemoryUserDetailsManager```

1. It implements ```UserDetailsService``` to provide support for username/password based authentication that is stored in memory
2. It provides management of ```UserDetails``` by implementing ```UserDetailsManager``` interface

### Step 1: Creating a bean for ```UserDetailsService```:

```java

@Bean
public UserDetailsService users(){

    UserBuilder users = User.withDefaultPasswordEncoder();
    UserDetails user = users
        .username("user")
        .password("password")
        .roles("USER")
        .build()

    UserDetails admin = users
        .username("admin")
        .password("password")
        .roles("USER", "ADMIN")
        .build();

    return new InMemoryUserDetailsManager(user, admin);

}

```