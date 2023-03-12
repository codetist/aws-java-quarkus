package de.codemonaut.aws.lambda;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;
import software.constructs.IConstruct;

import java.util.Map;

public class QuarkusApiLambda extends Construct {

  public static final String FUNCTION_NAME = "quarkusApiLambda";
  private final static Map<String, String> configuration = Map.of(
    "message", "Quarkus API application as AWS Lambda",
    // https://quarkus.io/guides/amazon-snapstart
    "JAVA_TOOL_OPTIONS", "-XX:+TieredCompilation -XX:TieredStopAtLevel=1"
  );

  private final static String lambdaHandler = "io.quarkus.amazon.lambda.runtime.QuarkusStreamHandler::handleRequest";
  private final int memory = 256;
  private final int timeout = 10;

  private IFunction function;

  public QuarkusApiLambda(Construct scope,
                          String functionName,
                          boolean snapStart) {
    super(scope, "quarkusApiLambda");
    this.function = createFunction(functionName, snapStart, memory, timeout);
    if (snapStart) {
      Version version = setupSnapStart();
      this.function = createAlias(version);
    }
  }

  private IFunction createFunction(String functionName,
                                   boolean snapStart,
                                   int memory,
                                   int timeout) {
    Architecture architecture = snapStart?Architecture.X86_64:Architecture.ARM_64;
    return Function.Builder.create(this, functionName)
      .runtime(Runtime.JAVA_11)
      .architecture(architecture)
      .code(Code.fromAsset("../quarkusapilambda/target/function.zip"))
      .handler(QuarkusApiLambda.lambdaHandler)
      .memorySize(memory)
      .functionName(functionName)
      .environment(configuration)
      .timeout(Duration.seconds(timeout))
      .build();
  }

  private Version setupSnapStart() {
    IConstruct defaultChild = this.function.getNode().getDefaultChild();
    if (defaultChild instanceof CfnFunction cfnFunction) {
      cfnFunction.addPropertyOverride("SnapStart", Map.of("ApplyOn", "PublishedVersions"));
    }
    String uniqueLogicalId = "SnapStartVersion" + System.currentTimeMillis();

    return Version.Builder.create(this, uniqueLogicalId)
      .lambda(this.function)
      .description("SnapStart")
      .build();
  }

  private Alias createAlias(Version version) {
    return Alias.Builder.create(this, "SnapStartAlias")
      .aliasName("snapStart")
      .description("Alias required for SnapStart")
      .version(version)
      .build();
  }

  public IFunction getFunction() {
    return this.function;
  }
}
