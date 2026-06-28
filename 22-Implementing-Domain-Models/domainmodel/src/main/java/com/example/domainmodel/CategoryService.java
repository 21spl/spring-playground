package com.example.domainmodel;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CategoryService {
    
    private final CategoryRepository categoryRepository;

    // constructor injection
    public CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Transactional
    public Category createRootCategory(String name){
        Category category = new Category(name);
        return categoryRepository.save(category);
    }
    
    @Transactional
    public Category createChildCategory(String name, Long parentId){

        // step 1: Find the parent by Id - if not exists, throw an exception
        Category parent = categoryRepository.findById(parentId)
            .orElseThrow(()-> new IllegalArgumentException(
                "Parent category not found: " + parentId));

        // step 2: Now create the child category
        Category category = new Category(name, parent);
        // save the child category
        return categoryRepository.save(category);
    }

    // Business rule: a category can only be deleted if its has no child
    @Transactional
    public void deleteCategory(Long categoryId){
        Category category = categoryRepository.findById(categoryId)
            .orElseThrow(() -> new IllegalArgumentException("Category not found: " + categoryId));
        
        if(!category.getChildren().isEmpty()){
            throw new IllegalStateException(
                "Cannot delete category" + category.getName() + 
                " - it has " + category.getChildren().size() + " child categories");   
        }

        categoryRepository.delete(category);
    }

    // why aren't we using @Transactional annotation here
    // use @Transaction for making any insert/update/delete - basically modifying the db state,
    // in the following method
    public List<Category> getRootCategories(){
        return categoryRepository.findByParentIsNull(); 
    }
}
