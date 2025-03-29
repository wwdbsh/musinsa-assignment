package com.musinsa.backend.controller;

import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.BrandRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final BrandRepository brandRepository;

    public AdminController(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    // 브랜드 및 상품 추가 (생성)
    @PostMapping("/brands")
    public ResponseEntity<?> createBrand(@RequestBody BrandRequest request) {
        try {
            if (brandRepository.findByName(request.getName()) != null) {
                return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", "Brand already exists"));
            }
            Brand brand = new Brand(request.getName());
            for (ProductRequest pr : request.getProducts()) {
                if (!pr.getCategory().equals("")) {
                    brand.addProduct(new Product(pr.getCategory(), pr.getPrice()));
                }
            }
            brandRepository.save(brand);
            return ResponseEntity.ok(new AdminResponse("SUCCESS", "Brand created successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", e.getMessage()));
        }
    }

    // 브랜드 및 상품 업데이트
    @PutMapping("/brands/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody BrandRequest request) {
        try {
            Brand brand = brandRepository.findById(id).orElse(null);
            if (brand == null) {
                return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", "Brand not found"));
            }
            brand.setName(request.getName());
            for (ProductRequest pr : request.getProducts()) {
                if (brand.getProducts().stream().anyMatch(p -> p.getCategory().equals(pr.getCategory()))) {
                    brand.getProducts().stream().filter(p -> p.getCategory().equals(pr.getCategory())).findFirst().ifPresent(p -> p.setPrice(pr.getPrice()));
                } else {
                    if (!pr.getCategory().equals("")) {
                        brand.addProduct(new Product(pr.getCategory(), pr.getPrice()));
                    }
                }
            }
            brandRepository.save(brand);
            return ResponseEntity.ok(new AdminResponse("SUCCESS", "Brand updated successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", e.getMessage()));
        }
    }

    // 브랜드 및 상품 삭제
    @DeleteMapping("/brands/{id}")
    public ResponseEntity<?> deleteBrand(@PathVariable Long id) {
        try {
            Brand brand = brandRepository.findById(id).orElse(null);
            if (brand == null) {
                return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", "Brand not found"));
            }
            brandRepository.delete(brand);
            return ResponseEntity.ok(new AdminResponse("SUCCESS", "Brand deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", e.getMessage()));
        }
    }

    // 브랜드 이름으로 id 조회
    @GetMapping("/brands/name/{name}")
    public ResponseEntity<?> getBrandIdByName(@PathVariable String name) {
        try {
            Brand brand = brandRepository.findByName(name);
            if (brand == null) {
                return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", "Brand not found"));
            }
            return ResponseEntity.ok(new AdminResponse("SUCCESS", "Brand found successfully", brand.getId()));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(new AdminResponse("FAILURE", e.getMessage()));
        }
    }   

    // 요청/응답 DTO 클래스
    public static class BrandRequest {
        private String name;
        private List<ProductRequest> products;
        public BrandRequest() {}
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public List<ProductRequest> getProducts() { return products; }
        public void setProducts(List<ProductRequest> products) { this.products = products; }
    }
    
    public static class ProductRequest {
        private String category;
        private int price;
        public ProductRequest() {}
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
    }
    
    public static class AdminResponse {
        private String status;
        private String message;
        private Long id;
        public AdminResponse(String status, String message) {
            this.status = status;
            this.message = message;
        }
        public AdminResponse(String status, String message, Long id) {
            this.status = status;
            this.message = message;
            this.id = id;
        }
        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }
}