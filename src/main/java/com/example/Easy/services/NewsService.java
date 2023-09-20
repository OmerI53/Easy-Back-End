package com.example.Easy.services;

import com.example.Easy.dao.NewsDao;
import com.example.Easy.entities.NewsCategoryEntity;
import com.example.Easy.entities.NewsEntity;
import com.example.Easy.entities.RecordsEntity;
import com.example.Easy.entities.UserEntity;
import com.example.Easy.mappers.NewsMapper;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.NewsCategoryRepository;
import com.example.Easy.repository.NewsRepository;
import com.example.Easy.repository.RecordsRepository;
import com.example.Easy.repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.support.PagedListHolder;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;



@Service
@RequiredArgsConstructor
public class NewsService implements NewsDao {
    private final NewsCategoryRepository newsCategoryRepository;


    private final UserRepository userRepository;
    private final UserService userService;

    private final NewsRepository newsRepository;
    private final NewsMapper newsMapper;

    private final RecordsRepository recordsRepository;

    private final static int DEFAULT_PAGE=0;
    private final static int DEFAULT_PAGE_SIZE=25;
    private final static String DEFAULT_SORT="creationTime";
    private final static String DOWNLOAD_URL ="https://firebasestorage.googleapis.com/v0/b/easy-newss.appspot.com/o/%s?alt=media&";




    public Page<NewsDTO> get(Integer pageNumber, Integer pageSize, String sortBy) {
        if(pageNumber==null && pageSize==null || sortBy==null){
            System.out.println("Some values are null!");
            return null;
        }else{
            PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
            return newsRepository.findAll(pageRequest).map(NewsMapper::toNewsDTO);
        }
    }

    public Page<NewsDTO> get(String category, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);

        List<NewsDTO> newsEntities = newsCategoryRepository.findByname(category)
                .getNews()
                .stream()
                .map(NewsMapper::toNewsDTO)
                .toList();

