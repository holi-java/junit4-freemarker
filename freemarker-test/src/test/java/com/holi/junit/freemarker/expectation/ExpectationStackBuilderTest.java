package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.ExpectationBlock;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.utils.JUnitBlocks;
import freemarker.template.TemplateDirectiveBody;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.EXCEPTION;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * Created by selonj on 16-9-3.
 */
public class ExpectationStackBuilderTest {
  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final Sequence execution = context.sequence("execution");
  private final Map params = new HashMap();
  private final TemplateDirectiveBody body = context.mock(TemplateDirectiveBody.class);
  private final BlockStack stack = context.mock(BlockStack.class);
  private final ExpectationBlock testBlock = JUnitBlocks.blockAs(EXCEPTION);
  private final Expectation expectation = context.mock(Expectation.class);
  private final ExpectationBuilder original = context.mock(ExpectationBuilder.class);
  private final ExpectationStackBuilder builder = new ExpectationStackBuilder(original, stack);

  @Test public void createExpectationImmediately() throws Exception {
    context.checking(new Expectations() {{
      atLeast(1).of(original).create(testBlock, params, body);
    }});

    builder.create(testBlock, params, body);
  }

  @Test public void checkingExpectationAroundStack() throws Exception {
    context.checking(new Expectations() {{
      allowing(original).create(testBlock, params, body); will(returnValue(expectation));
    }});

    context.checking(new Expectations() {{
      oneOf(stack).push(testBlock); inSequence(execution);
      oneOf(expectation).checking(); inSequence(execution);
      oneOf(stack).pop(testBlock); inSequence(execution);
    }});

    builder.create(testBlock, params, body).checking();
  }

  @Test public void popBlockFromStackWhenExpectationFails() throws Exception {
    context.checking(new Expectations() {{
      allowing(original).create(testBlock, params, body); will(returnValue(expectation));
    }});

    context.checking(new Expectations() {{
      allowing(stack).push(testBlock);
      allowing(expectation).checking(); will(throwException(new IOException()));
      oneOf(stack).pop(testBlock);
    }});

    try {
      builder.create(testBlock, params, body).checking();
      fail("should failed");
    } catch (IOException expected) {
      assertTrue(true);
    }
  }
}