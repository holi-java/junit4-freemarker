package com.holi.junit.freemarker;

import com.holi.junit.core.Script;
import com.holi.junit.core.ScriptTest;
import com.holi.junit.utils.TestResult;
import org.junit.Test;

import static com.holi.junit.utils.StaticScript.createScript;
import static com.holi.junit.utils.TestResult.test;

/**
 * Created by selonj on 16-8-31.
 */
public class FreeMarkerScriptTestCompilerTest {
  private final FreeMarkerScriptTestCompiler compiler = new FreeMarkerScriptTestCompiler();
  private final String script = "test.ftl";
  private final String scriptTest = "test";

  @Test public void createGlobalScriptTestIfNoTestsExists() throws Throwable {
    ScriptTest test = compiler.compile(script(""));

    TestResult result = test(test);
    result.hasRanTests(1);
    result.hasRanTest(scriptTest, null);
    result.assertAllTestsPassed();
  }

  @Test public void createScriptTestsIfExistTests() throws Throwable {
    ScriptTest test = compiler.compile(script("<@test name=\"test something\"/>"));

    TestResult result = test(test);
    result.hasRanTests(1);
    result.hasRanTest(scriptTest, "test something");
    result.assertAllTestsPassed();
  }

  private Script script(final String code) {
    return createScript("src/test/resources", script, code);
  }
}