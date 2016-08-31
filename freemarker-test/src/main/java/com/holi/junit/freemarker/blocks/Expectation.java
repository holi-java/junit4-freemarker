package com.holi.junit.freemarker.blocks;

import freemarker.template.TemplateException;
import java.io.IOException;

/**
 * Created by selonj on 16-8-30.
 */
public interface Expectation {
  void checking() throws TemplateException, IOException;

  enum ExpectationType {
    EXCEPTION("test"), ASSERTION("assert");

    private final String blockName;

    ExpectationType(String blockName) {
      this.blockName = blockName;
    }

    public String blockName() {
      return blockName;
    }
  }
}
