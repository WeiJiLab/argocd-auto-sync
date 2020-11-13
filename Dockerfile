# 使用JDK8作为基础镜像
FROM openjdk:8-jdk-alpine

VOLUME /tmp

COPY build/libs/argocd-auto-sync-0.0.1.jar app.jar


ENTRYPOINT ["java","-jar","app.jar"]
