package com.example.user_profile.entity;

import jakarta.persistence.*;
import lombok.Data;

// 포인트 충전 엔티티
@Entity
@Data
@Table(name = "POINT_CHARGE")
public class PointCharge {

    // 회원번호
    @Id
    @Column(name = "USER_NO")
    private Integer userNo;

    // 회원 프로필 엔티티와 1:1 관계 설정
    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO", unique = true)
    private UserProfile userProfile;

    // 충전 금액
    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;
}
