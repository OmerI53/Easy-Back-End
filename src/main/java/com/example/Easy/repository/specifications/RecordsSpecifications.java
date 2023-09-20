package com.example.Easy.repository.specifications;

import com.example.Easy.entities.RecordsEntity;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class RecordsSpecifications {

    public static Specification<RecordsEntity> getSpecifiedRecords(UUID userId, Boolean like, Boolean bookmark) {
        return (root, query, criteriaBuilder) -> org.springframework.data.jpa.domain.Specification.where(withLikedPost(like))
                .and(withBookmarkedPost(bookmark))
                .and(withUser(userId))
                .toPredicate(root, query, criteriaBuilder);
    }
    public static Specification<RecordsEntity> withLikedPost(Boolean like) {
        return (root, query, criteriaBuilder) -> {
            if (like == null)
                return null;
            return criteriaBuilder.equal(root.get("postlike"), like);
        };
    }
    public static Specification<RecordsEntity> withBookmarkedPost(Boolean bookmark) {
        return (root, query, criteriaBuilder) -> {
            if (bookmark == null)
                return null;
            return criteriaBuilder.equal(root.get("postbookmark"), bookmark);
        };

    }
    public static Specification<RecordsEntity> withUser(UUID user) {
        return (root, query, criteriaBuilder) ->{
            if (user == null)
                return null;
            return criteriaBuilder.equal(root.get("user").get("userId"), user);
        };
    }
}
