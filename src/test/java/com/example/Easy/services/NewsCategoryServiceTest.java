package com.example.Easy.services;

import com.example.Easy.mappers.CategoryMapper;
import com.example.Easy.models.CategoryDTO;
import com.example.Easy.requests.CreateCategoryRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class NewsCategoryServiceTest {
    @Autowired
    CategoryService newsCategoryService;

    @Autowired
    CategoryMapper newsCategoryMapper;


    @Test
    void bootstrap(){
        addCategoryTest();
        addSubCategory();
        subsub();
    }

    @Test
    void addCategoryTest(){
        CreateCategoryRequest cat1 = CreateCategoryRequest.builder()
                .name("cat1")
                .parent(null)
                .build();
        CreateCategoryRequest cat2 = CreateCategoryRequest.builder()
                .name("cat2")
                .parent(null)
                .build();
        CreateCategoryRequest cat3 = CreateCategoryRequest.builder()
                .name("cat3")
                .parent(null)
                .build();

        newsCategoryService.addCategory(cat1);
        newsCategoryService.addCategory(cat2);
    }
    @Test
    void addSubCategory(){


    }
    @Test
    void subsub(){
    }

    @Test
    void getAllCategories(){
        List<CategoryDTO> newsCategoryDTOS = newsCategoryService.getAllCategories();
        System.out.println(newsCategoryDTOS);
    }
    @Test
    void getAllRoots(){

    }

    @Test
    void getCategoryById(){
        CategoryDTO newsCategoryDTO = newsCategoryService.getNewsCategory(4L);


    }





}