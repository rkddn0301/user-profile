# JDK 17을 사용하는 베이스 이미지
FROM openjdk:17-jdk-slim

# 작업 디렉토리 설정
WORKDIR /app

# Gradle 래퍼와 build.gradle 파일을 먼저 복사 
COPY gradlew .
COPY gradle gradle
COPY build.gradle .
COPY settings.gradle .

# Gradle 의존성 다운로드
RUN ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src src

# 애플리케이션 빌드
RUN ./gradlew build --no-daemon

# 실행할 JAR 파일을 app.jar로 복사
RUN cp build/libs/user-profile-0.0.1-SNAPSHOT.jar app.jar

# 포트 8080 노출
EXPOSE 8080

# 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"] 