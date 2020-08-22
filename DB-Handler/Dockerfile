#Base image with alias as jdk
FROM openjdk:11.0.8 AS jdk
ENV APP_HOME=/root/dev/db/
WORKDIR $APP_HOME
COPY . $APP_HOME
# Apply execute permission
RUN chmod +x gradlew
RUN ./gradlew build

FROM openjdk:11.0.8-jre
WORKDIR /root/
EXPOSE 9999
COPY --from=jdk /root/dev/db/build/libs/DB-Handler-all.jar .
CMD ["java","-jar","DB-Handler-all.jar"]