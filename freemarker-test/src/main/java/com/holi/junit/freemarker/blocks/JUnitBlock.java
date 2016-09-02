package com.holi.junit.freemarker.blocks;

import com.holi.junit.freemarker.blocks.Expectation.ExpectationType;
import freemarker.template.TemplateModel;

/**
 * Created by selonj on 16-8-31.
 */
public interface JUnitBlock extends TemplateModel {
  String getName();

  ExpectationType getExpectationType();
}
