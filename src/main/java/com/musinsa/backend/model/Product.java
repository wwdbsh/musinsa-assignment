package com.musinsa.backend.model;

import jakarta.persistence.*;

@Entity
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 카테고리 (상의, 아우터, 바지 등)
    @Column(nullable = false)
    private String category;
    
    // 상품 가격 (예: 10,000)
    @Column(nullable = false)
    private Integer price;
    
    // 각 상품은 하나의 브랜드에 속함 (N:1 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;
    
    // 기본 생성자
    public Product() {}
    
    // 생성자
    public Product(String category, Integer price) {
        this.category = category;
        this.price = price;
    }
    
    // Getter, Setter
    public Long getId() {
        return id;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public Integer getPrice() {
        return price;
    }
    
    public void setPrice(Integer price) {
        this.price = price;
    }
    
    public Brand getBrand() {
        return brand;
    }
    
    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}