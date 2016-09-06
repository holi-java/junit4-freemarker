package com.holi.junit.core;

import java.io.IOException;

/**
 * Created by selonj on 16-8-29.
 */
public interface Action<T, R> {
  R call(T it) throws IOException;
}
