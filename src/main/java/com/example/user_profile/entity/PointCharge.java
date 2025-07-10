package com.example.user_profile.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "POINT_CHARGE")
public class PointCharge {
    @Id
    @Column(name = "USER_NO")
    private Integer userNo;

    @OneToOne
    @MapsId
    @JoinColumn(name = "USER_NO", referencedColumnName = "USER_NO", unique = true)
    private UserProfile userProfile;

    @Column(name = "AMOUNT", nullable = false)
    private Integer amount;
}
