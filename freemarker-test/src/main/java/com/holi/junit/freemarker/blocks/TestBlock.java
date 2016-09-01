package com.holi.junit.freemarker.blocks;

import com.holi.junit.Test;
import com.holi.junit.freemarker.TestOutOfCompilationStageException;
import freemarker.core.Environment;
import freemarker.template.Template;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.EXCEPTION;

/**
 * Created by selonj on 16-8-31.
 */
public class TestBlock implements JUnitBlock, TemplateDirectiveModel {
  private TestCollector collector;
  private ExpectationBuilder expectations;
  private BlockStack stack;

  public TestBlock(TestCollector collector, ExpectationBuilder expectations, BlockStack stack) {
    this.collector = collector;
    this.expectations = expectations;
    this.stack = stack;
  }

  public String getName() {
    return EXCEPTION.blockName();
  }

  @Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    try {
      collector.add(createTest(params, body));
    } catch (TestOutOfCompilationStageException ex) {
      String directive = getName();
      throw new TemplateException(directive + " block can't be nested in another " + directive + " block!", env);
    }
  }

  private Test createTest(final Map params, final TemplateDirectiveBody body) throws TemplateException {
    final TestBlock self = this;
    return new Test() {
      private Expectation expectation = expectations.create(EXCEPTION, params, body);

      @Override public String getName() {
        return testName(params);
      }

      @Override public void run() throws Throwable {
        stack.push(self);
        try {
          expectation.checking();
        } finally {
          stack.pop(self);
        }
      }
    };
  }

  private String testName(Map params) {
    Object name = params.get("name");
    if (name == null) return "";
    return name.toString();
  }
}
