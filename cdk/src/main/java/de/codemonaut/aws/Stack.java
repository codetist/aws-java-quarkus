package de.codemonaut.aws;

import software.amazon.awscdk.StackProps;
import software.constructs.Construct;

public class Stack extends software.amazon.awscdk.Stack {

  public Stack(final Construct scope, final String id, final StackProps props) {
    super(scope, id, props);
  }
}
