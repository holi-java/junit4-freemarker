package com.holi.junit;

import java.io.File;
import java.io.IOException;
import java.io.Reader;

/**
 * Created by selonj on 16-8-29.
 */
public interface Script {
  String getName();

  File baseDir();

  <R> R open(Action<Reader, R> action) throws IOException;
}
