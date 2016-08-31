package com.holi.junit.freemarker.blocks;

import com.holi.junit.freemarker.blocks.Expectation.ExpectationType;
import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
public interface ExpectationBuilder {
  //todo env
  Expectation create(ExpectationType type, Environment env, Map params, TemplateDirectiveBody body) throws TemplateException;
}
