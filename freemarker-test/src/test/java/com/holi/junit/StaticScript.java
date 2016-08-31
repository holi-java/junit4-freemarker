package com.holi.junit;

import com.holi.junit.Action;
import com.holi.junit.Script;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

/**
 * Created by selonj on 16-8-31.
 */
public class StaticScript {
  public static Script createScript(final String script, final String snippet) {
    return new Script() {
      public String getName() {
        return script;
      }

      @Override public <R> R open(Action<Reader, R> action) throws IOException {
        return action.call(new StringReader(snippet));
      }
    };
  }
}
