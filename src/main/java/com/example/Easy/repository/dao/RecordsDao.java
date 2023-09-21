package com.example.Easy.repository.dao;

import com.example.Easy.mappers.RecordsMapper;
import com.example.Easy.models.RecordsDTO;
import com.example.Easy.repository.RecordsRepository;
import com.example.Easy.repository.specifications.RecordsSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class RecordsDao implements Dao<RecordsDTO> {
    private final RecordsRepository recordsRepository;
    private final RecordsMapper recordsMapper;
    private final ResourceBundleMessageSource source;
    @Override
    public RecordsDTO get(UUID id) {
        return recordsMapper.toRecordsDTO(recordsRepository.findById(id)
                        .orElseThrow(()->new NullPointerException(source.getMessage("records.notfound",null, LocaleContextHolder.getLocale()))));
    }
    public RecordsDTO get(UUID userId, UUID newsId) {
        return recordsMapper.toRecordsDTO(recordsRepository.findByUserAndNews(userId.toString(),newsId.toString()));
    }

    public List<RecordsDTO> getByUser(UUID userId,Boolean likes,Boolean bookmarks) {
        return recordsRepository.findAll(RecordsSpecifications.getSpecifiedRecords(userId,likes,bookmarks))
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<RecordsDTO> getAll() {
        return recordsRepository.findAll()
                .stream().map(recordsMapper::toRecordsDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecordsDTO save(RecordsDTO recordsDTO) {
        return recordsMapper.toRecordsDTO(recordsRepository.save(recordsMapper.toRecordsEntity(recordsDTO)));
    }

    @Override
    public RecordsDTO update(RecordsDTO recordsDTO) {
        return recordsMapper.toRecordsDTO(recordsRepository.save(recordsMapper.toRecordsEntity(recordsDTO)));
    }

    @Override
    public RecordsDTO delete(RecordsDTO recordsDTO) {
        return null;
    }

}
