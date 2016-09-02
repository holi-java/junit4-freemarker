package com.holi.junit.freemarker.blocks;

import com.holi.junit.freemarker.blocks.Expectation.ExpectationType;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.Map;

/**
 * Created by selonj on 16-9-3.
 */
abstract public class ExpectationBlock implements JUnitBlock {
  private final ExpectationType type;
  protected ExpectationBuilder expectations;

  public ExpectationBlock(ExpectationType type, ExpectationBuilder expectations) {
    this.type = type;
    this.expectations = expectations;
  }

  public String getName() {
    return type.blockName();
  }

  public ExpectationType getExpectationType() {
    return type;
  }

  protected Expectation createExpectation(Map params, TemplateDirectiveBody body) throws TemplateException {
    return expectations.create(this, params, body);
  }
}
