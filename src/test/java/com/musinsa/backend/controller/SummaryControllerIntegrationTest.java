package com.musinsa.backend.controller;

import com.musinsa.backend.MusinsaAssignmentApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = MusinsaAssignmentApplication.class)
@AutoConfigureMockMvc
public class SummaryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    // 테스트 1: 카테고리별 최저가 및 총액 조회
    @Test
    void testGetCategorySummary() throws Exception {
        mockMvc.perform(get("/api/v1/summary/category"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(8)))
                .andExpect(jsonPath("$.total", greaterThan(0)));
    }

    // 테스트 2: 단일 브랜드 구매 최저 총액 조회
    @Test
    void testGetLowestBrandPurchase() throws Exception {
        mockMvc.perform(get("/api/v1/summary/brand"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.brand", not(emptyString())))
                .andExpect(jsonPath("$.total", greaterThan(0)))
                .andExpect(jsonPath("$.category", not(empty())));
    }

    // 테스트 3: 특정 카테고리의 최저/최고 가격 조회 (예: 상의)
    @Test
    void testGetCategoryPriceRange() throws Exception {
        mockMvc.perform(get("/api/v1/summary/category/상의"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.category", is("상의")))
                .andExpect(jsonPath("$.lowestPrice[0].brand", not(emptyString())))
                .andExpect(jsonPath("$.highestPrice[0].brand", not(emptyString())));
    }
}