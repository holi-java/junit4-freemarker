package com.holi.junit.freemarker.expectation.factory;

import com.holi.junit.freemarker.expectation.ExpectationFactory;

/**
 * Created by selonj on 16-8-31.
 */
abstract public class ExpectationFactories {
  public static ExpectationFactory createBodyEqualityExpectationFactory() {
    return new BodyEqualityExpectationFactory();
  }

  public static ExpectationFactory createPredicationExpectationFactory() {
    return new PredicationExpectationFactory();
  }

  public static ExpectationFactory createEqualityExpectationFactory() {
    return new EqualityExpectationFactory();
  }

  public static ExpectationFactory createBodyWithNoExceptionExpectationFactory() {
    return new NoExceptionExpectationFactory();
  }

  public static ExpectationFactory createBodyWithExpectedExpectationFactory() {
    return new ExpectedExceptionExpectationFactory();
  }
}
