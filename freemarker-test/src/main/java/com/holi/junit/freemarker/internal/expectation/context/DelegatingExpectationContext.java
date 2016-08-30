package com.holi.junit.freemarker.internal.expectation.context;

import com.holi.junit.freemarker.internal.expectation.ExpectationContext;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.io.IOException;

/**
 * Created by selonj on 16-8-31.
 */
class DelegatingExpectationContext implements ExpectationContext {
  private ExpectationContext context;

  public DelegatingExpectationContext(ExpectationContext context) {
    this.context = context;
  }

  @Override public Object expectedValue() throws TemplateModelException, IOException {
    return context.expectedValue();
  }

  @Override public Object actualValue() throws TemplateException, IOException {
    return context.actualValue();
  }

  @Override public String body() throws IOException, TemplateException {
    return context.body();
  }
}
