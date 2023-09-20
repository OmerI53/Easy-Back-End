package com.example.Easy.services;

import com.example.Easy.models.CategoryDTO;
import com.example.Easy.models.CommentDTO;
import com.example.Easy.models.NewsDTO;
import com.example.Easy.models.UserDTO;
import com.example.Easy.repository.dao.NewsDao;
import com.example.Easy.requests.CreateInteractionRequest;
import com.example.Easy.requests.CreateNewsRequest;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.support.MutableSortDefinition;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final UserService userService;
    private final NewsDao newsDao;
    private final CategoryService categoryService;
    private final RecordsService recordsService;
    private final ImageService imageService;
    private final ResourceBundleMessageSource source;

    private final static int DEFAULT_PAGE = 0;
    private final static int DEFAULT_PAGE_SIZE = 25;
    private final static String DEFAULT_SORT = "creationTime";

    public Page<NewsDTO> getAllNews(Integer pageNumber, Integer pageSize, String sortBy,String category, String title, String authorName) {
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);
        return new PageImpl<>(pagedListHolderFromRequest(pageRequest,newsDao.getAll(category,title,authorName)).getPageList());
    }

    public NewsDTO getNewsById(UUID newsId) {
        return newsDao.get(newsId);
    }

    @Transactional
    public NewsDTO postNews(CreateNewsRequest createNewsRequest) {
        UserDTO user = userService.getUser(createNewsRequest.getUserId());
        CategoryDTO category = categoryService.getCategoryByName(createNewsRequest.getCategory());
        String imageUrl;
        try {
            imageUrl = imageService.uploadImage(createNewsRequest.getImage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        NewsDTO news = NewsDTO.builder()
                .text(createNewsRequest.getText())
                .image(imageUrl)
                .author(user)
                .category(category)
                .creationTime(LocalDateTime.now())
                .title(createNewsRequest.getTitle())
                .build();

        return newsDao.save(news);

    }

    @Transactional
    public NewsDTO patchNews(UUID newsId, CreateNewsRequest createNewsRequest) {
        NewsDTO newsDTO = getNewsById(newsId);
        if(createNewsRequest.getCategory()!=null && !createNewsRequest.getCategory().equals(""))
            newsDTO.setCategory(categoryService.getCategoryByName(createNewsRequest.getCategory()));
        if(createNewsRequest.getTitle()!=null && !createNewsRequest.getTitle().equals(""))
            newsDTO.setTitle(createNewsRequest.getTitle());
        if(createNewsRequest.getText()!=null && !createNewsRequest.getText().equals(""))
            newsDTO.setText(createNewsRequest.getText());
        if(createNewsRequest.getImage()!=null) {
            try {
                newsDTO.setImage(imageService.uploadImage(createNewsRequest.getImage()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return newsDao.save(newsDTO);
    }

    @Transactional
    public NewsDTO deletePostById(UUID newsUUID) {
        NewsDTO newsDTO = getNewsById(newsUUID);
        newsDao.delete(newsDTO);
        return newsDTO;
    }

    @Transactional
    public void addComment(NewsDTO news, CommentDTO comment) {
        news.getComments().add(comment);
        newsDao.save(news);
    }

    public Page<CommentDTO> getComments(UUID newsId, Integer pageNumber, Integer pageSize, String sortBy) {
        NewsDTO news = getNewsById(newsId);
        PageRequest request = buildPageRequest(pageNumber, pageSize, sortBy);
        return new PageImpl<>(pagedListHolderFromRequestComments(request, news.getComments()).getPageList());

    }

    public Page<NewsDTO> getRecommendedNews(UUID userId, Integer pageNumber, Integer pageSize, String sortBy) {
        List<NewsDTO> newsDTOS = new LinkedList<>();
        Map<String, Integer> rep = new HashMap<>();
        userService.getUserRecordsById(userId, null, null, "repeatedRead", null, null)
                .stream().forEach(x -> {
                    int val = x.getRepeatedRead();
                    if (x.isPostlike())
                        val += 1;
                    if (x.isPostbookmark())
                        val += 2;
                    Integer oldval = rep.put(x.getNewsCategory().getName(), val);
                    if (oldval != null)
                        rep.put(x.getNewsCategory().getName(), oldval + val);
                });
        for (String category : rep.keySet()) {
            Integer count = (int) (rep.get(category) * 0.2) + 1;
            List<NewsDTO> newsByCategory = getAllNews(1, 100, null, category, null, null).stream().toList();
            newsDTOS.addAll(newsByCategory);
        }
        PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortBy);

        return new PageImpl<>(pagedListHolderFromRequest(pageRequest, newsDTOS).getPageList());
    }

    //Convert to Page


    //-----Like and Bookmark function-----
    public String likePost(UUID newsId, UUID userId, Boolean bool) {
        NewsDTO news = getNewsById(newsId);
        UserDTO user = userService.getUser(userId);
        recordsService.setlike(user.getUserId(), news.getNewsId(), bool);
        return "liked";
    }

    public String bookmark(UUID newsId, UUID userId, Boolean bool) {
        NewsDTO news = getNewsById(newsId);
        UserDTO user = userService.getUser(userId);
        recordsService.setbookmark(user.getUserId(), news.getNewsId(), bool);
        return "bookmarked";
    }

    public Map<String, Integer> getInteractions(UUID newsId) {
        Map<String, Integer> map = new HashMap<>();
        map.put("likes", recordsService.getLikes(newsId));
        map.put("bookmarks", recordsService.getBookmarks(newsId));
        map.put("views", recordsService.getViews(newsId));
        return map;
    }

    public Map<String, Integer> patchInteractions(CreateInteractionRequest createInteractionRequest) {
        if(createInteractionRequest.getLike()!=null)
            likePost(createInteractionRequest.getNewsId(), createInteractionRequest.getUserId(), createInteractionRequest.getLike());
        if(createInteractionRequest.getBookmark()!=null)
            bookmark(createInteractionRequest.getNewsId(), createInteractionRequest.getUserId(), createInteractionRequest.getBookmark());
        return getInteractions(createInteractionRequest.getNewsId());
    }

    public PageRequest buildPageRequest(Integer pageNumber, Integer pageSize, String sortBy) {
        // if not initilized set it to default

        int queryPageNumber;
        int queryPageSize;
        String querySortBy;

        if (pageNumber != null && pageNumber > 0)
            queryPageNumber = pageNumber - 1; //it is 0 indexed, for first page, number is 1.
        else
            queryPageNumber = DEFAULT_PAGE;

        if (pageSize == null)
            queryPageSize = DEFAULT_PAGE_SIZE;
        else
            queryPageSize = pageSize;
        //setting a max size
        if (queryPageSize > 100)
            queryPageSize = 100;

        if (sortBy != null && !sortBy.equals(""))
            querySortBy = sortBy;
        else
            querySortBy = DEFAULT_SORT;

        Sort sort = Sort.by(Sort.Order.desc(querySortBy));
        return PageRequest.of(queryPageNumber, queryPageSize, sort);

    }

    private PagedListHolder<NewsDTO> pagedListHolderFromRequest(PageRequest pageRequest, List<NewsDTO> list) {
        PagedListHolder<NewsDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }

    private PagedListHolder<CommentDTO> pagedListHolderFromRequestComments(PageRequest pageRequest, List<CommentDTO> list) {
        PagedListHolder<CommentDTO> pagedListHolder = new PagedListHolder<>(list);
        pagedListHolder.setPageSize(pageRequest.getPageSize());
        pagedListHolder.setPage(pageRequest.getPageNumber());
        pagedListHolder.setSort(new MutableSortDefinition(pagedListHolder.getSort().toString(), true, true));
        return pagedListHolder;
    }

}
