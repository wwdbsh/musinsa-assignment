package com.musinsa.backend.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.BrandRepository;
import com.musinsa.backend.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Arrays;

public class PriceServiceTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private PriceService priceService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    // 테스트 1: 카테고리별 최저가와 총액 검증
    @Test
    void testGetCategorySummary() {
        // 각 카테고리에서 동일한 가격과 브랜드를 반환하도록 모의 설정
        String[] categories = {"상의", "아우터", "바지", "스니커즈", "가방", "모자", "양말", "액세서리"};
        for(String category: categories) {
            Product product = new Product(category, 10000);
            Brand brand = new Brand("C");
            product.setBrand(brand);
            when(productRepository.findFirstByCategoryOrderByPriceAsc(category)).thenReturn(product);
        }
        
        PriceService.CategorySummaryResponse response = priceService.getCategorySummary();
        assertEquals(8, response.getItems().size(), "카테고리 수가 8개여야 합니다.");
        assertEquals(80000, response.getTotal(), "총액은 8 * 10000 이어야 합니다.");
    }

    // 테스트 2: 단일 브랜드 구매 시 최저 총액 검증
    @Test
    void testGetLowestBrandPurchase() {
        // 두 브랜드를 모의 데이터로 생성
        Brand brandD = new Brand("D");
        brandD.addProduct(new Product("상의", 10100));
        brandD.addProduct(new Product("아우터", 5100));
        brandD.addProduct(new Product("바지", 3000));
        brandD.addProduct(new Product("스니커즈", 9500));
        brandD.addProduct(new Product("가방", 2500));
        brandD.addProduct(new Product("모자", 1500));
        brandD.addProduct(new Product("양말", 2400));
        brandD.addProduct(new Product("액세서리", 2000));
        // 총합 = 36100

        Brand brandC = new Brand("C");
        brandC.addProduct(new Product("상의", 10000));
        brandC.addProduct(new Product("아우터", 6200));
        brandC.addProduct(new Product("바지", 3300));
        brandC.addProduct(new Product("스니커즈", 9200));
        brandC.addProduct(new Product("가방", 2200));
        brandC.addProduct(new Product("모자", 1900));
        brandC.addProduct(new Product("양말", 2200));
        brandC.addProduct(new Product("액세서리", 2100));
        // 총합 = 37900

        when(brandRepository.findAll()).thenReturn(Arrays.asList(brandD, brandC));

        PriceService.LowestBrandPurchaseResponse response = priceService.getLowestBrandPurchase();
        assertEquals("D", response.getBrand(), "최저 총액 브랜드는 D여야 합니다.");
        assertEquals(36100, response.getTotal(), "총액은 36100이어야 합니다.");
    }

    // 테스트 3: 특정 카테고리의 최저, 최고 가격 검증
    @Test
    void testGetCategoryPriceRange() {
        // 예시: "상의" 카테고리
        Product lowest = new Product("상의", 10000);
        lowest.setBrand(new Brand("C"));
        Product highest = new Product("상의", 11400);
        highest.setBrand(new Brand("I"));

        when(productRepository.findFirstByCategoryOrderByPriceAsc("상의")).thenReturn(lowest);
        when(productRepository.findFirstByCategoryOrderByPriceDesc("상의")).thenReturn(highest);

        PriceService.CategoryPriceRangeResponse response = priceService.getCategoryPriceRange("상의");
        assertEquals("상의", response.getCategory());
        assertEquals("C", response.getLowestPrice().get(0).getBrand());
        assertEquals(10000, response.getLowestPrice().get(0).getPrice());
        assertEquals("I", response.getHighestPrice().get(0).getBrand());
        assertEquals(11400, response.getHighestPrice().get(0).getPrice());
    }
}