# AWS Lambda and CDK Playground

Just a small playground, to learn implementing AWS CDK Stacks
in Java. And AWS Lambdas in Java with [Quarkus](https://quarkus.io).

### Modules

* [cdk](cdk/)
* [quarkusapilambda](quarkusapilambda/)

#### CDK

CDK Stack written in Java.

#### quarkusapilambda

Simple Java Microservice with a few dummy HTTP endpoints.
Using [Quarkus](https://quarkus.io). Gets deployed into an AWS Lambda
and optionally wired with an AWS API Gateway by via [cdk](cdk/)-Projekt.

### Build and deploy

The Lambda may be compiled to Java 11 or Native executable. Change
the `useNativ` flag in [CDKAPP.java](cdk/src/main/java/de/codemonaut/aws/CDKApp.java) accordingly.

```shell
mvn clean package # for JAVA 11
mvn clean package -Dquarkus.native.container-build=true # for Native
cd cdk
cdk deploy
...
cdk destroy
```
