# 1. 빌드 스테이지 (Maven을 사용한 빌드)
FROM maven:3.8-openjdk-17 AS builder

WORKDIR /app

# 의존성 파일 복사
COPY pom.xml .
RUN mvn dependency:go-offline

# 소스 코드 복사 및 빌드
COPY src /app/src
RUN mvn clean package -DskipTests

# 2. 실행 스테이지 (JAR 파일 실행)
FROM openjdk:17-jdk-alpine

WORKDIR /app

# 빌드된 JAR 파일을 컨테이너로 복사
COPY --from=builder /app/target/used-market-0.0.1-SNAPSHOT.jar /app/used-market.jar

# `application.yml`을 Dockerfile에 포함시키지 않고, 외부에서 마운트하려면 이 부분을 생략하면 된다.
# COPY src/main/resources/application.yml /app/config/application.yml

EXPOSE 8080

# JAR 파일 실행
CMD ["java", "-jar", "/app/used-market.jar"]
