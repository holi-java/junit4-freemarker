package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import com.holi.junit.freemarker.blocks.JUnitBlock;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.util.Map;

/**
 * Created by selonj on 16-9-3.
 */
public class ExpectationStackBuilder implements ExpectationBuilder {
  private ExpectationBuilder expectations;
  private BlockStack stack;

  public ExpectationStackBuilder(ExpectationBuilder expectations, BlockStack stack) {
    this.expectations = expectations;
    this.stack = stack;
  }

  @Override public Expectation create(final JUnitBlock block, final Map params, final TemplateDirectiveBody body) throws TemplateException {
    return createExpectationThatCheckingAroundStack(block, expectations.create(block, params, body));
  }

  private Expectation createExpectationThatCheckingAroundStack(final JUnitBlock block, final Expectation expectation) {
    return new Expectation() {
      @Override public void checking() throws TemplateException, IOException {
        stack.push(block);
        try {
          expectation.checking();
        } finally {
          stack.pop(block);
        }
      }
    };
  }
}
