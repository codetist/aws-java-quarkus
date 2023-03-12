package de.codemonaut.aws.apigateway;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.services.apigateway.LambdaRestApi;
import software.amazon.awscdk.services.apigatewayv2.alpha.HttpApi;
import software.amazon.awscdk.services.apigatewayv2.integrations.alpha.HttpLambdaIntegration;
import software.amazon.awscdk.services.lambda.IFunction;
import software.constructs.Construct;

public class QuarkusApiLambdaGateway extends Construct {

  public QuarkusApiLambdaGateway(Construct scope,
                                 ApiGatewayTypeEnum apiGatewayType,
                                 IFunction function) {
    super(scope, "APIGateway");

    switch (apiGatewayType) {
      case HTTP_GATEWAY -> integrateWithHttpApiGateway(function);
      case REST_GATEWAY -> integrateWithRestApiGateway(function);
      default -> throw new IllegalArgumentException("ApiGatewayType not implemented");
    }
  }

  private void integrateWithRestApiGateway(IFunction function) {
    LambdaRestApi restApiGateway = LambdaRestApi.Builder.create(this, "RestApiGatway")
      .handler(function)
      .build();
    CfnOutput.Builder.create(this, "RestApiGatewayUrlOutput")
      .value(restApiGateway.getUrl())
      .build();
  }

  private void integrateWithHttpApiGateway(IFunction function) {
    HttpLambdaIntegration httpApiGatewayIntegration = HttpLambdaIntegration.Builder.create("HttpApiGatewayIntegration", function)
      .build();
    HttpApi httpApiGateway = HttpApi.Builder.create(this, "HttpApiGateway")
      .defaultIntegration(httpApiGatewayIntegration)
      .build();

    String url = httpApiGateway.getUrl();
    CfnOutput.Builder.create(this, "HttpApiGatewayUrlOutput")
      .value(url)
      .build();
  }
}
