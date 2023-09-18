package com.example.Easy.controllers;

import com.example.Easy.models.NewsCategoryDTO;
import com.example.Easy.services.NewsCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class NewsCategoryController {

    private final NewsCategoryService newsCategoryService;
    @GetMapping("/hierarchy")
    public Map<String, List<?>> getCategories() {
        return newsCategoryService.getCategoriesHierarchy();
    }


    @GetMapping("/all-hierarchy")
    public List<NewsCategoryDTO> getAllCategories(){
        return newsCategoryService.getAllCategories();
    }

    @GetMapping("/{categoryId}")
    public NewsCategoryDTO getCategoryById(@PathVariable("categoryId") Long categoryId){
        return  newsCategoryService.getNewsCategoryById(categoryId);
    }
    @PostMapping("/new")
    public ResponseEntity<?> addCategory(@RequestBody NewsCategoryDTO categoryDTO){
        newsCategoryService.addNewCategory(categoryDTO);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


}
