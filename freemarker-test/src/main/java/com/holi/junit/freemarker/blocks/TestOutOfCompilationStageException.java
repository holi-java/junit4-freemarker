package com.holi.junit.freemarker.blocks;

/**
 * Created by selonj on 16-9-2.
 */
public class TestOutOfCompilationStageException extends RuntimeException {
  public final Test test;

  public TestOutOfCompilationStageException(Test test) {
    this.test = test;
  }
}
