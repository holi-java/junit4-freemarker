package com.holi.junit.freemarker.blocks;

import com.holi.junit.Test;
import com.holi.junit.freemarker.TestOutOfCompilationStageException;
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

  public TestBlock(TestCollector collector, ExpectationBuilder expectations) {
    this.collector = collector;
    this.expectations = expectations;
  }

  public String getName() {
    return EXCEPTION.blockName();
  }

  @Override public Expectation.ExpectationType getExpectationType() {
    return EXCEPTION;
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
    final Expectation expectation = expectations.create(self, params, body);
    return new Test() {

      @Override public String getName() {
        return testName(params);
      }

      @Override public void run() throws Throwable {
        expectation.checking();
      }
    };
  }

  private String testName(Map params) {
    Object name = params.get("name");
    if (name == null) return "";
    return name.toString();
  }
}
