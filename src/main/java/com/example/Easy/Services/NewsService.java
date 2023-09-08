package com.example.Easy.Services;

import com.example.Easy.Entities.NewsEntity;
import com.example.Easy.Entities.RecordsEntity;
import com.example.Easy.Entities.UserEntity;
import com.example.Easy.Mappers.NewsMapper;
import com.example.Easy.Models.NewsDTO;
import com.example.Easy.Models.UserDTO;
import com.example.Easy.Repository.NewsCategoryRepository;
import com.example.Easy.Repository.NewsRepository;
import com.example.Easy.Repository.RecordsRepository;
import com.example.Easy.Repository.UserRepository;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
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
import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsService {
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


    public Page<NewsDTO> getAllNews(Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        return newsRepository.findAll(pageRequest).map(newsMapper::toNewsDTO);
    }
    public Page<NewsDTO> getNewsByCategoryName(String category, Integer pageNumber, Integer pageSize, String sortBy) {
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);
        List<NewsDTO> newsEntities = newsCategoryRepository.findByname(category).getNews()
                .stream().map(newsMapper::toNewsDTO).toList();
        return new PageImpl<NewsDTO>(pagedListHolderFromRequest(pageRequest,newsEntities).getPageList());
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
        return newsRepository.findByTitle(title,pageRequest).map(newsMapper::toNewsDTO);
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

    public Page<NewsDTO> getRecommendedNews(UUID userId,Integer pageNumber, Integer pageSize, String sortBy){
        List<NewsDTO> newsDTOS = new LinkedList<>();
        Map<String,Integer> rep = new HashMap<>();
        userService.getUserRecordsById(userId,null,null,"repeatedRead")
                .stream().forEach(x->{
                    int val = x.getRepeatedRead();
                    if(x.isPostlike())
                        val+=1;
                    if(x.isPostbookmark())
                        val+=2;
                    Integer oldval = rep.put(x.getNewsCategory().getName(),val);
                    if(oldval!=null)
                        rep.put(x.getNewsCategory().getName(),oldval+val);
                });
        for(String category:rep.keySet()){
            Integer count = (int) (rep.get(category)*0.2)+1;
            List<NewsDTO> newsByCategory = getNewsByCategoryName(category,null,count,null).stream().toList();
            newsDTOS.addAll(newsByCategory);
        }
        PageRequest pageRequest = buildPageRequest(pageNumber,pageSize,sortBy);

        return new PageImpl<>(pagedListHolderFromRequest(pageRequest,newsDTOS).getPageList());
    }
    //Upload image to firebase storage
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
    //Convert to Page
    private PagedListHolder<NewsDTO> pagedListHolderFromRequest(PageRequest pageRequest,List<NewsDTO> list){
        PagedListHolder<NewsDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(),true,true));
    return pagedListHolder;
    }
    //Like and Bookmark function
    public String likePost(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElse(null);
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElse(null);
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);
        records.setPostlike(true);
        recordsRepository.save(records);
        return "liked";
    }
    public String unlikePost(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElse(null);
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElse(null);
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);
        records.setPostlike(false);
        recordsRepository.save(records);
        return "unliked";
    }
    public String bookmark(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElse(null);
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElse(null);
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);
        records.setPostbookmark(true);
        recordsRepository.save(records);
        return "bookmarked";
    }
    public String removeBookmark(UUID newsId, UserDTO userDTO) {
        NewsEntity news = newsRepository.findById(newsId).orElse(null);
        UserEntity user = userRepository.findById(userDTO.getUserId()).orElse(null);
        RecordsEntity records = recordsRepository.findByUserAndNews(user,news);
        records.setPostbookmark(false);
        recordsRepository.save(records);
        return "removed bookmark";
    }
    public int getLikes(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy) {
        //default value is for newsEntity, so we need to set it for RecordsEntity
        NewsEntity news = newsRepository.findById(newsId).orElse(null);
        return recordsRepository.findByNewsAndPostlike(news, true).size();
    }
    public int getBookmarks(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy) {
        //default value is for newsEntity, so we need to set it for RecordsEntity

        NewsEntity news = newsRepository.findById(newsId).orElse(null);
        return recordsRepository.findByNewsAndPostbookmark(news, true).size();
    }
}
