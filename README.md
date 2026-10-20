# Spring Boot 게시판 평가 답안

첨부 HWP와 요청한 문항 1~5를 기준으로 만든 MVC 게시판입니다.
Spring Boot 3.5.7 / Java 17 / Gradle / MySQL / MyBatis Starter 3.0.4 / Thymeleaf를 사용합니다.
문서에는 테이블명·컬럼·패키지가 지정되어 있지 않아 `posts`, `com.example.post`로 구성했습니다.

## 실행 순서

1. JDK 17과 MySQL을 설치합니다.
2. MySQL Workbench 등에서 `database/schema.sql` 전체를 실행합니다.
3. `src/main/resources/application.properties`의 접속 정보에 맞게 환경변수를 설정합니다.
4. 프로젝트 폴더를 IDE에서 Gradle 프로젝트로 열거나 아래 명령으로 실행합니다.

```powershell
$env:DB_USERNAME = 'root'
$env:DB_PASSWORD = '본인의 MySQL 비밀번호'
.\gradlew.bat bootRun
```

브라우저: http://localhost:8080
DB 기본 주소: localhost:3306/post_test. 다른 주소는 DB_URL 환경변수로 지정합니다.
비밀번호를 소스에 기록하거나 GitHub에 올리지 마세요.
Gradle Wrapper는 최초 실행 시 Gradle을 내려받으므로 인터넷이 필요합니다.

## 문항별 제출 파일

| 문항 | 파일 |
| --- | --- |
| 1 | src/main/resources/mapper/PostMapper.xml |
| 2 | src/main/java/com/example/post/dto/Post.java, service/PostService.java, service/PostServiceImpl.java, mapper/PostMapper.java |
| 3 | src/main/java/com/example/post/controller/PostController.java, build.gradle |
| 4 | src/main/resources/templates/index.html, Post/list.html, Post/read.html, Post/insert.html, Post/update.html |
| 5 | dto/Page.java, controller/PostController.java, service/PostService.java, service/PostServiceImpl.java, mapper/PostMapper.java, src/main/resources/mapper/PostMapper.xml |

Java 파일은 모두 `src/main/java/com/example/post/` 아래에 있습니다.
`submission/문항별_답안.md`에 문항 순서대로 코드가 정리되어 있습니다.
1~4도 페이징이 포함된 최종본을 제출하면 됩니다. 문항5의 추가·변경 부분은 별도로 설명했습니다.
문항5에서 Post.java는 추가 변경이 필요 없습니다. HWP의 답안 목록에는 XML이 빠져 있으나 요구사항에 따라 XML도 포함했습니다.

## 동작

- 메인 → 목록 → 등록 → 조회 → 수정·삭제
- URL은 대문자 P를 포함한 `/Post`로 통일했습니다.
- 조회 링크는 `/Post/read?PostNo=글번호`입니다.
- 한 페이지 10개, 페이지 번호 묶음 5개, 최신 글부터 출력합니다.
- 0 이하 페이지는 1, 마지막을 초과하는 페이지는 마지막 페이지로 보정합니다.
- 등록·수정·삭제 후 리다이렉트하고, 삭제는 POST로 처리합니다.
- 없는 글은 404, 입력 오류는 폼의 오류 메시지로 처리합니다.
- 로그인·권한 기능은 평가 요구사항에 포함되어 있지 않아 구현하지 않았습니다.

## 확인 방법

```powershell
.\gradlew.bat test
.\gradlew.bat bootJar
```

테스트는 H2의 MySQL 호환 모드를 사용하여 실제 MyBatis XML의 CRUD·페이징,
컨트롤러 요청, Thymeleaf 템플릿, 입력 검증을 확인합니다.
실제 MySQL 8.0에서 XML CRUD·페이징을 검수했고, 캐시된 Spring Boot 4 환경에서
동일 소스의 웹 요청·템플릿·입력 검증도 보조 검수했습니다.
지정 버전의 Gradle 전체 빌드와 수동 브라우저 확인은 아직 실행하지 못했습니다.
자세한 결과와 환경 해결 절차는 `환경설정과_검수결과.md`를 확인하세요.

## GitHub 제출

첨부 HWP에는 전체 소스가 들어 있는 GitHub 저장소 URL 제출 문항도 있습니다.
예시 저장소명: `Post_TEST_본인이름`. 본 압축파일은 저장소에 업로드할 소스이며,
실제 GitHub 저장소 생성·업로드는 하지 않았습니다. 별도 지정된 저장소가 있다면 그곳에 올리세요.

## 호환성 참고

MyBatis 공식 호환표: https://mybatis.org/spring-boot-starter/mybatis-spring-boot-autoconfigure/
Starter 3.0 계열은 Java 17 이상 및 Spring Boot 3.2~3.5에 대응합니다.
