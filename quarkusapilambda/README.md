# quarkusapilambda

Sample HTTP-API application with a single endpoint `/note` providing a
`GET` and `POST` method. The project uses [Quarkus](https://quarkus.io).
This application is deployed as AWS Lambda by the [cdk](../cdk/).

### Dev-Mode

Quarkus provides a dev mode for local live coding and testing:

```shell
mvn compile quarkus:dev
```

### Package application

#### Executable JAR

```shell
mvn package
```

#### Native Executable

Native compilation requires [GraalVM](https://www.graalvm.org). GraalVM can be
setup locally or downloaded as Docker container during build (requires Docker or
Podman to be installed).

```shell
# Native build with local GraalVM
mvn package -Pnative

# Native build with GraalVM from Docker image
mvn package -Pnative -Dquarkus.native.container-build=true
```

*Building native executable takes a few minutes. Even on fast machines!*

### There is more

More Maven options and configurations are documented
in the [Quarkus docs](https://quarkus.io/guides/maven-tooling).
