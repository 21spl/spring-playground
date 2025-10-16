package io.github.spl21.microproject1.repository;

import io.github.spl21.microproject1.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByLastName(String lastName);

    Customer findById(long id);
}


/*
Note:

1. Spring Data JPA focuses on using JPA to store data in a relational database
2. The most compelling feature is the ability to create repository interface automatically
at runtime, from a repository interface
3. CustomerRepository extends the CrudRepository interface
4. The type of entity and ID that it works with are Customer and Long (datatype of customer entity id)
5. So CrudRepository  is specified by generic parameters<Customer, Long>
6. By extending CrudRepository, CustomerRepository inherits several methods for working with Customer persistence
7. Spring Data JPA also lets us define other query methods by declaring their method signature - findByLastName(), findById()
8. In typical java application, we write a class that implements CustomerRepository.
9. But it is the magic of Spring Data JPA which creates an implementation when we run the application
 */