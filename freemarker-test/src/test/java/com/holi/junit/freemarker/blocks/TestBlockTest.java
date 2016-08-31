package com.holi.junit.freemarker.blocks;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.holi.junit.freemarker.expectation.Variables.testName;
import static com.holi.junit.freemarker.expectation.Variables.testNameMissing;
import static com.holi.junit.freemarker.expectation.Variables.with;
import static com.holi.junit.freemarker.matchers.TestMatchers.testThatIs;

/**
 * Created by selonj on 16-8-31.
 */
public class TestBlockTest {
  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final ExpectationBuilder expectations = context.mock(ExpectationBuilder.class);
  private final TestCollector testCollector = context.mock(TestCollector.class);
  private static final Environment UNUSED_ENV = null;
  private static final TemplateModel[] UNUSED_LOOP_VARS = null;
  private static final TemplateDirectiveBody UNUSED_BODY = null;
  private final TestBlock testBlock = new TestBlock(testCollector, expectations);

  @Test public void addsTestWhenTestBlockInterpolated() throws Exception {
    context.checking(new Expectations() {{
      oneOf(testCollector).add(with(testThatIs("test something")));
    }});

    testBlock.execute(UNUSED_ENV, with(testName("test something")), UNUSED_LOOP_VARS, UNUSED_BODY);
  }

  @Test public void allowingAddTestWithoutName() throws Exception {
    context.checking(new Expectations() {{
      oneOf(testCollector).add(with(testThatIs("")));
    }});

    testBlock.execute(UNUSED_ENV, with(testNameMissing()), UNUSED_LOOP_VARS, UNUSED_BODY);
  }
}