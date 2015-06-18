# [![ClickChat](http://public.acactown.org/clickchat-logo-readme-files.png)](http://bootenv.com)-REST API

[![license](https://img.shields.io/badge/license-Apache_2.0-blue.svg)]()
[![engine](https://img.shields.io/badge/JDK-v1.7+-yellow.svg)]()
[![gradle](https://img.shields.io/badge/gradle-v2.4-blue.svg)]()
[![Build Status](https://travis-ci.org/ClickChat/clickchat-api.svg?branch=master)](https://travis-ci.org/ClickChat/clickchat-api)
[![Coverage Status](https://coveralls.io/repos/ClickChat/clickchat-api/badge.svg)](https://coveralls.io/r/ClickChat/clickchat-api)

## Prerequisites

You will need the following things properly installed on your computer.

* [Git](http://git-scm.com/)
* [Java](http://nodejs.org/)

## Installation

Used [Gradle](http://www.gradle.org), a cross-platform build automation tool that help with our full development flow.
If you prefer [install Gradle](http://www.gradle.org/installation) or use a [Gradle wrapper](http://www.gradle.org/docs/current/userguide/gradle_wrapper.html) inside this project.

* `git clone git@github.com:ClickChat/clickchat-api.git` this repository
* change into the new directory `clickchat-api`

### Build project

```
./gradlew clean build
```

### Run tests

```
./gradlew clean test
```

### Generate Javadoc

```
./gradlew javaDoc
```

## Docker

As we want to run this REST-API in a [Docker](https://www.docker.com/) container, please take a look on a given `Dockerfile`. 
The [Dockerfile](Dockerfile) is simple and straightforward.

### Build and Running the Docker Image

```
./gradlew build
docker build --tag="clickchat/rest-api:latest" .
docker run -d -p 8080:8080 -v /my/local/path/logs:/usr/local/tomcat/logs clickchat/rest-api:latest
```

### Running with Docker Compose

[Docker Compose](https://docs.docker.com/compose/) is a tool for defining and running complex applications with Docker. 
With Compose, you define a multi-container application in a single file, then spin your application up in a single 
command which does everything that needs to be done to get it running.

```
./gradlew build
docker-compose up
```

## Further Reading / Useful Links

* [Gradle](http://gradle.org/)
* [Getting started with Gradle](http://gradle.org/getting-started-jvm/)
* [Google Guava](https://code.google.com/p/guava-libraries/wiki/GuavaExplained)
* [Docker Compose](https://docs.docker.com/compose/install/)

## Versions
 
 - 1.0.0 (current)

## License

[Apache-2.0](LICENSE)
