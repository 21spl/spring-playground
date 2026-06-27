package com.example.jpademo;

import jakarta.persistence.*;

@Entity
@Table(name="MESSAGE")
public class Message{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="TEXT")
    private String text;


    // no-arg constructor
    public Message(){}

    // arg constructor for convenience
    public Message(String text){
        this.text = text;
    }

    // getters
    public Long getId(){
        return this.id;
    }

    public String getText(){
        return this.text;
    }

    // setters - id can't have setter
    public void setText(String text){
        this.text = text;
    }

    @Override
    public String toString() {
        return "Message [id=" + id + ", text=" + text + "]";
    } 
}

/*
Understanding other GenerationType options


1. IDENTITY - DB column is autoincremented

- best for mysql, H2 db
- DB generates the ID on INSERT, hibernate reads it back

2. SEQUENCE - DB Sequence object

- best for postgresql, oracle
- hibernate calls nextVal('seq') before INSERT

3. TABLE - a dedicated table stores the next ID value
- maximum portability - works for any db
- bad performance - extra db read

4. AUTO - hibernate autopicks the best strategy for dialect
- good for prototyping
- risk - may differ across databases

*/