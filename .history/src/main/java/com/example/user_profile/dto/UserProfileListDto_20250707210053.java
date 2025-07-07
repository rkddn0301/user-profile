package com.example.user_profile.dto;

import java.sql.Timestamp;

public class UserProfileListDto {
    private String userName;
    private Integer viewCount;
    private Timestamp createdAt;

    public UserProfileListDto(String userName, Integer viewCount, Timestamp createdAt) {
        this.userName = userName;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

    public String getUserName() { return userName; }
    public Integer getViewCount() { return viewCount; }
    public Timestamp getCreatedAt() { return createdAt; }
} 