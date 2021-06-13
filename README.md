# Commerce-JPA

> Spring Boot 및 연관관계 매핑 연습을 위한 프로젝트

> 회원, 상품, 주문의 설계 이후 점차 늘려나가는 식으로 진행

> 추후 결제시스템 까지 고려할 예정.
 
> Travis CI 사용 중 문제가 생겨 우선은 수동배포로 진행.

> [사이트 바로가기](http://www.commerce-jpa.com)

### 개발환경
+ Spring Boot '2.4.5'
+ Java 8
+ Gradle
+ Spring Data JPA
+ Querydsl
+ Postgresql -> MariaDB(RDS)
+ Swagger

1. 회원 
  + 회원 등록 (검증에 대한 로직처리 필요)
  + ( 회원 조회 )
  + 로그인 

---
2. 상품 (등록 및 수정 - 관리자)
+ ( 상품등록 )
+ 상품조회
+ ( 상품 수정 )

---
3. 주문
+ 상품 주문
+ 상품 주문 취소
+ 주문 내역 조회

---
4. 장바구니 ( 5.15~ 추가 ) 
+ 장바구니 조회
+ 상품 담기
+ 장바구니 상품 정보 수정
+ 상품 빼기
+ 주문하기

5. 결제 시스템 (5.27)
+ 5.27일 기준 간단하게 동작이 되게 해놓은 상태
+ BootPay API (O)

6. 찜 목록 구현 (6.3)
+ 추가 및 삭제 API(6.3)
+ API + 프론트 연동

7. OAuth2.0 
+ Google 

8. EC2 서버 배포
+ Amazon Linux 2

### TO-DO List (6.12)
+ 화면단 CSS 작성
+ README.md 프로젝트 사진첨부
+ HTTPS 재설정
+ 리팩토링
