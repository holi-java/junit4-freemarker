package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.Expectation;
import com.holi.junit.freemarker.ExpectationBuilder;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.utility.NullWriter;
import java.util.Collections;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.holi.junit.freemarker.matchers.ThrowableMatchers.hasStackTrace;
import static org.hamcrest.Matchers.containsString;
import static org.junit.Assert.*;

/**
 * Created by selonj on 16-8-30.
 */
public class InstructionStackExpectationBuilderTest {
  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final Map params = Collections.singletonMap("expected", "foo");
  private final TemplateDirectiveBody body = context.mock(TemplateDirectiveBody.class);
  private final Expectation actualExpectation = context.mock(Expectation.class);
  private final ExpectationBuilder expectationBuilder = context.mock(ExpectationBuilder.class);
  private final InstructionStackExpectationBuilder expectations = new InstructionStackExpectationBuilder(expectationBuilder, environmentStub());

  @Test public void dumpStackTraceWithInstructionStack() throws Exception {
    context.checking(new Expectations() {{
      allowing(expectationBuilder).create(params, body); will(returnValue(actualExpectation));
      oneOf(actualExpectation).checking(); will(throwException(new AssertionError("error")));
    }});

    Expectation expectation = expectations.create(params, body);

    try {
      expectation.checking();
      shouldFailed();
    } catch (AssertionError expected) {
      assertThat(expected, hasStackTrace(containsString("error")));
      assertThat(expected, hasStackTrace(containsString("stack")));
    }
  }

  private void shouldFailed() {
    throw new IllegalStateException("should failed");
  }

  private Environment environmentStub() {
    Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
    configuration.setLogTemplateExceptions(false);
    try {
      return new Template("test.ftl", "<@blockMissing/>", configuration).createProcessingEnvironment(null, NullWriter.INSTANCE);
    } catch (Exception error) {
      throw new Error(error);
    }
  }
}