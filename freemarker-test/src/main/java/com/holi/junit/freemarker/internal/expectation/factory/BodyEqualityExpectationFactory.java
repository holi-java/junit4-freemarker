package com.holi.junit.freemarker.internal.expectation.factory;

import com.holi.junit.freemarker.Expectation;
import com.holi.junit.freemarker.ExpectationContext;
import com.holi.junit.freemarker.internal.expectation.ExpectationSpecification;

import static com.holi.junit.freemarker.internal.expectation.context.ExpectationContexts.createBodyEqualityContext;

/**
 * Created by selonj on 16-8-31.
 */
class BodyEqualityExpectationFactory extends EqualityExpectationFactory {
  @Override public void describeTo(StringBuilder out) {
    out.append("<@assert expected=foo>any template element</@assert> and foo must be a string scalar");
  }

  @Override public boolean accept(ExpectationSpecification specification) {
    return specification.hasExpectedValue() && specification.hasBody() && !specification.hasActualValue();
  }

  @Override public Expectation create(ExpectationContext context) {
    return super.create(createBodyEqualityContext(context));
  }
}
