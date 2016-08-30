package com.holi.junit.freemarker.internal.expectation.factory;

import com.holi.junit.freemarker.internal.blocks.Expectation;
import com.holi.junit.freemarker.internal.expectation.ExpectationContext;
import freemarker.template.TemplateException;
import java.io.IOException;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-31.
 */
class Equality implements Expectation {
  private final ExpectationContext context;

  public Equality(ExpectationContext context) {
    this.context = context;
  }

  @Override public void checking() throws TemplateException, IOException {
    assertThat(context.actualValue(), equalTo(context.expectedValue()));
  }
}
