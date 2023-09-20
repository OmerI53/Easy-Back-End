package com.example.Easy.dao;

import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.models.NewsCategoryDTO;

import java.util.List;
import java.util.Map;

public interface NewsCategoryDao {
    public void add(NewsCategoryDTO categoryDTO);
    public NewsCategoryDTO get(Long categoryId);
    public List<NewsCategoryDTO> get();
    public List<NewsCategoryDTO> getAllRoots();
    public Map<String,List<?>> getCategoriesHierarchy();
    public Map<String, List<?>> subcats(NewsCategoryEntity node);
}
