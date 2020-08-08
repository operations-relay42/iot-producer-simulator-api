FROM openjdk:11-jdk-slim as builder
WORKDIR /usr/app

COPY . /usr/app
RUN ./gradlew clean build
RUN mkdir -p build/dependency && (cd build/dependency; jar -xf ../libs/*.jar)

FROM openjdk:11-jre-slim
WORKDIR /usr/app
RUN mkdir -p /var/log

EXPOSE 8080
ARG DEPENDENCY=/usr/app/build/dependency

COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

ENTRYPOINT ["java","-noverify","-cp","app:app/lib/*","br.com.iot.producer.simulator.api.ApplicationStarter"]