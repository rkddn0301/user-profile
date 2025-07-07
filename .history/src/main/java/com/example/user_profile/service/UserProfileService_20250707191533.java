package com.example.user_profile.service;

import com.example.user_profile.entity.UserProfile;
import com.example.user_profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // 프로필 목록 조회 (페이징, 정렬)
    public Page<UserProfile> getProfiles(Pageable pageable) {
        return userProfileRepository.findAll(pageable);
    }

    // 프로필 상세 조회수 증가
    public boolean incrementViewCount(Integer userNo) {
        Optional<UserProfile> optional = userProfileRepository.findById(userNo);
        if (optional.isPresent()) {
            UserProfile profile = optional.get();
            profile.setViewCount(profile.getViewCount() + 1);
            userProfileRepository.save(profile);
            return true;
        }
        return false;
    }

    // 모든 프로필 전체 조회 (테스트용)
    public Iterable<UserProfile> getAllProfiles() {
        return userProfileRepository.findAll();
    }
} 