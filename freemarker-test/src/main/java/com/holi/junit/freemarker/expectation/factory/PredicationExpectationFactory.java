package com.holi.junit.freemarker.expectation.factory;

import com.holi.junit.freemarker.Expectation;
import com.holi.junit.freemarker.ExpectationContext;
import com.holi.junit.freemarker.expectation.ExpectationFactory;
import com.holi.junit.freemarker.expectation.ExpectationSpecification;
import com.holi.junit.freemarker.expectation.context.ExpectationContexts;

import static com.holi.junit.freemarker.expectation.context.ExpectationContexts.createPredicationContext;

/**
 * Created by selonj on 16-8-31.
 */
class PredicationExpectationFactory extends EqualityExpectationFactory {
  @Override public void describeTo(StringBuilder out) {
    out.append("<@assert expected=foo /> and foo must be a boolean value");
  }

  @Override public boolean accept(ExpectationSpecification specification) {
    return specification.hasExpectedValue() && !specification.hasActualValue() && !specification.hasBody();
  }

  @Override public Expectation create(ExpectationContext context) {
    return super.create(createPredicationContext(context));
  }
}
