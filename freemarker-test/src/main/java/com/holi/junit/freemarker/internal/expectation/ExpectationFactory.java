package com.holi.junit.freemarker.internal.expectation;

import com.holi.junit.freemarker.Expectation;
import com.holi.junit.freemarker.ExpectationContext;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationFactory {
  void describeTo(StringBuilder out);

  boolean accept(ExpectationSpecification specification);

  Expectation create(ExpectationContext context);
}
