package com.example.Easy.Services;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Repository.NewsCategoryRepository;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NewsService {
    private final NewsCategoryRepository newsCategoryRepository;
    private final UserRepository userRepository;
    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    private final static int DEFAULT_PAGE=0;
    private final static int DEFAULT_PAGE_SIZE=25;
    private final static String DEFAULT_SORT="creationTime";


    public Page<NewsDTO> getAllNews(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return newsRepository.findAll(pageRequest).map(newsMapper::toNewsDTO);
    }
    public Page<NewsDTO> getNewsByCategoryId(String category,Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        List<NewsDTO> newsEntities = newsCategoryRepository.findByname(category).getNews()
                .stream().map(newsMapper::toNewsDTO).collect(Collectors.toList());
        return new PageImpl<>(newsEntities,pageRequest,newsEntities.size());
    }
    public PageRequest buildPageRequest(Integer pageNumber,Integer pageSize,String sortBy){
        // if not initilized set it to default

        int queryPageNumber;
        int queryPageSize;
        String querySortBy;

        if(pageNumber!=null && pageNumber>0)
                queryPageNumber = pageNumber-1; //it is 0 indexed, for first page, number is 1.
        else
            queryPageNumber=DEFAULT_PAGE;

        if(pageSize==null)
            queryPageSize=DEFAULT_PAGE_SIZE;
        else
            queryPageSize=pageSize;
        //setting a max size
        if(queryPageSize>100)
            queryPageSize=100;

        if(sortBy!=null && !sortBy.equals(""))
            querySortBy=sortBy;
        else
            querySortBy=DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(querySortBy));
        return PageRequest.of(queryPageNumber,queryPageSize,sort);

    }

    public Page<NewsDTO> getNewsByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        List<NewsDTO> newsDTOS =newsRepository.findByTitle(title)
                .stream().map(newsMapper::toNewsDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(newsDTOS,pageRequest,newsDTOS.size());
    }
    public NewsDTO getNewsById(UUID newsId) {
        return newsMapper.toNewsDTO(newsRepository.findById(newsId).orElse(null));
    }
    public void postNews(NewsDTO newsDTO) {
        UserEntity author = userRepository.findById(newsDTO.getAuthor().getUserId()).orElse(null);
        NewsEntity news = NewsEntity.builder()
                .text(newsDTO.getText())
                .image(newsDTO.getImage())
                .author(author)
                .category(newsCategoryRepository.findById(newsDTO.getCategory().getCategoryId()).orElse(null))
                .creationTime(LocalDateTime.now())
                .title(newsDTO.getTitle())
                .build();
        newsRepository.save(news);
    }

    public void deletePostById(UUID newsUUID) {
        newsRepository.deleteById(newsUUID);
    }

    private final static String DOWNLOAD_URL ="https://firebasestorage.googleapis.com/v0/b/easy-newss.appspot.com/o/%s?alt=media&";
    public String uploadImage(MultipartFile file) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        BlobId blobId = BlobId.of("easy-newss.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream()))
                .build().getService();

        storage.create(blobInfo, file.getInputStream());
        return String.format(DOWNLOAD_URL,imageName);
    }
    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getExtension(originalFileName);
    }
    private String getExtension(String originalFileName) {
        return StringUtils.getFilenameExtension(originalFileName);
    }


}
