package com.example.user_profile.service;

import com.example.user_profile.entity.PointCharge;
import com.example.user_profile.entity.QPointCharge;
import com.example.user_profile.entity.UserProfile;
import com.example.user_profile.entity.QUserProfile;
import com.example.user_profile.repository.PointChargeRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.HashMap;

// 포인트 충전 서비스
@Service
public class PointChargeService {

    // 포인트 충전 repository
    private final PointChargeRepository pointChargeRepository;
    private final JPAQueryFactory queryFactory;

    @Autowired
    public PointChargeService(PointChargeRepository pointChargeRepository,
            EntityManager em) {
        this.pointChargeRepository = pointChargeRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 포인트 적립
    @Transactional
    public boolean chargePoint(Integer userNo, Integer amount) {

        // QueryDSL을 사용하여 UserProfile과 PointCharge를 한 번에 조회
        QUserProfile qUserProfile = QUserProfile.userProfile;
        QPointCharge qPointCharge = QPointCharge.pointCharge;

        // UserProfile 조회
        UserProfile user = queryFactory
                .selectFrom(qUserProfile)
                .where(qUserProfile.userNo.eq(userNo))
                .fetchOne();

        if (user == null) {
            return false;
        }

        // PointCharge 조회
        PointCharge pointCharge = queryFactory
                .selectFrom(qPointCharge)
                .where(qPointCharge.userNo.eq(userNo))
                .fetchOne();

        if (pointCharge != null) {
            // update
            return updatePointCharge(pointCharge, amount);
        } else {
            // insert
            return insertPointCharge(user, userNo, amount);
        }
    }

    private boolean updatePointCharge(PointCharge pointCharge, Integer amount) {
        pointCharge.setAmount(pointCharge.getAmount() + amount);
        pointChargeRepository.save(pointCharge);
        return true;
    }

    private boolean insertPointCharge(UserProfile user, Integer userNo, Integer amount) {
        PointCharge pointCharge = new PointCharge();
        pointCharge.setUserProfile(user);
        pointCharge.setAmount(amount);
        pointChargeRepository.save(pointCharge);
        return true;
    }

    // 결제 승인(토스페이먼츠)
    @Transactional
    public ResponseEntity<?> confirmTossPayment(Map<String, Object> request) {
        try {
            String paymentKey = (String) request.get("paymentKey");
            String orderId = (String) request.get("orderId");
            Integer amount = (Integer) request.get("amount");
            Integer userNo = (Integer) request.get("userNo");

            // 필수값 체크
            if (paymentKey == null || orderId == null || amount == null || userNo == null) {
                // 400 오류 발생 시
                return ResponseEntity.badRequest().body(Map.of(
                        "code", "INVALID_REQUEST",
                        "message", "잘못된 요청입니다."));
            }

            // 포인트 적립
            boolean result = chargePoint(userNo, amount);
            if (result) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "결제 승인 성공 및 포인트 충전 완료");
                response.put("chargedAmount", amount);
                response.put("userNo", userNo);
                response.put("paymentKey", paymentKey);
                response.put("orderId", orderId);
                return ResponseEntity.ok(response);
            } else {
                // 400 오류 발생 시
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                        "code", "INVALID_REQUEST",
                        "message", "잘못된 요청입니다."));
            }
        } catch (Exception e) {
            // 500 오류 발생 시
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "code", "INTERNAL_SERVER_ERROR",
                    "message", "내부 시스템 처리 작업이 실패했습니다. 잠시 후 다시 시도해주세요."));
        }
    }

}
