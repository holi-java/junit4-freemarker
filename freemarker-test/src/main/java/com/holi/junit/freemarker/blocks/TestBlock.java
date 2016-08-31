package com.holi.junit.freemarker.blocks;

import com.holi.junit.Test;
import com.holi.junit.freemarker.TestOutOfCompilationStage;
import freemarker.core.Environment;
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
    stack.push(this);
    try {
      collector.add(createTest(env, params, body));
    } catch (TestOutOfCompilationStage stage) {
      throw new TemplateException("<@" + getName() + "> block can't be nested in another <@" + getName() + "> block.", env);
    }
  }

  private Test createTest(final Environment env, final Map params, final TemplateDirectiveBody body) {
    final TestBlock self = this;
    return new Test() {
      @Override public String getName() {
        return testName(params);
      }

      @Override public void run() throws Throwable {
        try {
          expectations.create(EXCEPTION, params, body).checking();
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
