# 微信云托管 Spring Boot 部署
# 构建阶段
FROM maven:3.8.1-jdk-8-slim AS build

WORKDIR /app
COPY pom.xml .
COPY src ./src

RUN mvn -B -DskipTests package

# 运行阶段
FROM openjdk:8-jdk-alpine

WORKDIR /app

# mysql 客户端：INIT_DB=true 时可在容器启动阶段导入备份
RUN apk add --no-cache mysql-client bash

COPY --from=build /app/target/shiveoj-backend-0.0.1-SNAPSHOT.jar app.jar
COPY sql ./sql
COPY docker/entrypoint.sh ./docker/entrypoint.sh
# 构建时强制去除 Windows CRLF，避免 set -e\r 报错
RUN sed -i 's/\r$//' ./docker/entrypoint.sh && chmod +x ./docker/entrypoint.sh

# 监听端口须与云托管控制台「监听端口」一致
EXPOSE 80

ENTRYPOINT ["sh", "/app/docker/entrypoint.sh"]
