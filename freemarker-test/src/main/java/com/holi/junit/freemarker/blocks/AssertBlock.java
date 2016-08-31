package com.holi.junit.freemarker.blocks;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.ASSERTION;

/**
 * Created by selonj on 16-8-30.
 */
public class AssertBlock implements JUnitBlock, TemplateDirectiveModel {
  private ExpectationBuilder expectations;
  private BlockStack stack;

  public AssertBlock(ExpectationBuilder expectations, BlockStack stack) {
    this.expectations = expectations;
    this.stack = stack;
  }

  @Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    stack.push(this);
    try {
      expectations.create(ASSERTION, env, params, body).checking();
    } finally {
      stack.pop(this);
    }
  }

  public String getName() {
    return ASSERTION.blockName();
  }
}
