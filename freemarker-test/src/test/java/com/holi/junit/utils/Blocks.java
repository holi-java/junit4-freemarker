package com.holi.junit.utils;

import com.holi.junit.freemarker.blocks.JUnitBlock;

/**
 * Created by selonj on 16-9-1.
 */
public class Blocks {
  public static JUnitBlock blockNamed(final String name) {
    return new JUnitBlock() {
      @Override public String getName() {
        return name;
      }
    };
  }
}
