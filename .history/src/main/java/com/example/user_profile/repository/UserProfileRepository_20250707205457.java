package com.example.user_profile.repository;

import com.example.user_profile.entity.UserProfile;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 회원 프로필 repository
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {
   
} 