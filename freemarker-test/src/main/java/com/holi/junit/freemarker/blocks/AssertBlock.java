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

  public AssertBlock(ExpectationBuilder expectations) {
    this.expectations = expectations;
  }

  @Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    expectations.create(this, params, body).checking();
  }

  public String getName() {
    return ASSERTION.blockName();
  }

  @Override public Expectation.ExpectationType getExpectationType() {
    return ASSERTION;
  }
}
