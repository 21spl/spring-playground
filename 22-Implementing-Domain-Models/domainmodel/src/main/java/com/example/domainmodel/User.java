package com.example.domainmodel;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="USERS")
public class User {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false, unique=true)
    private String username;

    private String firstname;
    private String lastname;

    // Three Address Embeddings in one USERS table row

    // @AttributeOverride renames the columns to avoid name collision

    // homeAddress column
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column= @Column(name="HOME_STREET")),
        @AttributeOverride(name="zipcode", column= @Column(name="HOME_ZIPCODE")),
        @AttributeOverride(name="city", column= @Column(name="HOME_CITY"))
    })
    private Address homeAddress;

    // billingAddress column
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column = @Column(name="BILLING_STREET")),
        @AttributeOverride(name="zipcode", column = @Column(name="BILLING_ZIPCODE")),
        @AttributeOverride(name="city", column = @Column(name="BILLING_CITY"))
    })
    private Address billingDetails;

    // shippingAddress column
    @Embedded
    @AttributeOverrides({
        @AttributeOverride(name="street", column = @Column(name="SHIPPING_STREET")),
        @AttributeOverride(name="zipcode", column = @Column(name="SHIPPING_ZIPCODE")),
        @AttributeOverride(name="city", column = @Column(name="SHIPPING_CITY"))
    })
    private Address shippingDetails;

    // no-arg constructor
    public User(){}

    // args constructor
    public User(String username, String firstname, String lastname){
        this.username=username;
        this.firstname=firstname;
        this.lastname=lastname;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public Address getBillingDetails() {
        return billingDetails;
    }

    public Address getShippingDetails() {
        return shippingDetails;
    }

    // setters for addresses only
    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public void setBillingDetails(Address billingDetails) {
        this.billingDetails = billingDetails;
    }

    public void setShippingDetails(Address shippingDetails) {
        this.shippingDetails = shippingDetails;
    }
}
