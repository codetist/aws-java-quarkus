# AWS Lambda and CDK Playground

Just a small playground, to learn implementing AWS CDK Stacks
in Java. And AWS Lambdas in Java with [Quarkus](https://quarkus.io).

### Modules

* [cdk](cdk/README.md)
* [quarkusapilambda](quarkusapilambda/README.md)

### Build and deploy

```shell
mvn clean package
cd cdk
cdk deploy
...
cdk destroy
```
