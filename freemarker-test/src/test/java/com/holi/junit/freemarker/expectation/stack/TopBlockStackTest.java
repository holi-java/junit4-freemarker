package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.expectation.stack.TopBlockStack;
import com.holi.junit.utils.Environments;
import com.holi.junit.utils.JUnitBlocks;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

/**
 * Created by selonj on 16-9-1.
 */
public class TopBlockStackTest {
  private final Environment env = Environments.as("test.ftl", "valid");
  private JUnitBlock testBlock = JUnitBlocks.blockNamed("test");
  private JUnitBlock assertBlock = JUnitBlocks.blockNamed("test");

  private TopBlockStack stack = new TopBlockStack(env);

  @Test public void throwsTemplateExceptionIfBlockWasInStackWhenPushBlockAgain() throws Exception {
    stack.push(testBlock);

    try {
      stack.push(testBlock);
      fail("should failed");
    } catch (TemplateException expected) {
      assertThat(expected, hasMessage(equalTo("<@test> block can't be nested with another <@test> block!")));
      assertThat(expected.getEnvironment(), is(sameInstance(env)));
    }
  }

  @Test public void diffBlocksCanPushedAtTheSameTime() throws Exception {
    stack.push(testBlock);
    stack.push(assertBlock);
  }

  @Test public void blockCanPushedAgainAfterBlockPoppedFromTheStack() throws Exception {
    stack.push(testBlock);
    stack.pop(testBlock);

    stack.push(testBlock);
  }

  @Test public void blockCannotBePushedAgainWhenAnotherBlockPoppedFromTheStack() throws Exception {
    stack.push(testBlock);
    stack.push(assertBlock);

    stack.pop(assertBlock);

    try {
      stack.push(testBlock);
      fail("should failed");
    } catch (TemplateException expected) {
      assertTrue(true);
    }
  }
}