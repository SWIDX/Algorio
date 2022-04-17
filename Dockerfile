FROM adoptopenjdk/openjdk11
LABEL org.opencontainers.image.source https://github.com/SWIDX/Algorio-Backend
ARG JAR_FILE=build/libs/*.jar
ENV CLIENT_ID=
ENV CLIENT_SECRET=
COPY ${JAR_FILE} app.jar
ENTRYPOINT [ \
    "java", \
    "-Dspring.security.oauth2.client.registration.google.client-id=${CLIENT_ID}", \
    "-Dspring.security.oauth2.client.registration.google.client-secret=${CLIENT_SECRET}", \
    "-jar", \
    "/app.jar" \
    ]
