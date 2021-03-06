package com.holi.junit.freemarker.expectation.factory;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.expectation.ExpectationContext;
import com.holi.junit.freemarker.expectation.ExpectationFactory;
import com.holi.junit.freemarker.expectation.ExpectationSpecification;

/**
 * Created by selonj on 16-8-31.
 */
class EqualityExpectationFactory implements ExpectationFactory {
  @Override public void describeTo(StringBuilder out) {
    out.append("<@assert expected=foo actual=bar/>");
  }

  @Override public boolean accept(ExpectationSpecification specification) {
    return specification.hasExpectedValue() && specification.hasActualValue() && !specification.hasBody();
  }

  @Override public Expectation create(ExpectationContext context) {
    return new Equality(context);
  }
}
