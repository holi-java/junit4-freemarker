package com.holi.junit.freemarker.internal.expectation;

import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.io.IOException;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationContext {
  String PARAM_ACTUAL = "actual";
  String PARAM_EXPECTED = "expected";

  Object expectedValue() throws TemplateModelException, IOException;

  Object actualValue() throws TemplateException, IOException;

  String body() throws IOException, TemplateException;
}
