package com.holi.junit.utils;

import com.holi.junit.core.Action;
import com.holi.junit.core.Script;
import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by selonj on 16-8-31.
 */
public class StaticScript {
  public static Script createScript(final String baseDir, final String script, final String snippet) {
    return new Script() {
      public String getName() {
        return script;
      }

      @Override public File baseDir() {
        return new File(baseDir);
      }

      @Override public <R> R open(Action<Reader, R> action) throws IOException {
        return action.call(new StringReader(snippet));
      }
    };
  }
}
