package com.holi.junit.freemarker;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import java.io.IOException;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
public class AssertBlock implements TemplateDirectiveModel {
  private ExpectationBuilder expectations;
  private ThreadLocal<AssertBlock> stack = new ThreadLocal<>();
  private String name = "assert";

  public AssertBlock(ExpectationBuilder expectations) {
    this.expectations = expectations;
  }

  @Override public void execute(Environment env, Map params, TemplateModel[] loopVars, TemplateDirectiveBody body) throws TemplateException, IOException {
    push(env);
    try {
      expectations.create(params, body).checking();
    } finally {
      pop(env);
    }
  }

  private void push(Environment env) throws TemplateException {
    if (stack.get() != null) {
      throw new TemplateException("<@" + getName() + "> can't nested with another <@" + getName() + "> block!", env);
    }
    stack.set(this);
  }

  private void pop(Environment env) {
    stack.remove();
  }

  public String getName() {
    return name;
  }
}
