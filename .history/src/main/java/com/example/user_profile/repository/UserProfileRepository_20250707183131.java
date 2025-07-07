package com.example.user_profile.repository;

import com.example.user_profile.entity.UserProfile;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends PagingAndSortingRepository<UserProfile, Integer> {
    // 추가적인 쿼리 메서드는 필요시 여기에 작성
} 