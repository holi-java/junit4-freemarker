package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.utils.Environments;
import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.Collections;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.ASSERTION;
import static com.holi.junit.freemarker.matchers.ThrowableMatchers.hasStackTrace;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.*;

/**
 * Created by selonj on 16-8-30.
 */
public class InstructionStackExpectationBuilderTest {
  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final Map params = Collections.singletonMap("expected", "foo");
  private final JUnitBlock block = context.mock(JUnitBlock.class);
  private final TemplateDirectiveBody body = context.mock(TemplateDirectiveBody.class);
  private final Expectation actualExpectation = context.mock(Expectation.class);
  private final ExpectationBuilder expectationBuilder = context.mock(ExpectationBuilder.class);
  private final Environment env = Environments.as("test.ftl", "<@blockMissing/>");
  private final InstructionStackExpectationBuilder expectations = new InstructionStackExpectationBuilder(expectationBuilder, env);

  @Test public void dumpStackTraceWithInstructionStackWhenExpectationFails() throws Exception {
    context.checking(new Expectations() {{
      allowing(expectationBuilder).create(block, params, body); will(returnValue(actualExpectation));
      oneOf(actualExpectation).checking(); will(throwException(new AssertionError("error")));
    }});

    Expectation expectation = expectations.create(block, params, body);

    try {
      expectation.checking();
      shouldFailed();
    } catch (AssertionError expected) {
      assertThat(expected, hasStackTrace(containsString("error")));
      assertThat(expected, hasStackTrace(containsString("stack")));
    }
  }

  @Test public void throwsExceptionDirectlyIfCheckingFailsWithTemplateException() throws Exception {
    final TemplateException error = new TemplateException("error", env);
    context.checking(new Expectations() {{

      allowing(expectationBuilder).create(block, params, body); will(returnValue(actualExpectation));
      oneOf(actualExpectation).checking(); will(throwException(error));
    }});

    Expectation expectation = expectations.create(block, params, body);

    try {
      expectation.checking();
      shouldFailed();
    } catch (TemplateException actual) {
      assertThat(actual, is(sameInstance(error)));
    }
  }

  private void shouldFailed() {
    throw new IllegalStateException("should failed");
  }
}