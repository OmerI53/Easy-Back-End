package com.example.Easy.repository.specifications;

import com.example.Easy.entities.NewsEntity;
import org.springframework.data.jpa.domain.Specification;

public class NewsSpecifications {

    public static Specification<NewsEntity> getSpecifiedNews(String category, String title, String author){
        return (root,query,builder)->Specification.where(withCategory(category))
                .and(withTitle(title))
                .and(withUser(author))
                .toPredicate(root,query,builder);
    }

    public static Specification<NewsEntity> withCategory(String category){
        return (root,query,builder)->{
            if(category==null)
                return null;
            return builder.equal(root.get("category").get("name"),category);
        };
    }
    public static Specification<NewsEntity> withTitle(String title){
        return (root,query,builder)->{
            if(title==null)
                return null;
            return builder.equal(root.get("title"),title);
        };
    }
    public static Specification<NewsEntity> withUser(String user){
        return (root,query,builder)->{
            if(user==null)
                return null;
            return builder.equal(root.get("author").get("name"),user);
        };
    }
}
