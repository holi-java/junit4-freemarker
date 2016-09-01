package com.holi.junit;

import java.util.List;
import org.junit.runners.model.TestClass;

/**
 * Created by selonj on 16-9-1.
 */
public interface ScriptScanner {
  List<Script> scan(TestClass testClass) throws Throwable;
}
