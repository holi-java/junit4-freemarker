package com.holi.junit.freemarker.blocks;

import org.jmock.Expectations;
import org.jmock.Sequence;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.holi.junit.utils.JUnitBlocks.blockNamed;

/**
 * Created by selonj on 16-9-1.
 */
public class BlockStackCollectionTest {
  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final BlockStack first = context.mock(BlockStack.class, "first");
  private final BlockStack last = context.mock(BlockStack.class, "last");
  private final Sequence order = context.sequence("action");
  private final BlockStackCollection stack = new BlockStackCollection(first, last);
  private JUnitBlock block = blockNamed("anything");

  @Test public void pushInSequence() throws Exception {
    context.checking(new Expectations() {{
      oneOf(first).push(block); inSequence(order);
      oneOf(last).push(block); inSequence(order);
    }});

    stack.push(block);
  }

  @Test public void popInSequence() throws Exception {
    context.checking(new Expectations() {{
      oneOf(first).pop(block); inSequence(order);
      oneOf(last).pop(block); inSequence(order);
    }});

    stack.pop(block);
  }
}