package com.example.Easy.requests;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FollowRequest {
    private UUID userId;
    private UUID followingUserId;
}
