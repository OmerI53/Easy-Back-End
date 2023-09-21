package com.example.Easy.services;

import com.example.Easy.models.CategoryDTO;
import com.example.Easy.repository.dao.CategoryDao;
import com.example.Easy.requests.CreateCategoryRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryDao categoryDao;

    @Transactional
    public CategoryDTO addCategory(CreateCategoryRequest createCategoryRequest) {
        CategoryDTO parent = categoryDao.get(createCategoryRequest.getParent());
        if (parent == null) {
            parent = CategoryDTO.builder()
                    .name(createCategoryRequest.getParent())
                    .build();
            parent = categoryDao.save(parent);
        }
        CategoryDTO category = CategoryDTO.builder()
                .name(createCategoryRequest.getName())
                .parent(parent)
                .build();
        categoryDao.save(category);
        return category;
    }

    public CategoryDTO getNewsCategory(Long categoryId) {
        return categoryDao.get(categoryId);
    }
    public CategoryDTO getNewsCategory(String name) {
        return categoryDao.get(name);
    }

    public List<CategoryDTO> getAllCategories() {
        return categoryDao.getAll();
    }

    public CategoryDTO deleteCategory(Long categoryId) {
        CategoryDTO categoryDTO = getNewsCategory(categoryId);
        categoryDao.delete(categoryDTO);
        return categoryDTO;
    }


    /*
        List<Category> categories  = ...
        Catego
     */
    //TODO refactor
    /*
    public Map<String, List> getCategoriesHierarchy() {
        List<CategoryDTO> roots = categoryDao.getAll(null);
        Map<String, List> map = new HashMap<>();
        for (CategoryDTO root : roots) {
            if (root.getChildren().isEmpty())
                map.put(root.getName(), null);
            else {
                map.put(root.getName(), new LinkedList());
                for (CategoryEntity children : root.getChildren()) {
                    map.get(root.getName()).add(subcats(children));
                }
            }
        }
        return map;
    }

    public Map<String, List> subcats(CategoryEntity node) {
        Map<String, List> map = new HashMap<>();
        if (node.getChildren().isEmpty()) {
            map.put(node.getName(), null);
            return map;
        }
        map.put(node.getName(), new LinkedList());
        for (CategoryEntity children : node.getChildren()) {
            map.get(node.getName()).add(subcats(children));
        }
        return map;
    }
     */


}
