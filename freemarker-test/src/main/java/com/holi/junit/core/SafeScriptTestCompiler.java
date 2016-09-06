package com.holi.junit.core;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.Statement;

/**
 * Created by selonj on 16-8-31.
 */
public class SafeScriptTestCompiler implements ScriptTestCompiler {
  private final ScriptTestCompiler compiler;

  public SafeScriptTestCompiler(ScriptTestCompiler compiler) {
    this.compiler = compiler;
  }

  @Override public ScriptTest compile(Script script) throws Exception {
    try {
      return compiler.compile(script);
    } catch (Throwable error) {
      return reportsExceptionOnTestStage(script, error);
    }
  }

  private ScriptTest reportsExceptionOnTestStage(final Script script, final Throwable error) {
    return new AbstractScriptTest(script) {

      @Override public void run(RunNotifier notifier) {
        runTest(reportCompilationFailureBlock(error), getDescription(), notifier);
      }

      private Statement reportCompilationFailureBlock(final Throwable error) {
        return new Statement() {
          @Override public void evaluate() throws Throwable {
            throw error;
          }
        };
      }
    };
  }
}
