package com.holi.junit.freemarker;

/**
 * Created by selonj on 16-8-29.
 */
public interface Action<T, R> {
  R call(T it);
}
