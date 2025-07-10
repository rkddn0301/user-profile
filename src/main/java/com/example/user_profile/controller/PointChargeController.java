package com.example.user_profile.controller;

import com.example.user_profile.service.PointChargeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

// 포인트 충전 컨트롤러
@RestController
@RequestMapping("/api/points")
public class PointChargeController {
    private final PointChargeService pointChargeService;

    @Autowired
    public PointChargeController(PointChargeService pointChargeService) {
        this.pointChargeService = pointChargeService;
    }

    // 토스페이먼츠 결제 승인(Confirm) API
    @PostMapping("/toss/confirm")
    public ResponseEntity<?> confirmTossPayment(@RequestBody Map<String, Object> request) {

        // 필수값 체크
        if (!request.containsKey("paymentKey") || !request.containsKey("orderId") || !request.containsKey("amount")
                || !request.containsKey("userNo")) {

            // 400 오류 발생 시
            return ResponseEntity.badRequest().body(Map.of(
                    "code", "INVALID_REQUEST",
                    "message", "잘못된 요청입니다."));
        }
        try {

            return pointChargeService.confirmTossPayment(request);
        } catch (Exception e) {

            // 서버 500 오류 발생 시
            return ResponseEntity.internalServerError().body(Map.of(
                    "code", "UNKNOWN_PAYMENT_ERROR",
                    "message", "결제에 실패했어요. 같은 문제가 반복된다면 은행이나 카드사로 문의해주세요."));
        }
    }

}
