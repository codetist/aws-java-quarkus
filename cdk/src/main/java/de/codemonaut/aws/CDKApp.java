package de.codemonaut.aws;

import software.amazon.awscdk.App;
import software.amazon.awscdk.Environment;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.Tags;

public class CDKApp {

  static StackProps createStackProperties() {
    String account = System.getenv("CDK_DEPLOY_ACCOUNT");
    String region = System.getenv("CDK_DEPLOY_REGION");

    if (account == null) {
      return StackProps.builder().build();
    }

    Environment environment = Environment.builder()
      .account(account)
      .region(region)
      .build();
    return StackProps.builder()
      .env(environment)
      .build();
  }

  public static void main(String[] args) {

    App app = new App();
    String appName = "awsQuarkusStack";
    String stackId = "aws-quarkus-stack";

    Tags.of(app).add("application", appName);

    new Stack(app, stackId, createStackProperties());
    app.synth();
  }
}
