package com.holi.junit.utils;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.ExpectationBlock;
import com.holi.junit.freemarker.blocks.JUnitBlock;

/**
 * Created by selonj on 16-9-1.
 */
public class JUnitBlocks {
  public static JUnitBlock blockAs(final String name) {
    return new JUnitBlock() {
      @Override public String getName() {
        return name;
      }
    };
  }

  public static ExpectationBlock blockAs(final Expectation.ExpectationType type) {
    return new ExpectationBlock(type, null) {
    };
  }
}
