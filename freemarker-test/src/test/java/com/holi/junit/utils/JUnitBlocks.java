package com.holi.junit.utils;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.JUnitBlock;

/**
 * Created by selonj on 16-9-1.
 */
public class JUnitBlocks {
  public static JUnitBlock blockNamed(final String name) {
    return new JUnitBlock() {
      @Override public String getName() {
        return name;
      }

      @Override public Expectation.ExpectationType getExpectationType() {
        throw new UnsupportedOperationException();
      }
    };
  }

  public static JUnitBlock blockAs(final Expectation.ExpectationType type) {
    return new JUnitBlock() {
      @Override public String getName() {
        return type.blockName();
      }

      @Override public Expectation.ExpectationType getExpectationType() {
        return type;
      }
    };
  }
}
