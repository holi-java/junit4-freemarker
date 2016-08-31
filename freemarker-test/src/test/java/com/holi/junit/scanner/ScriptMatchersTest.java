package com.holi.junit.scanner;

import org.junit.Test;

import static com.holi.junit.utils.Files.file;
import static org.junit.Assert.*;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptMatchersTest {
  @Test public void endsWith() throws Exception {
    ScriptMatcher scriptMatcher = ScriptMatchers.endsWith(".ftl");

    assertTrue(scriptMatcher.matches(file("test.ftl")));
    assertTrue(!scriptMatcher.matches(file("test.txt")));
  }

  @Test public void startsWith() throws Exception {
    ScriptMatcher scriptMatcher = ScriptMatchers.startsWith("_");

    assertTrue(scriptMatcher.matches(file("_test.ftl")));
    assertTrue(scriptMatcher.matches(file("src/test/_test.ftl")));
    assertTrue(!scriptMatcher.matches(file("test.ftl")));
  }

  @Test public void not() throws Exception {
    ScriptMatcher scriptMatcher = ScriptMatchers.not(ScriptMatchers.startsWith("_"));

    assertTrue(!scriptMatcher.matches(file("_test.ftl")));
    assertTrue(!scriptMatcher.matches(file("src/test/_test.ftl")));
    assertTrue(scriptMatcher.matches(file("test.ftl")));
  }

  @Test public void allOf() throws Exception {
    ScriptMatcher scriptMatcher = ScriptMatchers.allOf(ScriptMatchers.startsWith("_"), ScriptMatchers.endsWith(".ftl"));

    assertTrue(scriptMatcher.matches(file("_test.ftl")));
    assertTrue(scriptMatcher.matches(file("src/test/_test.ftl")));

    assertTrue(!scriptMatcher.matches(file("test.ftl")));
    assertTrue(!scriptMatcher.matches(file("_test.txt")));
  }

  @Test public void anyOf() throws Exception {
    ScriptMatcher scriptMatcher = ScriptMatchers.anyOf(ScriptMatchers.startsWith("_"), ScriptMatchers.endsWith(".ftl"));

    assertTrue(scriptMatcher.matches(file("_test.ftl")));
    assertTrue(scriptMatcher.matches(file("src/test/_test.ftl")));
    assertTrue(scriptMatcher.matches(file("test.ftl")));
    assertTrue(scriptMatcher.matches(file("_test.txt")));

    assertTrue(!scriptMatcher.matches(file("test.txt")));
  }
}