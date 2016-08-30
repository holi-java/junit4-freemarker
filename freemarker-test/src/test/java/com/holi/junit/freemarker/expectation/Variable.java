package com.holi.junit.freemarker.expectation;

import freemarker.template.TemplateModel;

/**
 * Created by selonj on 16-8-30.
 */
public class Variable {
  public final String name;

  public final TemplateModel value;

  public Variable(String name, TemplateModel value) {
    this.name = name;
    this.value = value;
  }

  public static Variable valueOf(String name, TemplateModel model) {
    return new Variable(name, model);
  }
}
