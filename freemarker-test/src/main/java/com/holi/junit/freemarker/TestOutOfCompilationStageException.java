package com.holi.junit.freemarker;

import com.holi.junit.Test;

/**
 * Created by selonj on 16-9-2.
 */
public class TestOutOfCompilationStageException extends RuntimeException {
  public final Test test;

  public TestOutOfCompilationStageException(Test test) {
    this.test = test;
  }
}
