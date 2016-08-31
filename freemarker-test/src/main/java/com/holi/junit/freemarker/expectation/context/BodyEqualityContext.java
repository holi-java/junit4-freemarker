package com.holi.junit.freemarker.expectation.context;

import com.holi.junit.freemarker.expectation.ExpectationContext;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.io.IOException;
import java.io.StringWriter;

/**
 * Created by selonj on 16-8-30.
 */
class BodyEqualityContext extends DelegatingExpectationContext {

  public BodyEqualityContext(ExpectationContext context) {
    super(context);
  }

  @Override public Object expectedValue() throws TemplateModelException, IOException {
    return String.class.cast(super.expectedValue());
  }

  @Override public Object actualValue() throws TemplateException, IOException {
    return body();
  }

  private String body() throws IOException, TemplateException {
    StringWriter out = new StringWriter();
    eval(out);
    return out.toString();
  }
}
