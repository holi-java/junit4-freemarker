package com.holi.junit.freemarker;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.Matchers.containsString;

/**
 * Created by selonj on 16-8-29.
 */
public class FreeMarkerRunnerAcceptanceTest {

  public static final String TEST_SCRIPT = "test.ftl";

  @Test public void testPassesWhenAssertTwoVariablesAreEquals() throws Exception {
    TestResult result = test("<@assert expected='foo' actual='foo'/>");

    result.hasRanTest("test.ftl");
    result.assertAllTestsPassed();
  }

  @Test public void testFailsWhenAssertTwoVariablesAreDifferent() throws Exception {
    TestResult result = test("<@assert expected='foo' actual='bar'/>");

    result.hasRanTest("test.ftl");
    result.assertTestFailed("test.ftl");
  }

  @Test public void reportInStackInScriptFileWhenTestFailed() throws Exception {
    TestResult result = test("<@assert expected='foo' actual='bar'/>");

    result.hasRanTest("test.ftl");
    result.assertTestFailedWithErrorStack("test.ftl", containsString("\"test.ftl\" at line 1, column 1"));
  }

  @Test public void reportHelpMessageWithInstructionStackIfAssertBlockInvalid() throws Exception {
    TestResult result = test("<@assert expected='foo' actual='bar'>${'foo'}</@assert>");

    result.hasRanTest("test.ftl");

    result.assertTestFailedWithErrorStack("test.ftl", containsString("<@assert expected=foo actual=bar/>"));
    result.assertTestFailedWithErrorStack("test.ftl", containsString("<@assert expected=foo /> and foo must be a boolean value"));
    result.assertTestFailedWithErrorStack("test.ftl", containsString("<@assert expected=foo>any template element</@assert> and foo must be a string scalar"));
    result.assertTestFailedWithErrorStack("test.ftl", containsString("\"test.ftl\" at line 1, column 1"));
  }

  @Test public void reportsFailedTestWithInstructionStackWhenAssertBlockNestedWithAnotherAssertBlock() throws Exception {
    TestResult result = test("<@assert expected='foo'>\n<@assert/></@assert>");

    result.hasRanTest("test.ftl");
    result.assertTestFailedWithErrorStack("test.ftl", containsString("<@assert> can't nested with another <@assert> block!"));
    result.assertTestFailedWithErrorStack("test.ftl", containsString("\"test.ftl\" at line 2, column 1"));
  }

  @Test public void testPassesWhenAssertBooleanExpressionSatisfied() throws Exception {
    TestResult result = test("<@assert expected=true/>");

    result.hasRanTest("test.ftl");
    result.assertAllTestsPassed();
  }

  private TestResult test(String snippet) throws Exception {
    return TestResult.test(TEST_SCRIPT, snippet);
  }
}
