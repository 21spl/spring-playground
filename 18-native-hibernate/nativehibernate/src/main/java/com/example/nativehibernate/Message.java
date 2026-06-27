package com.example.nativehibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Message {
    

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    // no arg constructor
    public Message(){}

    public String getText(){
        return text;
    }

    public void setText(String text){
        this.text = text;
    }
}


