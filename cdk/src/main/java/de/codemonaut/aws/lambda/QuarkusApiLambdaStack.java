package de.codemonaut.aws.lambda;

import software.amazon.awscdk.CfnOutput;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.lambda.FunctionUrl;
import software.amazon.awscdk.services.lambda.FunctionUrlAuthType;
import software.amazon.awscdk.services.lambda.FunctionUrlOptions;
import software.amazon.awscdk.services.lambda.IFunction;
import software.constructs.Construct;
import software.amazon.awscdk.Stack;

public class QuarkusApiLambdaStack extends Stack {

  public QuarkusApiLambdaStack(final Construct scope,
                               final String id,
                               final StackProps props,
                               final boolean snapStart) {
    super(scope, id, props);

    QuarkusApiLambda quarkusApiLambda = new QuarkusApiLambda(this,
      QuarkusApiLambda.FUNCTION_NAME,
      snapStart);
    IFunction lambdaFunction = quarkusApiLambda.getFunction();
    FunctionUrlOptions lambdaFunctionUrlOptions = FunctionUrlOptions.builder()
        .authType(FunctionUrlAuthType.NONE)
          .build();
    FunctionUrl lambdaFunctionUrl = lambdaFunction.addFunctionUrl(lambdaFunctionUrlOptions);

    CfnOutput.Builder.create(this, "FunctionUrlOutput")
      .value(lambdaFunctionUrl.getUrl())
      .build();
  }
}
