package com.example.user_profile.controller;

import com.example.user_profile.entity.UserProfile;
import com.example.user_profile.service.UserProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profiles")
public class UserProfileController {
    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    // 프로필 목록 조회 (페이징, 정렬)
    @GetMapping
    public Page<UserProfile> getProfiles(
            @PageableDefault(size = 10, sort = "userName") Pageable pageable) {
        return userProfileService.getProfiles(pageable);
    }

    // 프로필 상세 조회수 증가
    @PostMapping("/{userNo}/view")
    public String incrementViewCount(@PathVariable Integer userNo) {
        boolean result = userProfileService.incrementViewCount(userNo);
        return result ? "success" : "not found";
    }

    // 모든 프로필 전체 조회 (테스트용)
    @GetMapping("/all")
    public Iterable<UserProfile> getAllProfiles() {
        return userProfileService.getAllProfiles();
    }
} 