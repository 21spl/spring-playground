package io.github.spl21.microproject1.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;



@Data
@Getter
@Setter
@ToString
@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    // no args constructor
    protected Customer(){}

    public Customer(String firstName, String lastName)
    {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}

/*
Notes:

1. Customer class has three attributes: id, firstName, lastName
2. Default constructor exists only for the sake of JPA. We don't use it directly. So marked protected
3. Customer class is annotated with @Entity, indicating that it is a JPA entity.
4. Because no @Table annotation exists, it is assumed that this entity is mapped to a table named Customer
5. id property is marked as @Id so that JPA recognizes it as object's ID (Primary Key)
6. @GeneratedValue(Strategy = GenerationType.AUTO) means, Id will be generated automatically
 */
