package com.example.user_profile.service;

import com.example.user_profile.entity.PointCharge;
import com.example.user_profile.entity.QPointCharge;
import com.example.user_profile.entity.UserProfile;
import com.example.user_profile.entity.QUserProfile;
import com.example.user_profile.repository.PointChargeRepository;
import com.example.user_profile.repository.UserProfileRepository;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.persistence.EntityManager;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;
import java.util.HashMap;


@Service
public class PointChargeService {
    private final PointChargeRepository pointChargeRepository;
    private final UserProfileRepository userProfileRepository;
    private final JPAQueryFactory queryFactory;
    
    @Autowired
    public PointChargeService(PointChargeRepository pointChargeRepository, UserProfileRepository userProfileRepository, EntityManager em) {
        this.pointChargeRepository = pointChargeRepository;
        this.userProfileRepository = userProfileRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 포인트 적립 (insert/update 분리)
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
        pointCharge.setUserProfile(user); // 반드시 세팅!
        pointCharge.setAmount(amount);
        pointChargeRepository.save(pointCharge);
        return true;
    }

    // 결제 승인(토스페이먼츠)
    @Transactional
    public ResponseEntity<?> confirmTossPaymentMock(Map<String, Object> request) {
        try {
            String paymentKey = (String) request.get("paymentKey");
            String orderId = (String) request.get("orderId");
            Integer amount = (Integer) request.get("amount");
            Integer userNo = (Integer) request.get("userNo");

            // 필수값 체크
            if (paymentKey == null || orderId == null || amount == null || userNo == null) {
                return ResponseEntity.badRequest().body(Map.of(
                    "error", "결제 승인 실패",
                    "message", "필수 파라미터(paymentKey, orderId, amount, userNo)가 누락되었습니다."
                ));
            }

            // 실제 토스 요청 없이 바로 포인트 적립
            boolean result = chargePoint(userNo, amount);
            if (result) {
                Map<String, Object> response = new HashMap<>();
                response.put("status", "success");
                response.put("message", "Mock 결제 승인 성공 및 포인트 충전 완료");
                response.put("chargedAmount", amount);
                response.put("userNo", userNo);
                response.put("paymentKey", paymentKey);
                response.put("orderId", orderId);
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "error", "결제 승인 실패",
                    "message", "사용자를 찾을 수 없습니다."
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                "error", "결제 승인 실패",
                "message", e.getMessage()
            ));
        }
    } 

}
