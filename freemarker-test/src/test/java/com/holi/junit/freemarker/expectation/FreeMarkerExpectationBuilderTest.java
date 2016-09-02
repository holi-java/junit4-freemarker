package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.utils.JUnitBlocks;
import com.holi.junit.utils.TemplateDirectiveBodys;
import freemarker.template.TemplateDirectiveBody;
import org.junit.Test;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.ASSERTION;
import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.EXCEPTION;
import static com.holi.junit.utils.JUnitBlocks.blockAs;
import static com.holi.junit.utils.Variables.actualValue;
import static com.holi.junit.utils.Variables.expectedValue;
import static com.holi.junit.utils.Variables.testName;
import static com.holi.junit.utils.Variables.with;
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
    expectations.create(blockAs(ASSERTION), with(expectedValue("foo"), actualValue("foo")), NO_BODY).checking();
  }

  @Test public void throwsAssertionErrorIfActualValueNotEqualsToExpectedValue() throws Exception {
    Expectation expectation = expectations.create(blockAs(ASSERTION), with(expectedValue("foo"), actualValue("bar")), NO_BODY);
    try {
      expectation.checking();
      fail("should failed");
    } catch (AssertionError expected) {
      assertThat(expected, hasMessage(containsString("Expected: \"foo\"")));
      assertThat(expected, hasMessage(containsString("but: was \"bar\"")));
    }
  }

  @Test public void satisfiedIfExpectedValueSatisfied() throws Exception {
    expectations.create(blockAs(ASSERTION), with(expectedValue(true)), NO_BODY).checking();
  }

  @Test public void throwsClassCastExceptionIfExpectedValueNotABooleanExpressionWhenTestExpectedValueAsPredicate() throws Exception {
    try {
      expectations.create(blockAs(ASSERTION), with(expectedValue("other type")), NO_BODY).checking();
      fail("should failed");
    } catch (ClassCastException expected) {
      assertTrue(true);
    }
  }

  @Test public void satisfiedIfExpectedValueEqualsToBodyAsString() throws Exception {
    expectations.create(blockAs(ASSERTION), with(expectedValue("foo")), TemplateDirectiveBodys.bodyWithString("foo")).checking();
  }

  @Test public void throwsClassCastExceptionIfExpectedValueNotAStringWhenMatchingExpectedValueEqualsToBodyAsString() throws Exception {
    Expectation expectation = expectations.create(blockAs(ASSERTION), with(expectedValue(true)), TemplateDirectiveBodys.bodyWithString("true"));

    try {
      expectation.checking();
      fail("should failed");
    } catch (ClassCastException expected) {
      assertTrue(true);
    }
  }

  @Test public void reportsHelpMessageWhenAssertBlockWithInvalidForm() throws Exception {
    try {
      expectations.create(blockAs(ASSERTION), with(expectedValue("foo"), actualValue("foo")), TemplateDirectiveBodys.bodyWithString("body"));
      fail("should failed");
    } catch (IllegalArgumentException expected) {
      assertThat(expected, hasMessage(containsString("<@assert expected=foo actual=bar/>")));
      assertThat(expected, hasMessage(containsString("<@assert expected=foo /> and foo must be a boolean value")));
      assertThat(expected, hasMessage(containsString("<@assert expected=foo>any template element</@assert> and foo must be a string scalar")));
    }
  }

  @Test public void satisfiedIfTestRanWithNoExceptionFails() throws Exception {
    expectations.create(blockAs(EXCEPTION), with(testName("test")), TemplateDirectiveBodys.bodyWithString("body")).checking();
  }

  @Test public void satisfiedIfTestFailsWithExpectedException() throws Exception {
    IllegalStateException exception = new IllegalStateException();
    Expectation expectation = expectations.create(blockAs(EXCEPTION), with(expectedValue(exception.getClass().getName())), TemplateDirectiveBodys.throwExceptionWhenEvalBody(exception));

    expectation.checking();
  }

  @Test public void satisfiedIfTestFailsWithSubtypeOfExpectedException() throws Exception {
    IllegalStateException exception = new IllegalStateException();
    Expectation expectation = expectations.create(blockAs(EXCEPTION), with(expectedValue("java.lang.Exception")), TemplateDirectiveBodys.throwExceptionWhenEvalBody(exception));

    expectation.checking();
  }

  @Test public void throwsAssertionErrorIfExpectedExceptionMismatched() throws Exception {
    Expectation expectation = expectations.create(blockAs(EXCEPTION), with(expectedValue("java.lang.Exception")), TemplateDirectiveBodys.bodyWithString("success"));

    try {
      expectation.checking();
      fail("should failed");
    } catch (AssertionError expected) {
      assertThat(expected, hasMessage(containsString("Expected: is an instance of java.lang.Exception")));
      assertThat(expected, hasMessage(containsString("but: null")));
    }
  }

  private void fail(String message) {
    throw new IllegalStateException(message);
  }
}