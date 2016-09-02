package com.holi.junit.freemarker.expectation.factory;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.expectation.ExpectationContext;
import com.holi.junit.freemarker.expectation.ExpectationFactory;
import com.holi.junit.freemarker.expectation.ExpectationSpecification;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import freemarker.template.utility.NullWriter;
import java.io.IOException;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-31.
 */
class ExpectedExceptionExpectationFactory implements ExpectationFactory {
  @Override public void describeTo(StringBuilder out) {
    out.append("<@test name=foo expected=exception> and exception must a fully name of Exception type");
  }

  @Override public boolean accept(ExpectationSpecification specification) {
    return specification.hasExpectedValue();
  }

  @Override public Expectation create(final ExpectationContext context) {
    return new ExpectedExceptionExpectation(context);
  }

  private static class ExpectedExceptionExpectation implements Expectation {
    private final ExpectationContext context;

    public ExpectedExceptionExpectation(ExpectationContext context) {
      this.context = context;
    }

    @Override public void checking() throws TemplateException, IOException {
      assertThat(actualException(), is(instanceOf(expectedExceptionClass())));
    }

    private Throwable actualException() {
      try {
        context.eval(NullWriter.INSTANCE);
        return null;
      } catch (Throwable error) {
        return error;
      }
    }

    private Class expectedExceptionClass() throws IOException, TemplateModelException {
      Object value = context.expectedValue();
      if(value instanceof Class) return (Class) value;
      return loadClass((String) value);
    }

    private Class loadClass(String value) {
      try {
        return Class.forName(value);
      } catch (ClassNotFoundException e) {
        throw new IllegalArgumentException("Class not found: " + value, e);
      }
    }
  }
}
