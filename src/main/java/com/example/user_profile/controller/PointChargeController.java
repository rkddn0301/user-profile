package com.example.user_profile.controller;

import com.example.user_profile.service.PointChargeService;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

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
        if (!request.containsKey("paymentKey") || !request.containsKey("orderId") || !request.containsKey("amount") || !request.containsKey("userNo")) {
  
            return ResponseEntity.badRequest().body(Map.of(
                "error", "결제 승인 실패",
                "message", "필수 파라미터(paymentKey, orderId, amount, userNo)가 누락되었습니다."
            ));
        }
        try {
           
            return pointChargeService.confirmTossPaymentMock(request);
        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(Map.of(
                "error", "결제 승인 실패",
                "message", e.getMessage()
            ));
        }
    } 


}
