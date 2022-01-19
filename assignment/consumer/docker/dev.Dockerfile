FROM openjdk:11-jdk-slim as builder
WORKDIR /usr/consumer-app

COPY target/consumer-0.0.1-SNAPSHOT.jar target/consumer-0.0.1-SNAPSHOT.jar

ARG JAR_FILE=target/consumer-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-jre-slim
WORKDIR /usr/consumer-app

RUN groupadd relay && useradd -g relay relay
USER relay

RUN mkdir -p /var/log

COPY --from=builder /usr/consumer-app/dependencies/ ./
COPY --from=builder /usr/consumer-app/spring-boot-loader/ ./
COPY --from=builder /usr/consumer-app/snapshot-dependencies/ ./
COPY --from=builder /usr/consumer-app/application/ ./

ENTRYPOINT ["java","-noverify", "org.springframework.boot.loader.JarLauncher"]
