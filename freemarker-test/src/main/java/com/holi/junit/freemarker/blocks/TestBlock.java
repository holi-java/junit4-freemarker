package com.holi.junit.freemarker.blocks;

import com.holi.junit.Test;
import com.holi.junit.freemarker.TestOutOfCompilationStage;
import com.holi.junit.freemarker.expectation.InstructionStackExpectationBuilder;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.utility.NullWriter;
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
    return "test";
  }

  @Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    try {
      collector.add(createTest(params, body));
    } catch (TestOutOfCompilationStage stage) {
      throw new TemplateException("<@" + getName() + "> block can't be nested in another <@" + getName() + "> block.", env);
    }
  }

  private Test createTest(final Map params, final TemplateDirectiveBody body) {
    return new Test() {
      @Override public String getName() {
        return testName(params);
      }

      @Override public void run() throws Throwable {
        expectations.create(EXCEPTION, params, body).checking();
      }
    };
  }

  private String testName(Map params) {
    Object name = params.get("name");
    if (name == null) return "";
    return name.toString();
  }
}
