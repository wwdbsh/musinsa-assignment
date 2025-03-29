package com.musinsa.backend.bootstrap;

import com.musinsa.backend.model.Brand;
import com.musinsa.backend.model.Product;
import com.musinsa.backend.repository.BrandRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final BrandRepository brandRepository;

    public DataLoader(BrandRepository brandRepository) {
        this.brandRepository = brandRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // 데이터가 이미 로딩된 경우 재실행 방지
        if (brandRepository.count() > 0) {
            return;
        }

        // 브랜드 A 등록 (카테고리 순서: 상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액세서리)
        Brand brandA = new Brand("A");
        brandA.addProduct(new Product("상의", 11200));
        brandA.addProduct(new Product("아우터", 5500));
        brandA.addProduct(new Product("바지", 4200));
        brandA.addProduct(new Product("스니커즈", 9000));
        brandA.addProduct(new Product("가방", 2000));
        brandA.addProduct(new Product("모자", 1700));
        brandA.addProduct(new Product("양말", 1800));
        brandA.addProduct(new Product("액세서리", 2300));
        brandRepository.save(brandA);

        // 브랜드 B
        Brand brandB = new Brand("B");
        brandB.addProduct(new Product("상의", 10500));
        brandB.addProduct(new Product("아우터", 5900));
        brandB.addProduct(new Product("바지", 3800));
        brandB.addProduct(new Product("스니커즈", 9100));
        brandB.addProduct(new Product("가방", 2100));
        brandB.addProduct(new Product("모자", 2000));
        brandB.addProduct(new Product("양말", 2000));
        brandB.addProduct(new Product("액세서리", 2200));
        brandRepository.save(brandB);

        // 브랜드 C
        Brand brandC = new Brand("C");
        brandC.addProduct(new Product("상의", 10000));
        brandC.addProduct(new Product("아우터", 6200));
        brandC.addProduct(new Product("바지", 3300));
        brandC.addProduct(new Product("스니커즈", 9200));
        brandC.addProduct(new Product("가방", 2200));
        brandC.addProduct(new Product("모자", 1900));
        brandC.addProduct(new Product("양말", 2200));
        brandC.addProduct(new Product("액세서리", 2100));
        brandRepository.save(brandC);

        // 브랜드 D
        Brand brandD = new Brand("D");
        brandD.addProduct(new Product("상의", 10100));
        brandD.addProduct(new Product("아우터", 5100));
        brandD.addProduct(new Product("바지", 3000));
        brandD.addProduct(new Product("스니커즈", 9500));
        brandD.addProduct(new Product("가방", 2500));
        brandD.addProduct(new Product("모자", 1500));
        brandD.addProduct(new Product("양말", 2400));
        brandD.addProduct(new Product("액세서리", 2000));
        brandRepository.save(brandD);

        // 브랜드 E
        Brand brandE = new Brand("E");
        brandE.addProduct(new Product("상의", 10700));
        brandE.addProduct(new Product("아우터", 5000));
        brandE.addProduct(new Product("바지", 3800));
        brandE.addProduct(new Product("스니커즈", 9900));
        brandE.addProduct(new Product("가방", 2300));
        brandE.addProduct(new Product("모자", 1800));
        brandE.addProduct(new Product("양말", 2100));
        brandE.addProduct(new Product("액세서리", 2100));
        brandRepository.save(brandE);

        // 브랜드 F
        Brand brandF = new Brand("F");
        brandF.addProduct(new Product("상의", 11200));
        brandF.addProduct(new Product("아우터", 7200));
        brandF.addProduct(new Product("바지", 4000));
        brandF.addProduct(new Product("스니커즈", 9300));
        brandF.addProduct(new Product("가방", 2100));
        brandF.addProduct(new Product("모자", 1600));
        brandF.addProduct(new Product("양말", 2300));
        brandF.addProduct(new Product("액세서리", 1900));
        brandRepository.save(brandF);

        // 브랜드 G
        Brand brandG = new Brand("G");
        brandG.addProduct(new Product("상의", 10500));
        brandG.addProduct(new Product("아우터", 5800));
        brandG.addProduct(new Product("바지", 3900));
        brandG.addProduct(new Product("스니커즈", 9000));
        brandG.addProduct(new Product("가방", 2200));
        brandG.addProduct(new Product("모자", 1700));
        brandG.addProduct(new Product("양말", 2100));
        brandG.addProduct(new Product("액세서리", 2000));
        brandRepository.save(brandG);

        // 브랜드 H
        Brand brandH = new Brand("H");
        brandH.addProduct(new Product("상의", 10800));
        brandH.addProduct(new Product("아우터", 6300));
        brandH.addProduct(new Product("바지", 3100));
        brandH.addProduct(new Product("스니커즈", 9700));
        brandH.addProduct(new Product("가방", 2100));
        brandH.addProduct(new Product("모자", 1600));
        brandH.addProduct(new Product("양말", 2000));
        brandH.addProduct(new Product("액세서리", 2000));
        brandRepository.save(brandH);

        // 브랜드 I
        Brand brandI = new Brand("I");
        brandI.addProduct(new Product("상의", 11400));
        brandI.addProduct(new Product("아우터", 6700));
        brandI.addProduct(new Product("바지", 3200));
        brandI.addProduct(new Product("스니커즈", 9500));
        brandI.addProduct(new Product("가방", 2400));
        brandI.addProduct(new Product("모자", 1700));
        brandI.addProduct(new Product("양말", 1700));
        brandI.addProduct(new Product("액세서리", 2400));
        brandRepository.save(brandI);
    }
}