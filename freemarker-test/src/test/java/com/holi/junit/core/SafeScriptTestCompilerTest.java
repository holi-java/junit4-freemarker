package com.holi.junit.core;

import com.holi.junit.core.SafeScriptTestCompiler;
import com.holi.junit.core.Script;
import com.holi.junit.core.ScriptTest;
import com.holi.junit.core.ScriptTestCompiler;
import com.holi.junit.utils.StaticScript;
import com.holi.junit.utils.TestResult;
import org.jmock.Expectations;
import org.jmock.integration.junit4.JUnitRuleMockery;
import org.junit.Rule;
import org.junit.Test;

import static com.holi.junit.utils.TestResult.test;
import static org.hamcrest.CoreMatchers.startsWith;

/**
 * Created by selonj on 16-8-31.
 */
public class SafeScriptTestCompilerTest {

  @Rule
  public final JUnitRuleMockery context = new JUnitRuleMockery();
  private final ScriptTestCompiler target = context.mock(ScriptTestCompiler.class);
  private final SafeScriptTestCompiler compiler = new SafeScriptTestCompiler(target);

  @Test public void createCompilationFailedTestWhenCompileFailed() throws Throwable {
    final Script script = StaticScript.createScript("src/test/resources", "test.ftl", "");
    context.checking(new Expectations() {{
      allowing(target).compile(script); will(throwException(new Exception("failed")));
    }});

    ScriptTest test = compiler.compile(script);

    TestResult result = test(test);
    result.hasRanTests(1);
    result.hasTestFailedWithErrorStack("test", null, startsWith("java.lang.Exception: failed"));
  }
}