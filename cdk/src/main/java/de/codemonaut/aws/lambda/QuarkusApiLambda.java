package de.codemonaut.aws.lambda;

import software.amazon.awscdk.Duration;
import software.amazon.awscdk.RemovalPolicy;
import software.amazon.awscdk.services.lambda.*;
import software.amazon.awscdk.services.lambda.Runtime;
import software.amazon.awscdk.services.logs.LogGroup;
import software.amazon.awscdk.services.logs.LogGroupProps;
import software.amazon.awscdk.services.logs.RetentionDays;
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
  private final int memory = 128;
  private final int timeout = 10;

  private IFunction function;

  public QuarkusApiLambda(Construct scope,
                          String functionName,
                          boolean useNative) {
    super(scope, "quarkusApiLambda");

    // 1. Create custom loggroup, otherwise AWS creates its own with default settings
    createLogGroup(functionName);

    // 2. Create function after loggroup has been created
    this.function = createFunction(functionName, useNative, memory, timeout);
    if (!useNative) {
      Version version = setupSnapStart();
      this.function = createSnapStartAlias(version);
    }
  }

  private IFunction createFunction(String functionName,
                                   boolean useNative,
                                   int memory,
                                   int timeout) {

    // SnapStart currently supports JAVA_11 on X86_x64 only
    // https://docs.aws.amazon.com/de_de/lambda/latest/dg/snapstart.html
    Runtime runtime = useNative?Runtime.PROVIDED_AL2:Runtime.JAVA_11;
    Architecture architecture = Architecture.X86_64;

    return Function.Builder.create(this, functionName)
      .runtime(runtime)
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

  private Alias createSnapStartAlias(Version version) {
    return Alias.Builder.create(this, "SnapStartAlias")
      .aliasName("snapStart")
      .description("Alias required for SnapStart")
      .version(version)
      .build();
  }

  // Create loggroup which gets deleted on stack destroy
  // and uses reduced log retention
  private void createLogGroup(String functionName) {
    LogGroupProps logGroupProps = LogGroupProps.builder()
      .logGroupName("/aws/lambda/" + functionName)
      .retention(RetentionDays.ONE_DAY)
      .removalPolicy(RemovalPolicy.DESTROY)
      .build();

    new LogGroup(this, "LambdaLogGroup", logGroupProps);
  }

  public IFunction getFunction() {
    return this.function;
  }
}
