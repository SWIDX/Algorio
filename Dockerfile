FROM adoptopenjdk/openjdk11
LABEL org.opencontainers.image.source https://github.com/SWIDX/Algorio-Backend
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]
