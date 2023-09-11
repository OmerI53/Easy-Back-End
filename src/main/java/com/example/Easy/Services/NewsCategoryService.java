package com.example.Easy.Services;

import com.example.Easy.Entities.NewsCategoryEntity;
import com.example.Easy.Mappers.NewsCategoryMapper;
import com.example.Easy.Models.NewsCategoryDTO;
import com.example.Easy.Repository.NewsCategoryRepository;
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

    public void addNewCategory(NewsCategoryDTO categoryDTO) {
        NewsCategoryEntity parent = newsCategoryRepository.findByname(categoryDTO.getParent().getName());
        if(parent==null){
         newsCategoryRepository.save(newsCategoryMapper.toNewsCategoryEntity(categoryDTO.getParent()));
        }
        NewsCategoryEntity newsCategoryEntity = NewsCategoryEntity.builder()
                .name(categoryDTO.getName())
                .parent(newsCategoryMapper.toNewsCategoryEntity(categoryDTO.getParent()))
                .build();
        newsCategoryRepository.save(newsCategoryEntity);
    }

    public NewsCategoryDTO getNewsCategoryById(Long categoryId) {
        return newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.findById(categoryId).orElse(null));
    }

    public List<NewsCategoryDTO> getAllCategories() {
        return newsCategoryRepository.findAll()
                .stream().map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }

    public List<NewsCategoryDTO> getAllRoots() {
        return newsCategoryRepository.findByparent(null)
                .stream().map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }

    public Map<String,List> getCategoriesHierarchy(){
        List<NewsCategoryEntity> roots = newsCategoryRepository.findByparent(null);
        Map<String,List> map = new HashMap<>();
        for(NewsCategoryEntity root : roots){
            if(root.getChildren().isEmpty())
                map.put(root.getName(),null);
            else {
                map.put(root.getName(),new LinkedList());
                for(NewsCategoryEntity children : root.getChildren()){
                    map.get(root.getName()).add(subcats(children));
                }
            }
        }
        return map;
    }
    public Map<String,List> subcats(NewsCategoryEntity node){
        Map<String,List> map = new HashMap<>();
        if(node.getChildren().isEmpty()){
            map.put(node.getName(),null);
            return map;
        }
        map.put(node.getName(),new LinkedList());
        for(NewsCategoryEntity children : node.getChildren()){
            map.get(node.getName()).add(subcats(children));
        }
        return map;
    }
}
