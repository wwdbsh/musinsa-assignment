package com.musinsa.backend.controller;

import com.musinsa.backend.service.PriceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/summary")
public class SummaryController {

    private final PriceService priceService;

    public SummaryController(PriceService priceService) {
        this.priceService = priceService;
    }

    // API 1: 카테고리별 최저가 및 총액 조회
    @GetMapping("/category")
    public ResponseEntity<?> getCategorySummary() {
        try {
            return ResponseEntity.ok(priceService.getCategorySummary());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("FAILURE", e.getMessage()));
        }
    }

    // API 2: 단일 브랜드로 모든 카테고리 상품 구매 시 최저 총액 브랜드 조회
    @GetMapping("/brand")
    public ResponseEntity<?> getLowestBrandPurchase() {
        try {
            return ResponseEntity.ok(priceService.getLowestBrandPurchase());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("FAILURE", e.getMessage()));
        }
    }

    // API 3: 특정 카테고리의 최저/최고 가격 조회
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getCategoryPriceRange(@PathVariable String category) {
        try {
            return ResponseEntity.ok(priceService.getCategoryPriceRange(category));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new ErrorResponse("FAILURE", e.getMessage()));
        }
    }

    // 간단한 에러 응답 DTO
    public static class ErrorResponse {
        private String status;
        private String message;
        public ErrorResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}