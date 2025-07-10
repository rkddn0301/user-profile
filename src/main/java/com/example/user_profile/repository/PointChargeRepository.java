package com.example.user_profile.repository;

import com.example.user_profile.entity.PointCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// 포인트 충전 repository
@Repository
public interface PointChargeRepository extends JpaRepository<PointCharge, Integer> {
}