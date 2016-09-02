package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.expectation.BlockStack;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by selonj on 16-9-1.
 */
public class TopBlockStack implements BlockStack {
  private final Environment env;
  private ThreadLocal<Set<JUnitBlock>> stackLocal = new ThreadLocal<Set<JUnitBlock>>() {
    @Override protected Set<JUnitBlock> initialValue() {
      return new HashSet<>();
    }
  };

  public TopBlockStack(Environment env) {
    this.env = env;
  }

  @Override public void push(JUnitBlock block) throws TemplateException {
    if (stack().contains(block)) fail(block);
    stack().add(block);
  }

  private Set<JUnitBlock> stack() {
    return stackLocal.get();
  }

  private void fail(JUnitBlock block) throws TemplateException {
    String directive = "<@" + block.getName() + ">";
    throw new TemplateException(directive + " block can't be nested with another " + directive + " block!", env);
  }

  @Override public void pop(JUnitBlock block) throws TemplateException {
    stack().remove(block);
  }
}
