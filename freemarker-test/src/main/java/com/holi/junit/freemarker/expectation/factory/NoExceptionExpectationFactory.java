package com.holi.junit.freemarker.expectation.factory;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.expectation.ExpectationContext;
import com.holi.junit.freemarker.expectation.ExpectationFactory;
import com.holi.junit.freemarker.expectation.ExpectationSpecification;
import freemarker.template.TemplateException;
import java.io.IOException;

import static freemarker.template.utility.NullWriter.INSTANCE;

/**
 * Created by selonj on 16-8-31.
 */
class NoExceptionExpectationFactory implements ExpectationFactory {
  @Override public void describeTo(StringBuilder out) {
    out.append("<@test name=foo>");
  }

  @Override public boolean accept(ExpectationSpecification specification) {
    return !specification.hasExpectedValue();
  }

  @Override public Expectation create(final ExpectationContext context) {
    return new Expectation() {
      @Override public void checking() throws TemplateException, IOException {
        context.eval(INSTANCE);
      }
    };
  }
}
