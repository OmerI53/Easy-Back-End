package com.example.Easy.repository.dao;

import com.example.Easy.mappers.NewsCategoryMapper;
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

    private final CategoryRepository newsCategoryRepository;
    private final NewsCategoryMapper newsCategoryMapper;
    private final ResourceBundleMessageSource source;

    @Override
    public CategoryDTO get(UUID id) {
        return null; //there is no category with id type UUID
    }
    public CategoryDTO get(Long categoryId) {
        return newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.findById(categoryId)
                .orElseThrow(() -> new NullPointerException(source.getMessage("category.notfound", null, LocaleContextHolder.getLocale()))));
    }
    public CategoryDTO get(String name) {
        return newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.findByName(name));
    }

    @Override
    public List<CategoryDTO> getAll() {
        return newsCategoryRepository.findAll()
                .stream().map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryDTO save(CategoryDTO categoryDTO) {
        try {
            return newsCategoryMapper.toNewsCategoryDTO(newsCategoryRepository.save(newsCategoryMapper.toNewsCategoryEntity(categoryDTO)));
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
        return newsCategoryRepository.findByparent(newsCategoryMapper.toNewsCategoryEntity(categoryDTO))
                .stream().map(newsCategoryMapper::toNewsCategoryDTO)
                .collect(Collectors.toList());
    }
}
