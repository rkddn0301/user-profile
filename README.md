# User Profile & Point Charge Service

회원프로필 관리 및 토스페이먼츠 결제를 통한 포인트 충전 서비스

## 기술 스택

- **Java 17**
- **Spring Boot 3.5.3**
- **Spring Data JPA**
- **QueryDSL 5.0.0**
- **MySQL 8.0**
- **Gradle**
- **Docker**

## 외부 라이브러리 사용 목적

- `spring-boot-starter-data-jpa`: JPA를 통한 데이터베이스 접근
- `spring-boot-starter-web`: RESTful API 구현

- `mysql-connector-j`: MySQL 데이터베이스 연결
- `querydsl-jpa`: 타입 안전한 동적 쿼리 작성
- `querydsl-apt`: QueryDSL Q타입 자동 생성
- `lombok`: 보일러플레이트 코드 자동 생성 (getter, setter, 생성자 등)

## 주요 기능 및 API 엔드포인트

### 회원 프로필 목록 조회

- `GET /api/profiles` - 회원 프로필 목록 조회

#### 정렬(sorting) 가능한 필드

- `userName` : 회원 이름
- `viewCount` : 조회수
- `createdAt` : 등록일

정렬 예시:
- `?sort=userName,asc` : 회원 이름 오름차순
- `?sort=viewCount,desc` : 조회수 내림차순
- `?sort=createdAt,desc` : 등록일 최신순

#### 예시: 프로필 목록 조회 요청/응답

요청:

```
GET /api/profiles?page=0&size=5&sort=userName,asc
```

응답(JSON):

```json
{
  "content": [
    {
      "userName": "Alice",
      "viewCount": 82,
      "createdAt": "2024-06-23T19:00:00.000+00:00"
    },
    {
      "userName": "Bob",
      "viewCount": 92,
      "createdAt": "2024-06-24T20:00:00.000+00:00"
    },
    {
      "userName": "Charlie",
      "viewCount": 99,
      "createdAt": "2024-06-25T21:00:00.000+00:00"
    },
    {
      "userName": "강민수",
      "viewCount": 10,
      "createdAt": "2024-06-01T10:00:00.000+00:00"
    },
    {
      "userName": "김나영",
      "viewCount": 25,
      "createdAt": "2024-06-02T09:30:00.000+00:00"
    }
  ],
  "pageable": {
    "pageNumber": 0,
    "pageSize": 5,
    "sort": {
      "empty": false,
      "sorted": true,
      "unsorted": false
    },
    "offset": 0,
    "paged": true,
    "unpaged": false
  },
  "last": false,
  "totalPages": 6,
  "totalElements": 30,
  "size": 5,
  "number": 0,
  "sort": {
    "empty": false,
    "sorted": true,
    "unsorted": false
  },
  "first": true,
  "numberOfElements": 5,
  "empty": false
}
```

---

### 회원 프로필 상세 조회수 업데이트

- `POST /api/profiles/{userNo}/view` - 회원 프로필 상세 조회수 업데이트

- {userNo} : 조회수를 증가시킬 회원의 고유 번호(path variable)

#### 예시: 프로필 상세 조회수 업데이트 요청/응답

조회수 변경 전(DB 상태):

<img width="443" height="44" alt="image" src="https://github.com/user-attachments/assets/ba27432d-1b01-4fb5-890d-db0e4defe7cc" />


요청: 

```
POST /api/profiles/1/view
```

응답(String):

```
success
```

조회수 변경 후(DB 상태):

<img width="459" height="49" alt="image" src="https://github.com/user-attachments/assets/76342739-59c4-41a5-ba46-08cf74c4ae0c" />


***

### 포인트 충전

- `POST /api/points/toss/confirm` - 토스페이먼츠 결제 승인

#### 결제 승인 및 포인트 적립 흐름

1. 클라이언트가 토스페이먼츠 결제 승인 요청을 보냄
2. 서버가 결제 승인 및 포인트 적립 처리
3. 결과를 응답(JSON)으로 반환
4. DB(POINT_CHARGE)에 충전된 포인트(AMOUNT) 저장

#### 예시: 포인트 충전 요청/응답

요청(JSON):

```json
{
  "paymentKey": "테스트키",
  "orderId": "테스트주문",
  "amount": 1000,
  "userNo": 1
}
```

응답(JSON):

```json
{
    "orderId": "테스트주문",
    "userNo": 1,
    "chargedAmount": 1000,
    "paymentKey": "테스트키",
    "message": "결제 승인 성공 및 포인트 충전 완료",
    "status": "success"
}
```

응답 후 포인트 충전 확인(DB 상태):

<img width="182" height="47" alt="image" src="https://github.com/user-attachments/assets/4e200bea-b5b4-4cde-a9cc-5c630c9af9c3" />


## 프로젝트 실행 방법

1. **프로젝트 다운로드**

   ```bash
   git clone https://github.com/rkddn0301/user-profile
   cd user-profile
   ```

2. **DB 생성 및 추가**

   - 위의 `User(DB).sql` 파일을 MySQL에 적용

3. **Docker 이미지 빌드**

   ```bash
   docker build -t user-profile .
   ```

4. **Docker 컨테이너 실행**

   - **Git Bash 예시**
     ```gitbash
     docker run -p 8080:8080 \
       -e SPRING_DATASOURCE_URL=jdbc:mysql://<DB_IP>:3306/User \
       -e SPRING_DATASOURCE_USERNAME=<DB_USER> \
       -e SPRING_DATASOURCE_PASSWORD=<DB_PASS> \
       user-profile
     ```

   - **CMD 예시**
     ```cmd
     docker run -p 8080:8080 ^
       -e SPRING_DATASOURCE_URL=jdbc:mysql://<DB_IP>:3306/User ^
       -e SPRING_DATASOURCE_USERNAME=<DB_USER> ^
       -e SPRING_DATASOURCE_PASSWORD=<DB_PASS> ^
       user-profile
     ```


   - **실행 성공 화면**
   <img width="568" height="402" alt="image" src="https://github.com/user-attachments/assets/e50c9370-02ac-4885-a6b4-7b1d6f852c68" />
   <img width="566" height="91" alt="image" src="https://github.com/user-attachments/assets/7ceb2088-7ac7-4f1b-a01d-6ab1daa47d00" />


    
