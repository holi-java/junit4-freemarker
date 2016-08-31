package com.holi.junit;

import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

/**
 * Created by selonj on 16-8-29.
 */
public class FreeMarkerRunnerAcceptanceTest {

  public static final String scriptName = "test.ftl";
  public static final String test = "test";

  @Test public void testPassesWhenAssertTwoVariablesAreEquals() throws Throwable {
    TestResult result = test("<@assert expected='foo' actual='foo'/>");

    result.hasRanTest(test, null);
    result.assertAllTestsPassed();
  }

  @Test public void testFailsWhenAssertTwoVariablesAreDifferent() throws Throwable {
    TestResult result = test("<@assert expected='foo' actual='bar'/>");

    result.hasRanTest(test, null);
    result.hasTestFailed(test, null);
  }

  @Test public void reportInStackInScriptFileWhenTestFailed() throws Throwable {
    TestResult result = test("<@assert expected='foo' actual='bar'/>");

    result.hasRanTest(test, null);
    result.hasTestFailedWithErrorStack(test, null, containsString("\"test.ftl\" at line 1, column 1"));
  }

  @Test public void reportHelpMessageWithInstructionStackIfAssertBlockInvalid() throws Throwable {
    TestResult result = test("<@assert expected='foo' actual='bar'>${'foo'}</@assert>");

    result.hasRanTest(test, null);

    result.hasTestFailedWithErrorStack(test, null, containsString("<@assert expected=foo actual=bar/>"));
    result.hasTestFailedWithErrorStack(test, null, containsString("<@assert expected=foo /> and foo must be a boolean value"));
    result.hasTestFailedWithErrorStack(test, null, containsString("<@assert expected=foo>any template element</@assert> and foo must be a string scalar"));
    result.hasTestFailedWithErrorStack(test, null, containsString("\"test.ftl\" at line 1, column 1"));
  }

  @Test public void reportsFailedTestWithInstructionStackWhenAssertBlockNestedWithAnotherAssertBlock() throws Throwable {
    TestResult result = test("<@assert expected='foo'>\n<@assert/></@assert>");

    result.hasRanTest(test, null);
    result.hasTestFailedWithErrorStack(test, null, containsString("<@assert> can't nested with another <@assert> block!"));
    result.hasTestFailedWithErrorStack(test, null, containsString("\"test.ftl\" at line 2, column 1"));
  }

  @Test public void testPassesWhenAssertBooleanExpressionSatisfied() throws Throwable {
    TestResult result = test("<@assert expected=true/>");

    result.hasRanTest(test, null);
    result.assertAllTestsPassed();
  }

  @Test public void testPassesWhenWithEmptyTestBlock() throws Throwable {
    TestResult result = test("<@test name='something'/>");

    result.hasRanTests(1);
    result.assertAllTestsPassed();
    result.hasRanTest(test, "something");
  }

  @Test public void testFailedWhenWithAssertWithTestBlockFails() throws Throwable {
    TestResult result = test("<@test name='something'>\n<@assert expected=false/></@test>");

    result.hasRanTests(1);
    result.hasTestFailedWithErrorStack(test, "something", containsString("\"test.ftl\" at line 2, column 1"));
  }

  @Test public void testFailedWhenNestedWithAnotherTestBlock() throws Throwable {
    TestResult result = test("<@test name='something'>\n<@test name='another'/></@test>");

    result.hasRanTests(1);
    result.hasTestFailedWithErrorStack(test, "something", containsString("\"test.ftl\" at line 2, column 1"));
  }

  private TestResult test(String snippet) throws Throwable {
    return TestResult.test(scriptName, snippet);
  }
}
