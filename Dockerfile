FROM openjdk:17-jdk-slim as build
WORKDIR /workspace/app

RUN apt-get update && apt-get install -y curl

COPY mvnw .
COPY .mvn .mvn
COPY pom.xml .
COPY src src
COPY wait-for-it.sh /wait-for-it.sh

RUN chmod +x /wait-for-it.sh
RUN ./mvnw install -DskipTests
#RUN ./mvnw install
RUN mkdir -p target/dependency && (cd target/dependency; jar -xf ../*.jar)

FROM openjdk:17-jdk-slim
VOLUME /tmp
ARG DEPENDENCY=/workspace/app/target/dependency
COPY --from=build /wait-for-it.sh /wait-for-it.sh
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app

RUN apt-get update && apt-get install -y curl
ENTRYPOINT ["./wait-for-it.sh", "keycloak:8080", "java","-cp","app:app/lib/*","com.htwberlin.userservice.Application"]
