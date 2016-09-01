package com.holi.junit.utils;

import com.holi.junit.Script;
import com.holi.junit.ScriptScanner;
import java.util.Arrays;
import java.util.List;
import org.junit.runners.model.TestClass;

/**
 * Created by selonj on 16-9-1.
 */
public class StaticScriptScanner {
  public static ScriptScanner scanAs(final Script... scripts) {
    return new ScriptScanner() {
      @Override public List<Script> scan(TestClass testClass) {
        return Arrays.asList(scripts);
      }
    };
  }
}
