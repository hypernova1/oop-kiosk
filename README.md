### [29CM] 백엔드 포지션 과제

## 개발 환경
- JDK 17
- Spring Boot 3.3.1
- Spring Data JPA
- Gradle 8.5
- H2
- JUnit

## 프로젝트 구조
~~~
src/
  +- cart/              # 유저 도메인
    +- application/       # 비지니스 로직 관련
    +- domain/            # 도메인 모음
    +- infra/             # 외부 통신 (구현체 리포지토리 등)
    +- payload/           # DTO 모음
  +- common/            # 공통 로직
    +- exception/       
    +- lock/            
  +- config/            # 설정 관련
  +- order/             # 주문 도메인
  +- payment/           # 결제 도메인
  +- product/           # 상품 도메인
  +- util/              # 유틸 클래스 모음
  +- view/              # 콘솔 입/출력 관련 클래스
    +- input/
    +- output/
  - Application.java
~~~
각 도메인별로 패키지를 구성하였고, 도메인간 경계를 명확히 하기 위헤 도메인 의존 관계는 애그리 거트 내에서는 직접적으로 의존하도록, 다른 애그리거트의 경우에는 ID 값으로 간접 의존하도록 구성했습니다.

## 구현 관련
* 각 도메인 서비스별 소통은 도메인 객체가 아닌 DTO로 하여 외부에서 도메인을 조작하는 일을 방지하고 느슨하게 결합되도록 구현했습니다.
* 주문시 상품 재고를 감소시킬때 상품번호별로 분산락을 적용하여 상품 도메인에 대한 다른 작업들은 블로킹되지 않도록 했습니다.
  * 상품 재고 감소는 트랜잭션이 독립적으로 실행되게 하고, 주문 로직에서 예외 발생시 재고 롤백 이벤트를 발생시켜 독립적으로 동작하게 구현했습니다.
* 장바구니 도메인을 두고 View 클래스 에서 상품 추가나 주문시 장바구니를 기반으로 동작하도록 구현했습니다.
* 직접적인 도메인 조작은 도메인의 서비스에서 실행하고 View의 동작은 OrderProcessHandler에서 제어하도록 하여 View 클래스(OrderingMachine)에서는 입/출력만 담당하게 하였습니다.
* 사용자에게 입력 받을 객체인 Command를 두어 Command를 통해서만 입력값을 확인할 수 있도록 하였습니다.
* CommandLineRunner를 사용하여 앱 실행시 자동으로 OrderingMachine를 실행하도록 했습니다.

## 동시성 테스트
* ExecutorService 로 스레드를 생성한 후 동시에 ProductService.decreaseStock 메서드를 실행하게 하여 동시성 테스트를 진행했습니다.

### 기타 참고사항
* MemoryLockManager의 경우에는 메모리 기반 락을 구현하여 멀티 스레드 테스트를 통해 동시성 관련 테스트 진행 완료하였습니다.