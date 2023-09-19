package com.example.Easy.models.response;

import com.example.Easy.models.RecordsDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class RecordsResponse extends BaseResponse{
    private UUID userId;
    private UUID newsId;
    private Integer repeatedReads;
    private Boolean like;
    private Boolean bookmark;

    public RecordsResponse(RecordsDTO recordsDTO) {
        this.userId = recordsDTO.getUser().getUserId();
        this.newsId = recordsDTO.getNews().getNewsId();
        this.repeatedReads = recordsDTO.getRepeatedRead();
        this.like = recordsDTO.isPostlike();
        this.bookmark = recordsDTO.isPostbookmark();
    }
}
