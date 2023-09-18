package com.example.Easy.services;

import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.mappers.NewsCategoryMapper;
import com.example.Easy.models.NewsCategoryDTO;
import com.example.Easy.repository.NewsCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsCategoryService {

    private final NewsCategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;

    public void add(NewsCategoryDTO categoryDTO) {
        NewsCategoryEntity parentCategory = null;
        if(categoryDTO.getParent() != null){
            parentCategory = newsCategoryRepository.findByname(categoryDTO.getParent().getName());
        }
        NewsCategoryEntity newsCategoryEntity = new NewsCategoryEntity();
        newsCategoryEntity.setName(categoryDTO.getName());
        newsCategoryEntity.setParent(parentCategory);

        newsCategoryRepository.save(newsCategoryEntity);
    }

    public NewsCategoryDTO get(Long categoryId) {
        return newsCategoryRepository.findById(categoryId)
                .map(newsCategoryMapper::toNewsCategoryDTO)
                .orElseThrow(() -> new RuntimeException("news-category not found!"));
    }

    public List<NewsCategoryDTO> get(){
        return newsCategoryRepository.findAll()
                .stream()
                .map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }

    public List<NewsCategoryDTO> getAllRoots() {
        return newsCategoryRepository.findByparent(null)
                .stream()
                .map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }

    public Map<String,List<?>> getCategoriesHierarchy(){
        List<NewsCategoryEntity> roots = newsCategoryRepository.findByparent(null);
        Map<String,List<?>> map = new HashMap<>();

        for(NewsCategoryEntity root : roots){
            if(root.getChildren().isEmpty())
                map.put(root.getName(),null);
            else {
                map.put(root.getName(),new LinkedList<>());
                for(NewsCategoryEntity children : root.getChildren()){
                    map.get(children.getName());
                }
            }
        }
        return map;
    }
    public Map<String, List<?>> subcats(NewsCategoryEntity node) {
        Map<String, List<?>> map = new HashMap<>();

        if (node.getChildren().isEmpty()) {
            map.put(node.getName(), null);
            return map;
        } else {
            List<Map<String, List<?>>> childList = new LinkedList<>();
            for (NewsCategoryEntity child : node.getChildren()) {
                childList.add(subcats(child));
            }
            map.put(node.getName(), childList);
        }

        return map;
    }

}
