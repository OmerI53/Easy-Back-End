package com.example.Easy.services;

import com.example.Easy.entities.CategoryEntity;
import com.example.Easy.mappers.NewsCategoryMapper;
import com.example.Easy.models.CategoryDTO;
import com.example.Easy.repository.CategoryRepository;
import com.example.Easy.requests.CreateCategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;
    private final ResourceBundleMessageSource source;
    @Transactional
    public CategoryDTO addCategory(CreateCategoryRequest createCategoryRequest) {
        CategoryDTO parent = newsCategoryMapper
                .toNewsCategoryDTO(newsCategoryRepository.findByName(createCategoryRequest.getParent()));

        if(parent==null){
         parent = CategoryDTO.builder()
                 .name(createCategoryRequest.getParent())
                 .build();
         try {
             parent = newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.save(newsCategoryMapper.toNewsCategoryEntity(parent)));
         }catch (DataIntegrityViolationException e){
             throw new NullPointerException(source.getMessage("category.duplicate.name",null,LocaleContextHolder.getLocale()));
         }
         }

        CategoryDTO category = CategoryDTO.builder()
                .name(createCategoryRequest.getName())
                .parent(parent)
                .build();
        try {
            newsCategoryRepository.save(newsCategoryMapper.toNewsCategoryEntity(category));
        }catch (DataIntegrityViolationException e){
            throw new NullPointerException(source.getMessage("category.duplicate.name",null,LocaleContextHolder.getLocale()));
        }
        return category;
    }

    public CategoryDTO getNewsCategoryById(Long categoryId) {
        return newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.findById(categoryId)
                .orElseThrow(()-> new NullPointerException(source.getMessage("category.notfound",null, LocaleContextHolder.getLocale()))));
    }
    public CategoryDTO getCategoryByName(String category) {
        return newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.findByName(category));
    }

    public List<CategoryDTO> getAllCategories() {
        return newsCategoryRepository.findAll()
                .stream().map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }
    public CategoryDTO deleteCategory(Long categoryId) {
        CategoryDTO categoryDTO = getNewsCategoryById(categoryId);
        newsCategoryRepository.deleteById(categoryId);
        return categoryDTO;
    }
    //get all categories
    public List<CategoryDTO> getAllRoots() {
        return newsCategoryRepository.findByparent(null)
                .stream().map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }
    //convert to a hierechy map
    /*
        List<Category> categories  = ...
        Catego
     */
    public Map<String,List> getCategoriesHierarchy(){
        List<CategoryEntity> roots = newsCategoryRepository.findByparent(null);
        Map<String,List> map = new HashMap<>();
        for(CategoryEntity root : roots){
            if(root.getChildren().isEmpty())
                map.put(root.getName(),null);
            else {
                map.put(root.getName(),new LinkedList());
                for(CategoryEntity children : root.getChildren()){
                    map.get(root.getName()).add(subcats(children));
                }
            }
        }
        return map;
    }
    public Map<String,List> subcats(CategoryEntity node){
        Map<String,List> map = new HashMap<>();
        if(node.getChildren().isEmpty()){
            map.put(node.getName(),null);
            return map;
        }
        map.put(node.getName(),new LinkedList());
        for(CategoryEntity children : node.getChildren()){
            map.get(node.getName()).add(subcats(children));
        }
        return map;
    }


}
