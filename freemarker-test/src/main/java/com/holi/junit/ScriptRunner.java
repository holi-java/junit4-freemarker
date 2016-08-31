package com.holi.junit;

import com.holi.junit.freemarker.FreeMarkerScriptTestCompiler;
import java.util.ArrayList;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;

import static org.junit.runner.Description.createSuiteDescription;

/**
 * Created by selonj on 16-8-30.
 */
public class ScriptRunner extends ParentRunner<ScriptTest> {
  private List<ScriptTest> tests;
  private ScriptTestCompiler compiler = new SafeScriptTestCompiler(new FreeMarkerScriptTestCompiler());

  public ScriptRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  public ScriptRunner(Class<?> testClass, ScriptScanner scanner) throws Exception {
    super(testClass);
    this.tests = compile(scanner.scan(getTestClass()));
  }

  private List<ScriptTest> compile(List<Script> scripts) throws Exception {
    List<ScriptTest> tests = new ArrayList<>();
    for (Script script : scripts) tests.add(compiler.compile(script));
    return tests;
  }

  @Override protected List<ScriptTest> getChildren() {
    return tests;
  }

  @Override protected Description describeChild(ScriptTest child) {
    return child.getDescription();
  }

  @Override protected void runChild(ScriptTest child, RunNotifier notifier) {
    child.run(notifier);
  }
}
