package com.holi.junit.freemarker.expectation.context;

import com.holi.junit.freemarker.ExpectationContext;
import freemarker.template.TemplateModelException;
import java.io.IOException;

/**
 * Created by selonj on 16-8-30.
 */
class PredicationContext extends DelegatingExpectationContext {

  public PredicationContext(ExpectationContext context) {
    super(context);
  }

  @Override public Object expectedValue() {
    return true;
  }

  @Override public Object actualValue() throws TemplateModelException, IOException {
    return Boolean.class.cast(super.expectedValue());
  }
}
