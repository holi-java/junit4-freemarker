package com.holi.junit.freemarker.blocks;

import com.holi.junit.utils.Environments;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import java.util.HashMap;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

/**
 * Created by selonj on 16-8-30.
 */
public class AssertBlockTest {
  private static final TemplateModel[] UNUSED_LOOPVARS = new TemplateModel[0];
  private final Map params = new HashMap();
  private final Environment env = Environments.as("test.ftl", "");

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final ExpectationBuilder expectationBuilder = context.mock(ExpectationBuilder.class);
  private final Expectation expectation = context.mock(Expectation.class);
  private final TemplateDirectiveBody body = context.mock(TemplateDirectiveBody.class);
  private final AssertBlock assertBlock = new AssertBlock(expectationBuilder);

  @Before public void init() throws Throwable {
    context.checking(new Expectations() {{
      allowing(expectationBuilder).create(assertBlock, params, body); will(returnValue(expectation));
    }});
  }

  @Test public void checkingExpectationImmediately() throws Throwable {
    context.checking(new Expectations() {{
      oneOf(expectation).checking();
    }});

    assertBlock.execute(env, params, UNUSED_LOOPVARS, body);
  }
}