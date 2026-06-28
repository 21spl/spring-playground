package com.example.domainmodel;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BillingMode {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    // common to all billing strategies
    @Column(nullable=false)
    private String owner; // name on the bank account/cc


    // no arg constructor - must for jpa
    public BillingMode(){}

    public BillingMode(String owner){
        this.owner = owner;
    }

    // getters - id, owner

    public Long getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }
}
