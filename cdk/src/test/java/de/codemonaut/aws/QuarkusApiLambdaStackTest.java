package de.codemonaut.aws;

import de.codemonaut.aws.lambda.QuarkusApiLambdaStack;
import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import org.junit.jupiter.api.Test;

public class QuarkusApiLambdaStackTest {

  @Test
  public void testStack() {
    App app = new App();
    QuarkusApiLambdaStack quarkusApiLambdaStack = new QuarkusApiLambdaStack(app, "test", null, true);

    Template template = Template.fromStack(quarkusApiLambdaStack);

    // template.hasResourceProperties("AWS::SQS::Queue", new HashMap<String, Number>() {{
    //  put("VisibilityTimeout", 300);
    // }});
  }
}
