package com.holi.junit.freemarker.internal.blocks;

import freemarker.template.TemplateException;
import java.io.IOException;

/**
 * Created by selonj on 16-8-30.
 */
public interface Expectation {
  void checking() throws TemplateException, IOException;
}
