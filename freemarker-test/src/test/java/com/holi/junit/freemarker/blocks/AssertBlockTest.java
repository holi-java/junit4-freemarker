package com.holi.junit.freemarker.blocks;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.utility.NullWriter;
import java.util.HashMap;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

/**
 * Created by selonj on 16-8-30.
 */
public class AssertBlockTest {
  private static final TemplateModel[] UNUSED_LOOPVARS = new TemplateModel[0];
  private final Map params = new HashMap();
  private final Environment env = errorEnvironment();

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final ExpectationBuilder expectationBuilder = context.mock(ExpectationBuilder.class);
  private final Expectation expectation = context.mock(Expectation.class);

  private final TemplateDirectiveBody body = context.mock(TemplateDirectiveBody.class);
  private final AssertBlock assertBlock = new AssertBlock(expectationBuilder);

  @Before public void allowingBuildExpectation() throws Throwable {
    context.checking(new Expectations() {{
      allowing(expectationBuilder).create(Expectation.ExpectationType.ASSERTION, params, body); will(returnValue(expectation));
    }});
  }

  @Test public void checkingCurrentExpectation() throws Throwable {
    context.checking(new Expectations() {{
      oneOf(expectation).checking();
    }});

    assertBlock.execute(env, params, UNUSED_LOOPVARS, body);
  }

  @Test public void translatesExceptionWhenCheckExpectationFails() throws Throwable {
    try {
      env.process();
      fail("should failed");
    } catch (TemplateException expected) {
      assertThat(expected.getBlamedExpressionString(), equalTo("assert"));
      assertThat(expected.getTemplateSourceName(),containsString("test.ftl"));
      assertThat(expected.getFTLInstructionStack(), containsString("\"test.ftl\" at line 1, column 1"));
    }
  }

  private Environment errorEnvironment() {
    try {
      Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
      configuration.setLogTemplateExceptions(false);
      return new Template("test.ftl", "<@assert expected=foo actual=bar/>", configuration).createProcessingEnvironment(null, NullWriter.INSTANCE);
    } catch (Exception ex) {
      throw new Error(ex);
    }
  }
}