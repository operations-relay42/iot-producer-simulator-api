FROM openjdk:11-jdk as builder
WORKDIR /usr/app

ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract

FROM openjdk:11-jre
WORKDIR /usr/app
RUN mkdir -p /var/log

COPY --from=builder /usr/app/dependencies/ ./
COPY --from=builder /usr/app/spring-boot-loader/ ./
COPY --from=builder /usr/app/snapshot-dependencies/ ./
COPY --from=builder /usr/app/application/ ./
ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]