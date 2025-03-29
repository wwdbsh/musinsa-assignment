package com.musinsa.backend.repository;

import com.musinsa.backend.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    // 브랜드 이름으로 조회하는 메소드
    Brand findByName(String name);
}