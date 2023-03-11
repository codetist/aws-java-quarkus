package de.codemonaut.aws;

import software.amazon.awscdk.App;
import software.amazon.awscdk.assertions.Template;

import java.io.IOException;

import org.junit.jupiter.api.Test;

public class StackTest {

  @Test
  public void testStack() throws IOException {
    App app = new App();
    Stack stack = new Stack(app, "test", null);

    Template template = Template.fromStack(stack);

    // template.hasResourceProperties("AWS::SQS::Queue", new HashMap<String, Number>() {{
    //  put("VisibilityTimeout", 300);
    // }});
  }
}
