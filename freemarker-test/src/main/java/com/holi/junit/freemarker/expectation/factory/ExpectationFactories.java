package com.holi.junit.freemarker.expectation.factory;

import com.holi.junit.freemarker.expectation.ExpectationFactory;

/**
 * Created by selonj on 16-8-31.
 */
public class ExpectationFactories {
  public static ExpectationFactory createBodyEqualityExpectationFactory() {
    return new BodyEqualityExpectationFactory();
  }

  public static ExpectationFactory createPredicationExpectationFactory() {
    return new PredicationExpectationFactory();
  }

  public static ExpectationFactory createEqualityExpectationFactory() {
    return new EqualityExpectationFactory();
  }
}
