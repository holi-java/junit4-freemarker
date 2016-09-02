package com.holi.junit.freemarker.blocks;

/**
 * Created by selonj on 16-8-31.
 */
public interface TestCollector {
  void add(Test test) throws TestOutOfCompilationStageException;
}
