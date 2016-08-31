package com.holi.junit.scanner;

import java.io.File;

/**
 * Created by selonj on 16-9-1.
 */
abstract public class ScriptMatchers {
  public static ScriptMatcher endsWith(final String suffix) {
    return match(new ScriptNameMatcher() {
      @Override public boolean matches(String name) {
        return name.endsWith(suffix);
      }
    });
  }

  public static ScriptMatcher startsWith(final String prefix) {
    return match(new ScriptNameMatcher() {
      @Override public boolean matches(String name) {
        return name.startsWith(prefix);
      }
    });
  }

  private static ScriptMatcher match(final ScriptNameMatcher matcher) {
    return new ScriptMatcher() {
      @Override public boolean matches(File script) {
        return matcher.matches(script.getName());
      }
    };
  }

  public static ScriptMatcher not(final ScriptMatcher matcher) {
    return new ScriptMatcher() {
      @Override public boolean matches(File script) {
        return !matcher.matches(script);
      }
    };
  }

  public static ScriptMatcher allOf(final ScriptMatcher... matchers) {
    return new ScriptMatcher() {
      @Override public boolean matches(File script) {
        for (ScriptMatcher matcher : matchers) if (!matcher.matches(script)) return false;
        return true;
      }
    };
  }

  public static ScriptMatcher anyOf(final ScriptMatcher... matchers) {
    return new ScriptMatcher() {
      @Override public boolean matches(File script) {
        for (ScriptMatcher matcher : matchers) if (matcher.matches(script)) return true;
        return false;
      }
    };
  }

  private interface ScriptNameMatcher {
    boolean matches(String name);
  }
}
