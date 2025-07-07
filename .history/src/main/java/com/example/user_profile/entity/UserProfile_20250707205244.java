package com.example.user_profile.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

// 회원 프로필 엔티티
@Entity
@Data
@Table(name = "USER_PROFILE")
public class UserProfile {

    // 회원번호
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Integer userNo;

    // 회원이름
    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    // 조회수
    @Column(name = "VIEW_COUNT", nullable = false)
    private Integer viewCount = 0;

    // 생성일시
    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Timestamp createdAt;

    // 데이터 생성 시 자동으로 생성 시간 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

} 