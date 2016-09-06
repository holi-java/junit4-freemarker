package com.holi.junit.core;

import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;

/**
 * Created by selonj on 16-8-31.
 */
public interface ScriptTest {
  Description getDescription();

  void run(RunNotifier notifier);
}
