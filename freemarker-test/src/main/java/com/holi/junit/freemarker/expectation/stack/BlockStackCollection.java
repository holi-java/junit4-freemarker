package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.expectation.BlockStack;
import freemarker.template.TemplateException;

/**
 * Created by selonj on 16-9-1.
 */
public class BlockStackCollection implements BlockStack {
  private final BlockStack[] blocks;

  public BlockStackCollection(BlockStack... blocks) {
    this.blocks = blocks;
  }

  @Override public void push(JUnitBlock block) throws TemplateException {
    for (BlockStack stack : blocks) stack.push(block);
  }

  @Override public void pop(JUnitBlock block) throws TemplateException {
    for (BlockStack stack : blocks) stack.pop(block);
  }
}
