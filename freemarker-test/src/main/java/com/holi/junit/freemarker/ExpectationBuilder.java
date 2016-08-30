package com.holi.junit.freemarker;

import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationBuilder {
  Expectation create(Map params, TemplateDirectiveBody body) throws TemplateException;
}