        return new PageImpl<>(newsEntities, pageRequest, newsEntities.size());
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy) {
        pageNumber = pageNumber != null && pageNumber > 0 ? pageNumber - 1 : DEFAULT_PAGE;
        pageSize = pageSize != null && pageSize > 0 && pageSize <= 100 ? pageSize : DEFAULT_PAGE_SIZE;
        sortBy = sortBy != null && !sortBy.isEmpty() ? sortBy : DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(sortBy));
        return PageRequest.of(pageNumber, pageSize, sort);
    }

    public Page<NewsDTO> getByTitle(String title, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        return newsRepository.findByTitle(title, pageRequest)
                .map(NewsMapper::toNewsDTO);
    }

    public NewsDTO get(UUID newsId) {
        return newsRepository.findById(newsId)
                .map(NewsMapper::toNewsDTO)
                .orElseThrow(() -> new RuntimeException("newsId not found!"));
    }

    public void post(NewsDTO newsDTO) {
        UserEntity author = userRepository.findById(newsDTO.getAuthor().getUserId()).orElseThrow(() -> new RuntimeException("author not found!"));
        NewsCategoryEntity category = newsCategoryRepository.findById(newsDTO.getCategory().getCategoryId()).orElseThrow(() -> new RuntimeException("category not found!"));

        if (author == null && category == null) {
            System.out.println("Author and category not found!");
        }else{
            NewsEntity news = NewsEntity.builder()
                    .text(newsDTO.getText())
                    .image(newsDTO.getImage())
                    .author(author)
                    .category(category)
                    .creationTime(LocalDateTime.now())
                    .title(newsDTO.getTitle())
                    .build();
            newsRepository.save(news);
        }
    }

    public void delete(UUID newsUUID) {
        if(newsUUID==null){
            System.out.println("NewsID is null!");
        }else{
            newsRepository.deleteById(newsUUID);
        }

    }

    public Page<NewsDTO> getRecommendedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        Map<String, Integer> rep = new HashMap<>();

        userService.getRecords(userId, null, null, "repeatedRead")
                .forEach(x -> {
                    int val = x.getRepeatedRead();
                    if (x.isPostlike()) val += 1;
                    if (x.isPostbookmark()) val += 2;
                    rep.merge(x.getNewsCategory().getName(), val, Integer::sum);
                });

        List<NewsDTO> newsDTOS = rep.entrySet().stream()
                .flatMap(entry -> {
                    int count = (int) (entry.getValue() * 0.2) + 1;
                    return get(entry.getKey(), null, count, null).stream();
                })
                .collect(Collectors.toList());

        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);

        return new PageImpl<>(pagedListHolderFromRequest(pageRequest, newsDTOS).getPageList());
    }

    //Upload image to firebase storage

    public String uploadImage(MultipartFile file) throws IOException {
        String imageName = generateFileName(file.getOriginalFilename());
        BlobId blobId = BlobId.of("easy-newss.appspot.com", imageName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                .setContentType(file.getContentType())
                .build();

        StorageOptions storageOptions = StorageOptions.newBuilder()
                .setCredentials(GoogleCredentials.fromStream(new ClassPathResource("serviceAccountKey.json").getInputStream()))
                .build();
        Storage storage = storageOptions.getService();

        storage.create(blobInfo, file.getInputStream());

        return String.format(DOWNLOAD_URL, imageName);
    }

    private String generateFileName(String originalFileName) {
        return UUID.randomUUID().toString() + "." + getFileExtension(originalFileName);
    }

    private String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }

    //Convert to Page
    private PagedListHolder<NewsDTO> pagedListHolderFromRequest(PageRequest pageRequest, List<NewsDTO> list) {
        PagedListHolder<NewsDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        return pagedListHolder;
    }

    public String likePost(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new RuntimeException("userId not found!"));
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);

        if (news == null || user == null) {
            return "Like post failed (null exception)";
        }else{
            records.setPostlike(true);
            recordsRepository.save(records);
            return "liked";
        }
    }

    public String unlikePost(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new RuntimeException("userId not found!"));
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);

        if (news == null || user == null) {
            return "Like not removed";
        }else{
            records.setPostlike(false);
            recordsRepository.save(records);
            return "unliked";
        }
    }

    public String bookmark(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new RuntimeException("userId not found!"));
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);

        if (news == null || user == null) {
            return "Bookmark not added";
        }else{
            records.setPostbookmark(true);
            recordsRepository.save(records);
            return "bookmarked";
        }

    }

    public String removeBookmark(UUID newsId, UserDTO userDTO) {

        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElseThrow(() -> new RuntimeException("userId not found!"));
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);

        if (news == null || user == null) {
            return "Bookmark not removed";
        }else{
            records.setPostbookmark(false);
            recordsRepository.save(records);
            return "removed bookmark";
        }
    }

    public int getLikes(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy) {
        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));
        if (news == null) {
            System.out.println("newsId null");
            return 0;
        }else{
            return recordsRepository.findByNewsAndPostlike(news, true).size();
        }
        
    }

    public int getBookmarks(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy) {
        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));
        if (news == null) {
            System.out.println("newsId null");
            return 0;
        }else{
            return recordsRepository.findByNewsAndPostbookmark(news, true).size();
        }

    }

    private int getRecordCountByNewsAndPostType(UUID newsId, boolean postType) {
        NewsEntity news = newsRepository.findById(newsId).orElseThrow(() -> new RuntimeException("newsId not found!"));

        if (news == null) {
            return 0;
        }else{
            return recordsRepository.findByNewsAndPostlike(news, true).size();
        }
    }


    public Page<NewsDTO> getFollowingNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        UserEntity user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("userId not found!"));

        if (user == null) {
            return Page.empty();
        }

        List<NewsEntity> news = user.getFollowing()
                .stream()
                .flatMap(x -> x.getNews().stream())
                .toList();

        List<NewsDTO> newsDTOs = news.stream()
                .map(NewsMapper::toNewsDTO)
                .collect(Collectors.toList());

        return new PageImpl<>(newsDTOs, pageRequest, newsDTOs.size());
    }
}
