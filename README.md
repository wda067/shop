# 🛒 쇼핑몰 프로젝트

## 📌프로젝트 소개

본 프로젝트는 상품을 주문하고 결제할 수 있는 쇼핑몰 서비스입니다.

## 📌주요 기능

- **상품 등록 및 관리**
    - 관리자는 상품을 등록하고, 관리할 수 있습니다.
- **상품 구매**
    - 회원은 원하는 상품을 주문할 수 있습니다.
    - 주문한 상품은 토스페이먼츠 API를 통해 결제를 진행합니다.
- **알림**
    - 주문이 완료되면 회원에게 이메일을 전송합니다.

## 💻기술 스택

<p>
  <!-- Java -->
  <img src="https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" />
  <!-- Spring -->
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" />
  <!-- MySQL -->
  <img src="https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white" />
  <!-- Redis -->
  <img src="https://img.shields.io/badge/redis-%23DD0031.svg?&style=for-the-badge&logo=redis&logoColor=white" />
  <!-- Docker -->
  <img src="https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white" />
</p>

## 🏗️시스템 아키텍처

<img src="https://github.com/user-attachments/assets/f7b1a938-7989-4ec1-b885-6e2b2a836f19" width="60%" height="60%" />

## 💡기술적 고민
- **Feign Client를 활용한 외부 API 연동**
  - 토스페이먼츠 API 호출을 효율적으로 처리하기 위해 Feign Client를 도입했습니다.
  - 🔗 [OpenFeign 적용하기 (feat. Toss Payments API)](https://velog.io/@wda067/Spring-Boot-Feign-Client-%EC%A0%81%EC%9A%A9%ED%95%98%EA%B8%B0)
- **Spring Event와 비동기 처리를 통한 성능 최적화**
  - 주문 처리 속도를 개선하기 위해 `@TransactionalEventListener`와 `@Async`를 활용해 이메일 전송을 비동기적으로 처리하며 시스템 결합도를 낮췄습니다.
  - 🔗 [Spring Event로 비동기 이벤트 처리 (feat. @EventListener, @TransactionalEventListener)](https://velog.io/@wda067/Spring-Spring-Event-%EB%B9%84%EB%8F%99%EA%B8%B0-%EC%9D%B4%EB%B2%A4%ED%8A%B8-%EC%B2%98%EB%A6%AC-feat.-EventListener-TransactionalEventListener)
  - 🔗 [@Async를 활용한 이메일 전송 비동기 처리](https://velog.io/@wda067/Spring-Boot-Async%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EB%B9%84%EB%8F%99%EA%B8%B0-%EC%B2%98%EB%A6%AC)
- **MDC로 주문 추적 및 비동기 로그 관리**
  - 비동기 환경에서 로그를 효과적으로 추적하기 위해 MDC(Mapped Diagnostic Context)를 사용했습니다.
  - 🔗 [MDC를 활용한 주문 추적 및 비동기 로그 관리](https://velog.io/@wda067/Spring-Boot-MDC%EB%A5%BC-%ED%99%9C%EC%9A%A9%ED%95%9C-%EC%A3%BC%EB%AC%B8-%EC%B6%94%EC%A0%81-%EB%B0%8F-%EB%B9%84%EB%8F%99%EA%B8%B0-%EB%A1%9C%EA%B7%B8-%EA%B4%80%EB%A6%AC)