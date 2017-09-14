# Blog API
[![Build Status](https://travis-ci.org/nmuzychuk/blog-api.svg?branch=master)](https://travis-ci.org/nmuzychuk/blog-api)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/9f42572d516b4df495cd146d63c7c571)](https://www.codacy.com/app/nmuzychuk/blog-api)
[![Codacy Badge](https://api.codacy.com/project/badge/Coverage/9f42572d516b4df495cd146d63c7c571)](https://www.codacy.com/app/nmuzychuk/blog-api)

## Overview
Blog REST API built with Spring

[Client application](https://github.com/nmuzychuk/blog-web)
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
