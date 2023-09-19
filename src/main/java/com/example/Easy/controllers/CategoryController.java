package com.example.Easy.controllers;

import com.example.Easy.models.response.CategoryResponse;
import com.example.Easy.requests.CreateCategoryRequest;
import com.example.Easy.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService newsCategoryService;
    @GetMapping
    public ResponseEntity<List<CategoryResponse>> getAllCategories(){
        return new ResponseEntity<>(newsCategoryService.getAllCategories()
                .stream().map(CategoryResponse::new)
                .collect(Collectors.toList()),HttpStatus.OK);
    }
    @GetMapping("/category")
    public Map<String,List> getCategories(){
        return newsCategoryService.getCategoriesHierarchy();
    }
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> getCategoryById(@PathVariable("categoryId") Long categoryId){
        return  new ResponseEntity<>(new CategoryResponse(newsCategoryService.getNewsCategoryById(categoryId)),HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<CategoryResponse> addCategory(@RequestBody CreateCategoryRequest createCategoryRequest){
        return new ResponseEntity<>(new CategoryResponse(newsCategoryService.addCategory(createCategoryRequest)),HttpStatus.CREATED);
    }
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<CategoryResponse> deleteCategory(@PathVariable("categoryId") Long categoryId){
        return new ResponseEntity<>(new CategoryResponse(newsCategoryService.deleteCategory(categoryId)),HttpStatus.OK);
    }




}
