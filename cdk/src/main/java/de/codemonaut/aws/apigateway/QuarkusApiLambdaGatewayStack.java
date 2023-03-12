package de.codemonaut.aws.apigateway;

import de.codemonaut.aws.lambda.QuarkusApiLambda;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

import static de.codemonaut.aws.apigateway.ApiGatewayTypeEnum.HTTP_GATEWAY;

public class QuarkusApiLambdaGatewayStack extends Stack {

  public QuarkusApiLambdaGatewayStack(Construct scope,
                                      String id,
                                      StackProps props,
                                      boolean useNative) {
    super(scope, id, props);

    QuarkusApiLambda quarkusApiLambda = new QuarkusApiLambda(this,
      QuarkusApiLambda.FUNCTION_NAME,
      useNative);
    new QuarkusApiLambdaGateway(this,
      HTTP_GATEWAY,
      quarkusApiLambda.getFunction());
  }
}
