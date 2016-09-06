package com.holi.junit.scanner;

import java.io.File;

/**
 * Created by selonj on 16-9-1.
 */
public interface ScriptMatcher {
  boolean matches(File file);
}
