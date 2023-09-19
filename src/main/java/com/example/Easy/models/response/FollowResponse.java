package com.example.Easy.models.response;

import com.example.Easy.requests.FollowRequest;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class FollowResponse extends BaseResponse{
    private UUID user;
    private UUID followingUser;

    public FollowResponse(FollowRequest request) {
        this.user = request.getUserId();
        this.followingUser = request.getFollowingUserId();
    }
}
