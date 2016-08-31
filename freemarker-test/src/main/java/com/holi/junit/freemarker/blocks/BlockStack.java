package com.holi.junit.freemarker.blocks;

import freemarker.template.TemplateException;

/**
 * Created by selonj on 16-9-1.
 */
public interface BlockStack {
  void push(JUnitBlock block) throws TemplateException;

  void pop(JUnitBlock block) throws TemplateException;
}
