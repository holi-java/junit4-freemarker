package com.holi.junit.utils;

import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.Writer;

/**
 * Created by selonj on 16-9-3.
 */
public class TemplateDirectiveBodys {
  public static TemplateDirectiveBody throwExceptionWhenEvalBody(final RuntimeException exception) {
    return new TemplateDirectiveBody() {
      @Override public void render(Writer out) throws TemplateException, IOException {
        throw exception;
      }
    };
  }

  public static TemplateDirectiveBody bodyWithString(final String content) {
    return new TemplateDirectiveBody() {
      @Override public void render(Writer out) throws TemplateException, IOException {
        out.write(content);
      }
    };
  }
}
