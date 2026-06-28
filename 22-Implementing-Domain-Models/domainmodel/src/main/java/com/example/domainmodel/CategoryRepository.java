package com.example.domainmodel;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long>{
    
    List<Category> findByParentIsNull(); 

    List<Category> findByName(String name);

    List<Category> findByParent(Category parent);
}


/*
we do not add @Repository annotation on interfaces that extend JpaRepository in spring data jpa

Spring data jpa automatically detects any interface extends Repository or JpaRepository because of automatic
component scanning

we only need to ensure that the main application class (or configuration class is annotated with 
@EnableJpaRepositories
*/