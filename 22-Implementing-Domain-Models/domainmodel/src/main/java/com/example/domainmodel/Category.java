package com.example.domainmodel;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    // self- referential many to one: many categories share one parent
    @ManyToOne
    @JoinColumn(name="PARENT_ID") // FK column in the CATEGORY table
    private Category parent; // null for root categories

    // inverse side of the parent relationship - one category can have multiple children
    @OneToMany(mappedBy="parent")
    // we are assigning HashSet, to prevent NullPointerException for some new chilren
    private Set<Category> children = new HashSet<>();

    
    // no-arg constructor
    public Category(){}

    // arg constructor 1
    public Category(String name){
        this.name = name;
    }

    // arg constructor 2
    public Category(String name, Category parent){
        this.name = name;
        this.parent = parent;
        parent.getChildren().add(this); // keep both sides in sync
    }

    // helper: add a child Category
    // for Collections type we don't write setChildren
    public void addChild(Category child){
        child.parent = this;
        this.children.add(child);
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Category getParent() {
        return parent;
    }

    public Set<Category> getChildren() {
        return children;
    }

    // method to check root category
    public boolean isRoot(){
        return parent==null;
    }

    @Override
    public String toString() {
        return "Category [id=" + id + ", name=" + name + "]";
    }
}

