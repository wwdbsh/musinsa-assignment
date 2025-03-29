package com.musinsa.backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.backend.MusinsaAssignmentApplication;
import com.musinsa.backend.controller.AdminController.BrandRequest;
import com.musinsa.backend.controller.AdminController.ProductRequest;
import com.musinsa.backend.model.Brand;
import com.musinsa.backend.repository.BrandRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MusinsaAssignmentApplication.class)
@AutoConfigureMockMvc
public class AdminControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    private BrandRepository brandRepository;

    // 테스트 1: 새로운 브랜드 및 상품 등록
    @Test
    void testCreateBrand() throws Exception {
        BrandRequest request = new BrandRequest();
        request.setName("J");
        ProductRequest p1 = new ProductRequest();
        p1.setCategory("상의");
        p1.setPrice(12000);
        ProductRequest p2 = new ProductRequest();
        p2.setCategory("아우터");
        p2.setPrice(6000);
        request.setProducts(Arrays.asList(p1, p2));

        mockMvc.perform(post("/api/v1/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")));
    }

    // 테스트 2: 브랜드 업데이트 (먼저 브랜드를 생성한 후, 해당 브랜드를 업데이트)
    @Test
    void testUpdateBrand() throws Exception {
        // 1. 우선 새로운 브랜드 생성
        BrandRequest createRequest = new BrandRequest();
        createRequest.setName("TempBrand");
        ProductRequest createProduct = new ProductRequest();
        createProduct.setCategory("상의");
        createProduct.setPrice(10000);
        createRequest.setProducts(Arrays.asList(createProduct));

        mockMvc.perform(post("/api/v1/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")));
        
        // 2. 생성된 브랜드의 ID 조회 (예: 이름으로 조회)
        Brand createdBrand = brandRepository.findByName("TempBrand");
        Long brandId = createdBrand.getId();

        // 3. 업데이트 요청: 브랜드명 변경 및 상품 변경
        BrandRequest updateRequest = new BrandRequest();
        updateRequest.setName("K");
        ProductRequest updateProduct = new ProductRequest();
        updateProduct.setCategory("모자");
        updateProduct.setPrice(8000);
        updateRequest.setProducts(Arrays.asList(updateProduct));

        mockMvc.perform(put("/api/v1/admin/brands/" + brandId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updateRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")));
    }

    // 테스트 3: 브랜드 삭제 (예시로 생성한 브랜드 삭제)
    @Test
    void testDeleteBrand() throws Exception {
        // 먼저 새로운 브랜드 생성하여 삭제 대상 확보
        BrandRequest createRequest = new BrandRequest();
        createRequest.setName("DeleteBrand");
        ProductRequest createProduct = new ProductRequest();
        createProduct.setCategory("액세서리");
        createProduct.setPrice(1500);
        createRequest.setProducts(Arrays.asList(createProduct));

        mockMvc.perform(post("/api/v1/admin/brands")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createRequest)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")));
        
        // 생성된 브랜드의 ID 조회
        Brand createdBrand = brandRepository.findByName("DeleteBrand");
        Long brandId = createdBrand.getId();

        // 삭제 요청
        mockMvc.perform(delete("/api/v1/admin/brands/" + brandId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is("SUCCESS")));
    }
}