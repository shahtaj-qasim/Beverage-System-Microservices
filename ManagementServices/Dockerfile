#Base image with alias as jdk
FROM openjdk:14 AS jdk
ENV APP_HOME=/root/dev/beverage
WORKDIR $APP_HOME
COPY . /usr/src/app
COPY . $APP_HOME
# Apply execute permission
RUN chmod +x gradlew
RUN ./gradlew build

FROM openjdk:14.0.1-jdk
WORKDIR /root/
EXPOSE 8091
COPY --from=jdk /root/dev/beverage/build/libs/ManagementServices-all.jar .
CMD ["java","-jar","ManagementServices-all.jar"]