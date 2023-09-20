package com.example.Easy.repository;

import com.example.Easy.entities.RecordsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RecordsRepository extends JpaRepository<RecordsEntity, UUID>, JpaSpecificationExecutor<RecordsEntity> {

    @Query(value="select * from records a where a.user_user_id=:userId",
            nativeQuery=true)
    List<RecordsEntity> findByUser(@Param("userId") String userId);
    @Query(value="select * from records a where a.news_news_id= :newsId",
            nativeQuery=true)
    List<RecordsEntity> findByNews(@Param("newsId") String newsId);
    @Query(value="select * from records a where a.user_user_id= :userId and a.news_news_id= :newsId",
            nativeQuery=true)
    RecordsEntity findByUserAndNews(@Param("userId") String user,@Param("newsId") String news);
    @Query(value="select * from records a where a.news_news_id= :newsId and a.postlike= :postlike",
            nativeQuery=true)
    List<RecordsEntity> findByNewsAndPostlike(@Param("newsId") String newsId, @Param("postlike") boolean postlike);

    @Query(value="select * from records a where a.news_news_id= :newsId and a.postbookmark= :postbookmark",
            nativeQuery=true)
    List<RecordsEntity> findByNewsAndPostbookmark(@Param("newsId") String newsId, @Param("postbookmark") boolean postbookmark);

    @Query(value="select * from records a where a.user_user_id= :userId and a.postlike= :postlike",
            nativeQuery=true)
    List<RecordsEntity> findByUserAndPostlike(@Param("userId") String userId,@Param("postlike") boolean postlike);
    @Query(value="select * from records a where a.user_user_id= :userId and a.postlike= :postbookmark",
            nativeQuery=true)
    List<RecordsEntity> findByUserAndPostbookmark(@Param("userId") String user,@Param("postbookmark") boolean postbookmark);
}
