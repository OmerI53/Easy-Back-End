package com.example.Easy.Services;

import com.example.Easy.Mappers.NewsCategoryMapper;
import com.example.Easy.Models.NewsCategoryDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
@SpringBootTest
class NewsCategoryServiceTest {
    @Autowired
    NewsCategoryService newsCategoryService;

    @Autowired
    NewsCategoryMapper newsCategoryMapper;


    @Test
    void bootstrap(){
        addCategoryTest();
        addSubCategory();
        subsub();
    }

    @Test
    void addCategoryTest(){
        NewsCategoryDTO cat1 = NewsCategoryDTO.builder()
                .name("cat1")
                .parent(null)
                .build();
        NewsCategoryDTO cat2 = NewsCategoryDTO.builder()
                .name("cat2")
                .parent(null)
                .build();
        NewsCategoryDTO cat3 = NewsCategoryDTO.builder()
                .name("cat3")
                .parent(null)
                .build();

        newsCategoryService.addNewCategory(cat1);
        newsCategoryService.addNewCategory(cat2);
    }
    @Test
    void addSubCategory(){
        NewsCategoryDTO cat1 = newsCategoryService.getNewsCategoryById(1L);
        NewsCategoryDTO cat2 = newsCategoryService.getNewsCategoryById(2L);




    }
    @Test
    void subsub(){
    }

    @Test
    void getAllCategories(){
        List<NewsCategoryDTO> newsCategoryDTOS = newsCategoryService.getAllCategories();
        System.out.println(newsCategoryDTOS);
    }
    @Test
    void getAllRoots(){

    }

    @Test
    void getCategoryById(){
        NewsCategoryDTO newsCategoryDTO = newsCategoryService.getNewsCategoryById(4L);


    }





}