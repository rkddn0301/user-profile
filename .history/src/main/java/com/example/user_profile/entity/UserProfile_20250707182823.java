package com.example.user_profile.entity;

import javax.persistence.*;
import lombok.Data;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "USER_PROFILE")
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_NO")
    private Integer userNo;

    @Column(name = "USER_NAME", nullable = false)
    private String userName;

    @Column(name = "VIEW_COUNT", nullable = false)
    private Integer viewCount = 0;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private Timestamp createdAt;

    // 데이터 생성 시 자동으로 생성 시간 설정
    @PrePersist
    protected void onCreate() {
        this.createdAt = new Timestamp(System.currentTimeMillis());
    }

} 