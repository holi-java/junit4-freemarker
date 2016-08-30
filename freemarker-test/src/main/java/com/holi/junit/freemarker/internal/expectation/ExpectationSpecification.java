package com.holi.junit.freemarker.internal.expectation;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationSpecification {
  boolean hasExpectedValue();

  boolean hasActualValue();

  boolean hasBody();
}
