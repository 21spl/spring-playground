package com.example.domainmodel;
// @Embeddable - this class's fields are stored in the table of owning entity
// Not an entity - so no @Id, no table, no independent lifecycle

import jakarta.persistence.Embeddable;

@Embeddable
public class Address{

    private String street;
    private String zipcode;
    private String city;

    // jpa requires no-arg constructor
    protected Address(){}

    public String getStreet() {
        return street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public String getCity() {
        return city;
    }

    @Override
    public String toString() {
        return "Address [street=" + street + ", zipcode=" + zipcode + ", city=" + city + "]";
    }

    // Address is a value object
    // value objects should be immutable
    // so no setters - only getters
}