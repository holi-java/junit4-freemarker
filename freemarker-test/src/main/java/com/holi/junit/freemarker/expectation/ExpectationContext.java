package com.holi.junit.freemarker.expectation;

import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationContext {
  String PARAM_ACTUAL = "actual";
  String PARAM_EXPECTED = "expected";

  Object expectedValue() throws TemplateModelException, IOException;

  Object actualValue() throws TemplateException, IOException;

  void eval(Writer out) throws IOException, TemplateException;
}
