package com.holi.junit.freemarker.expectation.context;

import com.holi.junit.freemarker.expectation.ExpectationContext;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.io.Writer;

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

  @Override public void eval(Writer out) throws IOException, TemplateException {
    context.eval(out);
  }
}
