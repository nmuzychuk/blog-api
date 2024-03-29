# Blog API
[![Java CI with Gradle](https://github.com/nmuzychuk/blog-api/actions/workflows/gradle.yml/badge.svg)](https://github.com/nmuzychuk/blog-api/actions/workflows/gradle.yml)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f42572d516b4df495cd146d63c7c571)](https://www.codacy.com/app/nmuzychuk/blog-api)

## Overview
Blog REST API built with Spring. Specific request mappings require JSON Web Token. Swagger UI page generated from the OpenAPI specification document is available at /swagger-ui.html

https://my-blog-rest-api.herokuapp.com/swagger-ui.html

[AngularJS client application](https://github.com/nmuzychuk/blog-web)
## Docker
```
docker build -t blog-api .
docker run -p 8090:8090 blog-api
```

## Vagrant
```
./gradlew build
java -jar build/libs/blog-1.0-SNAPSHOT.jar
```

## Test
Run JUnit tests
```
./gradlew test
```

## License
This project is released under the [MIT License](LICENSE.txt)
