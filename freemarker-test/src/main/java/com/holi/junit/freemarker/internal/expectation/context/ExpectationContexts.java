package com.holi.junit.freemarker.internal.expectation.context;

import com.holi.junit.freemarker.ExpectationContext;
import freemarker.template.TemplateDirectiveBody;
import java.util.Map;

/**
 * Created by selonj on 16-8-31.
 */
abstract public class ExpectationContexts {
  public static BodyEqualityContext createBodyEqualityContext(ExpectationContext context) {
    return new BodyEqualityContext(context);
  }

  public static EqualityContext createExpectationContext(Map params, TemplateDirectiveBody body) {
    return new EqualityContext(params, body);
  }

  public static PredicationContext createPredicationContext(ExpectationContext context) {
    return new PredicationContext(context);
  }
}
