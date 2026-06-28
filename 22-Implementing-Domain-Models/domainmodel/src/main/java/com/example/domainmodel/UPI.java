package com.example.domainmodel;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name="UPI")
public class UPI extends BillingMode {
    
    private String upiId;

    // no arg constructor
    public UPI(){}

    // all arg constructor
    public UPI(String owner, String upiId){
        super(owner);
        this.upiId = upiId;
    }

    public String getUpiId() {
        return upiId;
    }
}
