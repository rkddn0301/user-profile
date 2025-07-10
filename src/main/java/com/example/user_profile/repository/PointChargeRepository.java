package com.example.user_profile.repository;

import com.example.user_profile.entity.PointCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointChargeRepository extends JpaRepository<PointCharge, Integer> {
} 