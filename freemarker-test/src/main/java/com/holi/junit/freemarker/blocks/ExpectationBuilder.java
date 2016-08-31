package com.holi.junit.freemarker.blocks;

import com.holi.junit.freemarker.blocks.Expectation.ExpectationType;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationBuilder {
  Expectation create(ExpectationType type, Map params, TemplateDirectiveBody body) throws TemplateException;
}
