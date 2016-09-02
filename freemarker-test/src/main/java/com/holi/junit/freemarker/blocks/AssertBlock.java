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
public class AssertBlock extends ExpectationBlock implements JUnitBlock, TemplateDirectiveModel {

  public AssertBlock(ExpectationBuilder expectations) {
    super(ASSERTION, expectations);
  }

  @Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    createExpectation(params, body).checking();
  }
}
