package com.example.Easy.repository.dao;

import com.example.Easy.mappers.CategoryMapperImpl;
import com.example.Easy.models.CategoryDTO;
import com.example.Easy.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class CategoryDao implements Dao<CategoryDTO> {

    private final CategoryRepository categoryRepository;
    private final CategoryMapperImpl categoryMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public CategoryDTO get(UUID id) {
        return null; //there is no category with id type UUID
    }
    public CategoryDTO get(Long categoryId) {
        return categoryMapper.toCategoryDTO(categoryRepository.findById(categoryId)
                .orElseThrow(() -> new NullPointerException(source.getMessage("category.notfound", null, LocaleContextHolder.getLocale()))));
    }
    public CategoryDTO get(String name) {
        return categoryMapper.toCategoryDTO(categoryRepository.findByName(name));
    }

    @Override
    public List<CategoryDTO> getAll() {
        return categoryRepository.findAll()
                .stream().map(categoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        try {
            return categoryMapper.toCategoryDTO(categoryRepository.save(categoryMapper.toCategoryEntity(categoryDTO)));
        } catch (
                DataIntegrityViolationException e) {
            throw new NullPointerException(source.getMessage("category.duplicate.name", null, LocaleContextHolder.getLocale()));
        }
    }

    @Override
    public CategoryDTO update(CategoryDTO categoryDTO) {
        return null;
    }

    @Override
    public CategoryDTO delete(CategoryDTO categoryDTO) {
        return null;
    }


    public List<CategoryDTO> getAll(CategoryDTO categoryDTO) {
        return categoryRepository.findByparent(categoryMapper.toCategoryEntity(categoryDTO))
                .stream().map(categoryMapper::toCategoryDTO)
                .collect(Collectors.toList());
    }
}
