package com.holi.junit.freemarker.blocks;

import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationBuilder {
  Expectation create(JUnitBlock block, Map params, TemplateDirectiveBody body) throws TemplateException;
}
