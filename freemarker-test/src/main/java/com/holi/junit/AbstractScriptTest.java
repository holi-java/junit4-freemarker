package com.holi.junit;

import org.junit.internal.AssumptionViolatedException;
import org.junit.internal.runners.model.EachTestNotifier;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.Statement;

/**
 * Created by selonj on 16-8-31.
 */
abstract public class AbstractScriptTest implements ScriptTest {
  private Script script;

  public AbstractScriptTest(Script script) {
    this.script = script;
  }

  protected final void runTest(Statement statement, Description description, RunNotifier notifier) {
    EachTestNotifier eachNotifier = new EachTestNotifier(notifier, description);
    eachNotifier.fireTestStarted();
    try {
      statement.evaluate();
    } catch (AssumptionViolatedException e) {
      eachNotifier.addFailedAssumption(e);
    } catch (Throwable e) {
      eachNotifier.addFailure(e);
    } finally {
      eachNotifier.fireTestFinished();
    }
  }

  @Override public Description getDescription() {
    return Description.createSuiteDescription(testName(script));
  }

  protected String testName(Script script) {
    return baseName(script);
  }

  private String baseName(Script script) {
    String name = script.getName();
    int extPos = name.lastIndexOf('.');
    if (extPos == -1) return name;
    return name.substring(0, extPos);
  }

  @Override
  public void run(RunNotifier notifier) {

  }
}
