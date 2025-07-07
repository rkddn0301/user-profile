package com.example.user_profile.dto;

import java.sql.Timestamp;

import lombok.Getter;

// 회원 프로필 목록 DTO
@Getter
public class UserProfileListDto {
    private String userName;
    private Integer viewCount;
    private Timestamp createdAt;

    public UserProfileListDto(String userName, Integer viewCount, Timestamp createdAt) {
        this.userName = userName;
        this.viewCount = viewCount;
        this.createdAt = createdAt;
    }

} 