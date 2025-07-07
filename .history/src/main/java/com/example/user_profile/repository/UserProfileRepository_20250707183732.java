package com.example.user_profile.repository;

import com.example.user_profile.entity.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
    // 추가적인 쿼리 메서드는 필요시 여기에 작성
} 