package com.musinsa.backend.service;

import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.BrandRepository;
import com.musinsa.backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class PriceService {

    private final ProductRepository productRepository;
    private final BrandRepository brandRepository;

    public PriceService(ProductRepository productRepository, BrandRepository brandRepository) {
        this.productRepository = productRepository;
        this.brandRepository = brandRepository;
    }

    // API 1: 카테고리별 최저가격 및 총액 조회
    public CategorySummaryResponse getCategorySummary() {
        List<String> categories = Arrays.asList(getCategories().toArray(new String[0]));
        List<CategorySummaryItem> items = new ArrayList<>();
        int totalSum = 0;
        
        for (String category : categories) {
            Product product = productRepository.findFirstByCategoryOrderByPriceAsc(category);
            if (product == null) {
                throw new RuntimeException("No product found for category: " + category);
            }
            items.add(new CategorySummaryItem(category, product.getBrand().getName(), product.getPrice()));
            totalSum += product.getPrice();
        }
        return new CategorySummaryResponse(items, totalSum);
    }

    // API 2: 단일 브랜드로 전체 카테고리 구매 시 최저 총액 브랜드 조회
    public LowestBrandPurchaseResponse getLowestBrandPurchase() {
        List<Brand> brands = brandRepository.findAll();
        LowestBrandPurchaseResponse result = null;
        int minTotal = Integer.MAX_VALUE;
        
        for (Brand brand : brands) {
            int sum = brand.getProducts().stream().mapToInt(Product::getPrice).sum();
            if (sum < minTotal) {
                minTotal = sum;
                List<CategoryPriceDetail> details = new ArrayList<>();
                List<String> categories = Arrays.asList(getCategories().toArray(new String[0]));
                Map<String, Integer> priceMap = new HashMap<>();
                for (Product p : brand.getProducts()) {
                    priceMap.put(p.getCategory(), p.getPrice());
                }
                for (String cat : categories) {
                    if (priceMap.containsKey(cat)) {
                        details.add(new CategoryPriceDetail(cat, priceMap.get(cat)));
                    }
                }
                result = new LowestBrandPurchaseResponse(brand.getName(), details, minTotal);
            }
        }
        
        if (result == null) {
            throw new RuntimeException("No brands found.");
        }
        return result;
    }

    // API 3: 특정 카테고리의 최저/최고 가격 조회
    public CategoryPriceRangeResponse getCategoryPriceRange(String category) {
        Product lowest = productRepository.findFirstByCategoryOrderByPriceAsc(category);
        Product highest = productRepository.findFirstByCategoryOrderByPriceDesc(category);
        if (lowest == null || highest == null) {
            throw new RuntimeException("No products found for category: " + category);
        }
        List<PriceDetail> lowestList = Collections.singletonList(new PriceDetail(lowest.getBrand().getName(), lowest.getPrice()));
        List<PriceDetail> highestList = Collections.singletonList(new PriceDetail(highest.getBrand().getName(), highest.getPrice()));
        return new CategoryPriceRangeResponse(category, lowestList, highestList);
    }

    // API 4: 존재하는 모든 카테고리 이름 조회
    public List<String> getCategories() {
        return productRepository.findAllCategories();
    }

    // DTO 클래스 (필요시 별도의 dto 패키지로 분리 가능)
    public static class CategorySummaryResponse {
        private List<CategorySummaryItem> items;
        private int total;

        public CategorySummaryResponse(List<CategorySummaryItem> items, int total) {
            this.items = items;
            this.total = total;
        }
        public List<CategorySummaryItem> getItems() { return items; }
        public void setItems(List<CategorySummaryItem> items) { this.items = items; }
        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }
    }

    public static class CategorySummaryItem {
        private String category;
        private String brand;
        private int price;

        public CategorySummaryItem(String category, String brand, int price) {
            this.category = category;
            this.brand = brand;
            this.price = price;
        }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
    }

    public static class LowestBrandPurchaseResponse {
        private String brand;
        private List<CategoryPriceDetail> category;
        private int total;

        public LowestBrandPurchaseResponse(String brand, List<CategoryPriceDetail> category, int total) {
            this.brand = brand;
            this.category = category;
            this.total = total;
        }
        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public List<CategoryPriceDetail> getCategory() { return category; }
        public void setCategory(List<CategoryPriceDetail> category) { this.category = category; }
        public int getTotal() { return total; }
        public void setTotal(int total) { this.total = total; }
    }

    public static class CategoryPriceDetail {
        private String category;
        private int price;

        public CategoryPriceDetail(String category, int price) {
            this.category = category;
            this.price = price;
        }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
    }

    public static class CategoryPriceRangeResponse {
        private String category;
        private List<PriceDetail> lowestPrice;
        private List<PriceDetail> highestPrice;

        public CategoryPriceRangeResponse(String category, List<PriceDetail> lowestPrice, List<PriceDetail> highestPrice) {
            this.category = category;
            this.lowestPrice = lowestPrice;
            this.highestPrice = highestPrice;
        }
        public String getCategory() { return category; }
        public void setCategory(String category) { this.category = category; }
        public List<PriceDetail> getLowestPrice() { return lowestPrice; }
        public void setLowestPrice(List<PriceDetail> lowestPrice) { this.lowestPrice = lowestPrice; }
        public List<PriceDetail> getHighestPrice() { return highestPrice; }
        public void setHighestPrice(List<PriceDetail> highestPrice) { this.highestPrice = highestPrice; }
    }

    public static class PriceDetail {
        private String brand;
        private int price;

        public PriceDetail(String brand, int price) {
            this.brand = brand;
            this.price = price;
        }
        public String getBrand() { return brand; }
        public void setBrand(String brand) { this.brand = brand; }
        public int getPrice() { return price; }
        public void setPrice(int price) { this.price = price; }
    }
}