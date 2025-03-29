# Musinsa Assignment

## 1. 구현 범위

본 프로젝트는 무신사의 광고 플랫폼 서비스 Backend Engineer 포지션 과제 구현을 위해 아래의 4가지 API 기능을 제공합니다.

### 조회용 API
1. **카테고리별 최저가격 조회 API**  
   - 각 카테고리(상의, 아우터, 바지, 스니커즈, 가방, 모자, 양말, 액세서리)에서 최저가격을 제공하는 브랜드와 해당 상품 가격을 조회  
   - 전체 카테고리 최저가격의 총액도 함께 제공

2. **단일 브랜드 구매 최저 총액 조회 API**  
   - 모든 카테고리 상품을 한 브랜드에서 구매할 경우의 총액을 계산하여, 최저 총액을 제공하는 브랜드와 상세 가격 정보를 반환

3. **특정 카테고리 최저/최고 가격 조회 API**  
   - 입력된 카테고리명에 대해 최저가격과 최고가격을 제공하는 브랜드 및 각 상품의 가격을 조회

### 관리자용 API (CRUD)
4. **브랜드 및 상품 관리 API**  
   - 운영자가 새로운 브랜드를 등록하고, 해당 브랜드의 상품을 추가, 변경, 삭제할 수 있도록 지원

### 추가 사항
- **초기 데이터 로딩:**  
  - `DataLoader` 클래스를 통해 과제에서 제공된 9개 브랜드(A ~ I)와 8개 카테고리 상품의 가격 데이터를 H2 데이터베이스에 미리 등록합니다.
- **테스트:**  
  - 단위 테스트와 통합 테스트를 통해 각 기능(비즈니스 로직, API 응답 등)을 검증합니다.
- **기술 스택:**  
  - **Java, Spring Boot, Spring Data JPA:** 기본 애플리케이션 프레임워크 및 ORM  
  - **H2 Database:** 개발/테스트 환경용 인메모리 데이터베이스 (실제 운영 환경에서는 MySQL 전환 가능)  
  - **JUnit, Mockito, MockMvc:** 단위 및 통합 테스트 도구  
  - **향후 확장:** 분산 시스템, Kafka, Redis, AWS, Kubernetes, Docker, AWS CloudWatch 관련 기술은 실제 운영 환경에서 확장 가능

---

## 2. 코드 빌드, 테스트, 실행 방법

### Prerequisites
- **Java 21** 이상 설치
- **Maven** 설치 (또는 Maven Wrapper 사용)
- IDE (IntelliJ IDEA, Eclipse 등) 또는 터미널 사용

### 빌드
프로젝트 루트 디렉토리에서 아래 명령어를 실행하여 코드를 빌드합니다.
```bash
mvn clean compile
```

### 테스트
단위 테스트 및 통합 테스트를 실행하려면 아래 명령어를 사용합니다.
```bash
mvn test
```
- 테스트 결과는 터미널 및 IDE의 테스트 리포트를 통해 확인할 수 있습니다.

### 실행
서버를 실행하려면 다음 명령어를 사용합니다.
```bash
mvn spring-boot:run
```
실행 후 브라우저나 Postman, curl 등을 사용해 아래 API 엔드포인트를 테스트할 수 있습니다:

- **고객용 API 예:**
  - 카테고리별 최저가격 조회: `GET http://localhost:8080/api/v1/summary/category`
  - 단일 브랜드 최저총액 조회: `GET http://localhost:8080/api/v1/summary/brand`
  - 특정 카테고리 최저/최고 조회 (예: 상의): `GET http://localhost:8080/api/v1/summary/category/상의`

- **운영자용 API 예:**
  - 신규 브랜드 등록: `POST http://localhost:8080/api/v1/admin/brands`
  - 브랜드 업데이트: `PUT http://localhost:8080/api/v1/admin/brands/{id}`
  - 브랜드 삭제: `DELETE http://localhost:8080/api/v1/admin/brands/{id}`

### 통합 UI 테스트

이 백엔드 프로젝트는 프론트엔드(React) 애플리케이션과 완벽하게 통합되어 있습니다. 프론트엔드 빌드 산출물은 백엔드의 `src/main/resources/static` 폴더에 복사되어, 서버 실행 시 정적 파일로 서빙됩니다.

- **UI 확인:**  
  서버가 실행되면, 브라우저에서 `http://localhost:8080`으로 접속하여 통합된 사용자 인터페이스(UI)를 확인할 수 있습니다.  
  - 대시보드와 관리자 페이지가 포함된 프론트엔드가 제공되며, 이를 통해 API 연동 및 전체 시스템의 동작을 직접 테스트할 수 있습니다.