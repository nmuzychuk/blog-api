FROM java:8

COPY . /usr/src/blog-api
WORKDIR /usr/src/blog-api

RUN ./gradlew build

ENTRYPOINT ["sh", "-c", "java -jar build/libs/blog-*.jar"]
