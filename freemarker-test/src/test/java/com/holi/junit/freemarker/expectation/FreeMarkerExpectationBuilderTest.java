package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.Expectation;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Writer;
import org.junit.Test;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.ASSERTION;
import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.EXCEPTION;
import static com.holi.junit.freemarker.expectation.Variables.actualValue;
import static com.holi.junit.freemarker.expectation.Variables.expectedValue;
import static com.holi.junit.freemarker.expectation.Variables.testName;
import static com.holi.junit.freemarker.expectation.Variables.with;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

/**
 * Created by selonj on 16-8-30.
 */
public class FreeMarkerExpectationBuilderTest {
  private static final TemplateDirectiveBody NO_BODY = null;
  private final FreeMarkerExpectationBuilder expectations = new FreeMarkerExpectationBuilder();

  @Test public void satisfiedIfActualValueEqualsToExpectedValue() throws Exception {
    expectations.create(ASSERTION, with(expectedValue("foo"), actualValue("foo")), NO_BODY).checking();
  }

  @Test public void throwsAssertionErrorIfActualValueNotEqualsToExpectedValue() throws Exception {
    Expectation expectation = expectations.create(ASSERTION, with(expectedValue("foo"), actualValue("bar")), NO_BODY);
    try {
      expectation.checking();
      fail("should failed");
    } catch (AssertionError expected) {
      assertThat(expected, hasMessage(containsString("Expected: \"foo\"")));
      assertThat(expected, hasMessage(containsString("but: was \"bar\"")));
    }
  }

  @Test public void satisfiedIfExpectedValueSatisfied() throws Exception {
    expectations.create(ASSERTION, with(expectedValue(true)), NO_BODY).checking();
  }

  @Test public void throwsClassCastExceptionIfExpectedValueNotABooleanExpressionWhenTestExpectedValueAsPredicate() throws Exception {
    try {
      expectations.create(ASSERTION, with(expectedValue("other type")), NO_BODY).checking();
      fail("should failed");
    } catch (ClassCastException expected) {
      assertTrue(true);
    }
  }

  @Test public void satisfiedIfExpectedValueEqualsToBodyAsString() throws Exception {
    expectations.create(ASSERTION, with(expectedValue("foo")), bodyWithString("foo")).checking();
  }

  @Test public void throwsClassCastExceptionIfExpectedValueNotAStringWhenMatchingExpectedValueEqualsToBodyAsString() throws Exception {
    Expectation expectation = expectations.create(ASSERTION, with(expectedValue(true)), bodyWithString("true"));

    try {
      expectation.checking();
      fail("should failed");
    } catch (ClassCastException expected) {
      assertTrue(true);
    }
  }

  @Test public void reportsHelpMessageWhenAssertBlockWithInvalidForm() throws Exception {
    try {
      expectations.create(ASSERTION, with(expectedValue("foo"), actualValue("foo")), bodyWithString("body"));
      fail("should failed");
    } catch (IllegalArgumentException expected) {
      assertThat(expected, hasMessage(containsString("<@assert expected=foo actual=bar/>")));
      assertThat(expected, hasMessage(containsString("<@assert expected=foo /> and foo must be a boolean value")));
      assertThat(expected, hasMessage(containsString("<@assert expected=foo>any template element</@assert> and foo must be a string scalar")));
    }
  }

  @Test public void satisfiedIfTestRanWithNoExceptionFails() throws Exception {
    expectations.create(EXCEPTION, with(testName("test")), bodyWithString("body")).checking();
  }

  @Test public void satisfiedIfTestFailsWithExpectedException() throws Exception {
    IllegalStateException exception = new IllegalStateException();
    Expectation expectation = expectations.create(EXCEPTION, with(expectedValue(exception.getClass().getName())), throwExceptionWhenEvalBody(exception));

    expectation.checking();
  }

  @Test public void satisfiedIfTestFailsWithSubtypeOfExpectedException() throws Exception {
    IllegalStateException exception = new IllegalStateException();
    Expectation expectation = expectations.create(EXCEPTION, with(expectedValue("java.lang.Exception")), throwExceptionWhenEvalBody(exception));

    expectation.checking();
  }

  @Test public void throwsAssertionErrorIfExpectedExceptionMismatched() throws Exception {
    Expectation expectation = expectations.create(EXCEPTION, with(expectedValue("java.lang.Exception")), bodyWithString("success"));

    try {
      expectation.checking();
      fail("should failed");
    } catch (AssertionError expected) {
      assertThat(expected, hasMessage(containsString("Expected: is an instance of java.lang.Exception")));
      assertThat(expected, hasMessage(containsString("but: null")));
    }
  }

  private TemplateDirectiveBody throwExceptionWhenEvalBody(final RuntimeException exception) {
    return new TemplateDirectiveBody() {
      @Override public void render(Writer out) throws TemplateException, IOException {
        throw exception;
      }
    };
  }

  private TemplateDirectiveBody bodyWithString(final String content) {
    return new TemplateDirectiveBody() {
      @Override public void render(Writer out) throws TemplateException, IOException {
        out.write(content);
      }
    };
  }

  private void fail(String message) {
    throw new IllegalStateException(message);
  }
}