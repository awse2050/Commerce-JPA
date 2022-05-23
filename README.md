# Commerce-JPA 

> Spring Boot 및 JPA 연관관계 매핑 연습을 위한 프로젝트

> `장바구니` - `주문` - `결제` 수순이 핵심

### 개발환경
+ Spring Boot '2.4.5'
+ Java 8
+ Gradle
+ Spring Data JPA
+ Querydsl
+ Postgresql -> MariaDB(RDS)
+ Swagger

1. 회원 
  + 회원 등록 (검증에 대한 로직처리 필요) [O]
  + ( 회원 조회 )
  + 로그인 [O]

---
2. 상품 (등록 및 수정 - 관리자)
+ ( 상품등록 )
+ 상품조회 [O]
+ ( 상품 수정 )

---
3. 주문
+ 상품 주문
+ 상품 주문 취소
+ 주문 내역 조회

---
4. 장바구니 ( 5.15~ 추가 ) [O]
+ 장바구니 조회
+ 상품 담기
+ 장바구니 상품 개수 수정
+ 상품 빼기
+ 주문하기

5. 결제 시스템 (5.27) [O]
+ 5.27일 기준 간단하게 동작이 되게 해놓은 상태
+ BootPay API (O)

6. 찜 목록 구현 (6.3) [O]
+ 추가 및 삭제 API(6.3)
+ API + 프론트 연동 

7. OAuth2.0 
+ Google 

8. EC2 서버 배포
+ Amazon Linux 2

9. 상품리뷰 (TO-DO)
+ 리뷰 작성
+ 리뷰 삭제
+ 리뷰 수정

### TO-DO List (Update. 6.17)
+ 화면단 CSS 작성
+ README.md 프로젝트 사진첨부
+ HTTPS 재설정
+ 리팩토링
+ 상품리뷰 Entity 추가
