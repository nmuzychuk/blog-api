FROM java:8

COPY . /usr/src/blog-api
WORKDIR /usr/src/blog-api

RUN ./gradlew build

CMD ["java", "-jar", "build/libs/blog-1.0-SNAPSHOT.jar"]
