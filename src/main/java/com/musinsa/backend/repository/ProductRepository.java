package com.musinsa.backend.repository;

import com.musinsa.backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    
    // 카테고리별 최저가 상품 조회 (가격 오름차순 정렬 후 첫 번째)
    Product findFirstByCategoryOrderByPriceAsc(String category);
    
    // 카테고리별 최고가 상품 조회 (가격 내림차순 정렬 후 첫 번째)
    Product findFirstByCategoryOrderByPriceDesc(String category);

    // 존재하는 모든 카테고리 이름 조회
    @Query("SELECT DISTINCT p.category FROM Product p")
    List<String> findAllCategories();
} 