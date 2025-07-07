package com.example.user_profile.repository;

import com.example.user_profile.entity.UserProfile;
import com.example.user_profile.dto.UserProfileListDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

// 회원 프로필 repository
@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile, Integer> {

    // 회원 프로필 목록 조회 DTO 반환 메서드 구현
    @Query("SELECT new com.example.user_profile.dto.UserProfileDto(u.userName, u.viewCount, u.createdAt) FROM UserProfile u")
    Page<UserProfileListDto> findAllDto(Pageable pageable);
} 