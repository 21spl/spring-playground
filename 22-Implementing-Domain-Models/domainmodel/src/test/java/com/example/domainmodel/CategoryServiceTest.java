package com.example.domainmodel;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // rollback after each test - keeps DB clean
public class CategoryServiceTest {
    
    @Autowired
    private CategoryService categoryService;

    @Test
    void rootCategoryCreation(){
        Category electronics = categoryService.createRootCategory("Electronics");

        assertNotNull(electronics.getId());
        assertEquals("Electronics", electronics.getName());
        assertTrue(electronics.isRoot());
    }

    @Test
    void createCategoryHierarchy(){
        Category electronics = categoryService.createRootCategory("Electronics");
        Category phones = categoryService.createChildCategory("Phones", electronics.getId());
        Category laptops = categoryService.createChildCategory("Laptops", electronics.getId());

        assertEquals("Electronics", phones.getParent().getName());
        assertEquals("Electronics", laptops.getParent().getName());
    }

    @Test
    void cannotDeleteCategoryWithChildren(){

        Category electronics = categoryService.createRootCategory("Electronics");
        categoryService.createChildCategory("Phones", electronics.getId());

        // Business rule: show throw Electronics has children
        IllegalStateException ex = assertThrows(
            IllegalStateException.class, 
            () -> categoryService.deleteCategory(electronics.getId())
        );

        assertTrue(ex.getMessage().contains("child categories"));
    }

    @Test
    void canDeleteLeafCategory(){
        Category electronics = categoryService.createRootCategory("Electronics");
        Category phones = categoryService.createChildCategory("Phones", electronics.getId());

        // Phones is a leaf - no children - should succeed
        assertDoesNotThrow(() -> categoryService.deleteCategory(phones.getId()));
    }

}
