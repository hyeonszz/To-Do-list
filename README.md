# 📝 Todo App

세션 기반 로그인을 지원하는 개인 투두 리스트 웹 애플리케이션입니다.  
Spring Boot + Thymeleaf 기반으로 구현되었으며, 로그인한 사용자만 자신의 Todo를 관리할 수 있습니다.

---

## 🤖 AI Assisted Development

본 프로젝트는 Claude Code를 활용한 실습 프로젝트입니다.
Claude Code를 사용하여 기본적인 코드 구조를 생성했습니다. 

---

## 🛠 Tech Stack

| 분류 | 기술 |
|------|------|
| Language | Java 17 |
| Framework | Spring Boot 4.0.3 |
| View | Thymeleaf |
| ORM | Spring Data JPA |
| Database | H2 (In-Memory) |
| Build | Gradle 9.3.0 |
| Security | Spring Security Crypto (BCrypt) |

---

## ✨ Features

- **회원가입 / 로그인 / 로그아웃** — 세션 기반 인증
- **비밀번호 암호화** — BCryptPasswordEncoder 적용
- **Todo CRUD** — 등록, 전체 조회, 단건 조회, 수정, 삭제
- **완료 처리** — 체크박스 클릭 시 즉시 반영
- **사용자 격리** — 로그인한 유저는 자신의 Todo만 조회/수정/삭제 가능
- **미인증 접근 차단** — 비로그인 상태로 `/todos` 접근 시 `/login`으로 리다이렉트
- **REST API** — `/api/todos` 엔드포인트 별도 제공

---

## 📁 Project Structure

```
src/main/java/com/group/todoapp/
├── controller/
│   ├── AuthController.java         # 회원가입 · 로그인 · 로그아웃
│   ├── TodoViewController.java     # Thymeleaf 뷰 컨트롤러
│   └── TodoController.java         # REST API 컨트롤러
├── service/
│   ├── UserService.java
│   └── TodoService.java
├── repository/
│   ├── UserRepository.java
│   └── TodoRepository.java
├── entity/
│   ├── User.java
│   └── Todo.java
└── dto/
    ├── SessionUser.java            # 세션 저장용 DTO
    ├── SignupRequest.java
    ├── LoginRequest.java
    ├── TodoCreateRequest.java
    ├── TodoUpdateRequest.java
    └── TodoResponse.java

src/main/resources/
├── templates/
│   ├── auth/
│   │   ├── signup.html
│   │   └── login.html
│   └── todo/
│       ├── list.html
│       └── create.html
└── application.properties
```

---

## 🚀 Getting Started

### 요구사항

- Java 17 이상
- 별도 DB 설치 불필요 (H2 In-Memory 사용)

### 실행 방법

```bash
# 저장소 클론
git clone https://github.com/your-username/todoapp.git
cd todoapp

# 애플리케이션 실행
./gradlew bootRun
```

> Windows 환경이라면 `gradlew.bat bootRun` 을 사용하세요.

서버가 정상 기동되면 브라우저에서 아래 주소로 접속합니다.

```
http://localhost:8080/login
```

---

## 🌐 URL 목록

### View (Thymeleaf)

| Method | URL | 설명 |
|--------|-----|------|
| GET | `/signup` | 회원가입 폼 |
| POST | `/signup` | 회원가입 처리 |
| GET | `/login` | 로그인 폼 |
| POST | `/login` | 로그인 처리 |
| POST | `/logout` | 로그아웃 |
| GET | `/todos` | Todo 목록 |
| GET | `/todos/new` | Todo 등록 폼 |
| POST | `/todos` | Todo 등록 처리 |
| POST | `/todos/{id}/done` | 완료 여부 토글 |
| POST | `/todos/{id}/delete` | Todo 삭제 |

### REST API

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/api/todos` | Todo 등록 |
| GET | `/api/todos` | 전체 조회 |
| GET | `/api/todos/{id}` | 단건 조회 |
| PATCH | `/api/todos/{id}` | 수정 |
| DELETE | `/api/todos/{id}` | 삭제 |

---

## 🗄 H2 Console

애플리케이션 실행 중 브라우저에서 DB를 직접 조회할 수 있습니다.

```
URL      : http://localhost:8080/h2-console
JDBC URL : jdbc:h2:mem:tododb
User     : sa
Password : (없음)
```

---

## 🗂 ERD

```
users
├── id          BIGINT (PK, Auto Increment)
├── email       VARCHAR (Unique, Not Null)
├── password    VARCHAR (BCrypt, Not Null)
└── nickname    VARCHAR (Not Null)

todos
├── id          BIGINT (PK, Auto Increment)
├── title       VARCHAR (Not Null)
├── description VARCHAR (Nullable)
├── is_done     BOOLEAN (Default: false)
└── user_id     BIGINT (FK → users.id)
```

---

## ⚙️ application.properties

```properties
# H2 Database
spring.datasource.url=jdbc:h2:mem:tododb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# H2 Console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

> `create-drop` 설정으로 인해 **서버 재시작 시 데이터가 초기화**됩니다.  
> 데이터를 영속하려면 `spring.datasource.url=jdbc:h2:file:./data/tododb` 로 변경하세요.

