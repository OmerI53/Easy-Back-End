package com.example.Easy.models.response;

import com.example.Easy.models.CategoryDTO;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CategoryResponse extends BaseResponse{
    String name;
    String parent;
    public CategoryResponse(CategoryDTO categoryDTO) {
        this.name = categoryDTO.getName();
        if(categoryDTO.getParent()!=null)
            parent=categoryDTO.getParent().getName();
    }
}
