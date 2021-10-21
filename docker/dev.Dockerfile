FROM openjdk:11-jdk-slim as builder
WORKDIR /usr/app

COPY gradlew .
COPY gradle gradle
COPY build.gradle build.gradle
COPY gradle.properties gradle.properties
COPY settings.gradle settings.gradle
COPY src src

RUN ./gradlew --no-daemon build -x test

RUN java -Djarmode=layertools -jar /usr/app/build/libs/iot-producer-simulator-api.jar extract

FROM openjdk:11-jre-slim
WORKDIR /usr/app

RUN groupadd relay && useradd -g relay relay
USER relay

RUN mkdir -p /var/log

COPY --from=builder /usr/app/dependencies/ ./
RUN true # https://github.com/moby/moby/issues/37965
COPY --from=builder /usr/app/spring-boot-loader/ ./
RUN true
COPY --from=builder /usr/app/snapshot-dependencies/ ./
RUN true
COPY --from=builder /usr/app/application/ ./

ENTRYPOINT ["java","-noverify", "org.springframework.boot.loader.JarLauncher"]