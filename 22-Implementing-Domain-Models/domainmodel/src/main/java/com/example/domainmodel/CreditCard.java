package com.example.domainmodel;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name= "CREDIT_CARD")
public class CreditCard  extends BillingMode{
    
    @Column(nullable = false)
    private String ccNumber;

    private int expMonth;
    private int expYear;

    // no arg constructor
    public CreditCard(){}

    public CreditCard(String owner, String ccNumber, int expMonth, int expYear) {
        super(owner);
        this.ccNumber = ccNumber;
        this.expMonth = expMonth;
        this.expYear = expYear;
    }

    public String getCcNumber() {
        return ccNumber;
    }


    public int getExpMonth() {
        return expMonth;
    }


    public int getExpYear() {
        return expYear;
    }  
}
