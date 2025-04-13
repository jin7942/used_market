# NextUse - 중고의 새로운 기준

**실시간 알림과 직관적인 UI를 가지고 있는 중고 거래 플랫폼**

-   프론트: [https://github.com/jin7942/used_market_frontend](https://github.com/jin7942/used_market_frontend)
-   백엔드: [https://github.com/jin7942/used_market](https://github.com/jin7942/used_market)
-   이미지 업로드 서버 (독립 운영): https://github.com/jin7942/uploadServer

---

## 소개

NextUse는 실시간 알림과 마이페이지 기능을 중심으로 한 중고거래 플랫폼입니다. 이 프로젝트는 관심사 분리와 책임 분산을 통한 SOLID 원칙 기반 설계를 바탕으로 구축되었으며, 아래와 같은 주요 기능을 제공합니다:

-   실시간 알림(SSE): 사용자가 상품을 찜하거나 구매하면, 판매자에게 SSE(Server-Sent Events) 기반 실시간 알림 전송
-   JWT 인증 기반 사용자 인증 시스템
-   이미지 분리 업로드 구조 (독립 이미지 업로드 서버 🔗 uploadServer)
-   DTO 매핑 및 Entity ↔ DTO 유틸 분리 설계
-   토글 방식 마이페이지 구성 (판매중/판매완료/찜/구매 구분)
-   SOLID 원칙 준수를 위한 관심사 분리 및 책임 분산 구조
-   차후 릴리즈를 통해 실시간 채팅 등의 기능도 순차적으로 업데이트할 예정입니다.

---

## 기술 스택

| 구성         | 기술                           |
| ------------ | ------------------------------ |
| 프론트엔드   | React, Vite, Bootstrap, Axios  |
| 벡엔드       | Spring Boot 3.x, JPA, JWT, SSE |
| 데이터베이스 | MySQL                          |
| 인프라       | Docker, Nginx, Ubuntu 20.04    |
| 공통 도구    | ModelMapper, Lombok            |

---

## 주요 기능

-   회원가입 / 로그인 (JWT 인증)
-   상품 등록 / 상세 / 리스트 / 상품 상태 자동 변경
-   찜하기 및 찜 해제
-   구매 기능 + 자동 상품 상태 변경
-   **실시간 알림 (SSE)**
-   마이페이지 토글별 분리 조회
-   이미지 업로드 서버 분리 운영

---

## ERD

> 채팅 / 리브와 등 일부 테이블은 현재 사용되지 않으며, **차후 리리즈에 포함될 예정**

![ERD](./used_market_erd.png)

---

## 프로젝트 디렉터리 구조

```bash
usedmarket/
├── src/
│   ├── main/
│   │   ├── java/com/jinfw/infra/usedmarket/
│   │   │   ├── common/              # 공통 유틸리티, 상수, 예외처리
│   │   │   │   ├── base/            # BaseEntity 등 공통 상속 엔티티
│   │   │   │   ├── constants/       # 공통 ENUM (공통 코드 상수 등)
│   │   │   │   ├── exception/       # 커스텀 예외
│   │   │   │   └── util/            # JWT, DTO 변환 유틸
│   │   │   ├── code/                # 공통 코드(Code, CodeGroup)
│   │   │   ├── img/                 # 이미지 업로드 도메인
│   │   │   ├── item/                # 상품 도메인
│   │   │   ├── user/                # 사용자 도메인
│   │   │   ├── orders/              # 주문(결제) 도메인
│   │   │   ├── notification/        # 알림 도메인 (SSE 기반)
│   │   │   └── chat/                # (릴리즈 예정) 실시간 채팅 도메인
│   │   └── resources/
│   │       ├── application.yml      # 프로젝트 설정 파일
│   │       └── static/              # 정적 리소스 (이미지 등)
│   └── test/
│       └── java/                    # 테스트 코드
│
├── pom.xml                          # Maven 빌드 설정 파일
└── README.md                        # 프로젝트 소개 문서

```

## 설계 구조의 특징

-   `Controller / Service / Repository / DTO / VO` 계층 분리
-   공통 응답 포맷 `ResponseVo<T>` 사용
-   `BaseEntity`로 생성일, 수정일 등 공통 필드 관리 (`createDT`, `updateDT`)
-   공통 상수는 ENUM으로 관리
-   DTO ↔ Entity 변환은 ModelMapper 유틸로 통일
-   JPA + Native Query 혼용 (목적에 따라 선택)

---

## 설치 및 실행

-   도커 이미지 생성 후 업데이트 예정.

---

## SOLID 원칙 적용

-   **단일 책임 원칙**: 각 서비스는 한가지 기능만 단단
-   **계보 - 포사 원칙**: 알림 로직은 채팅 알림으로도 확장 가능
-   **인터페이스 분리 원칙**: 유저과 아이템 각각 독립적인 서비스 구조
-   **의연 역전 원칙**: 컨트롤러는 서비스 인터페이스에만 의연

---

## API 명세 (예정)

> Swagger 적용 예정  
> 대부분의 API는 `/api/*` 패턴 사용  
> JWT 헤더는 `Authorization: Bearer {token}`

---

## 업데이트 예정

-   실시간 채팅 (WebSocket)
-   사용자 리뷰 및 평점
-   관리자 대쉬보드
-   CI/CD 자동화 서버 연동
-   오픈소스 배포용 도커 이미지
