package com.example.Easy.models.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
public class BaseResponse {
    protected LocalDateTime responseTime;
    public BaseResponse() {
        this.responseTime = LocalDateTime.now();
    }
}
