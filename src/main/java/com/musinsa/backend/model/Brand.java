package com.musinsa.backend.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Brand {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // 브랜드 이름 (예: A, B, C 등)
    @Column(nullable = false, unique = true)
    private String name;
    
    // 하나의 브랜드가 여러 상품(Product)을 가짐 (1:N 관계)
    @OneToMany(mappedBy = "brand", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();
    
    // 기본 생성자
    public Brand() {}
    
    // 생성자
    public Brand(String name) {
        this.name = name;
    }
    
    // Getter, Setter
    public Long getId() {
        return id;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<Product> getProducts() {
        return products;
    }
    
    public void addProduct(Product product) {
        products.add(product);
        product.setBrand(this);
    }
    
    public void removeProduct(Product product) {
        products.remove(product);
        product.setBrand(null);
    }
} 