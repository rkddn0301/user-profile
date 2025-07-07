package com.example.user_profile.service;

import com.example.user_profile.entity.UserProfile;
import com.example.user_profile.repository.UserProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.Optional;

// 회원 프로필 서비스
@Service
public class UserProfileService {
    private final UserProfileRepository userProfileRepository;

    @Autowired
    public UserProfileService(UserProfileRepository userProfileRepository) {
        this.userProfileRepository = userProfileRepository;
    }

    // 회원 프로필 목록 조회 (페이징, 정렬)
    public Page<UserProfile> getProfiles(Pageable pageable) {
        return userProfileRepository.findAll(pageable);
    }

    // 회원 프로필 상세 조회수 업데이트 
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

} 