package com.holi.junit;

import com.holi.junit.utils.TestResult;
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
    result.hasTestFailedWithErrorStack(test, null, containsString("<@assert> block can't be nested with another <@assert> block!"));
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

  @Test public void reportFailedTestWithInstructionStackWhenExpectedExceptionDoesNotBeCaught() throws Throwable {
    TestResult result = test("<@test name='something' expected='java.lang.Exception'>${'true'?boolean?string}</@test>");

    result.hasRanTests(1);
    result.hasTestFailedWithErrorStack(test, "something", containsString("\"test.ftl\" at line 1, column 1"));
  }

  @Test public void testCanIncludeRelativeTemplates() throws Throwable {
    TestResult result = test("<#include '_foo.ftl'><@assert expected=foo actual='bar'/>");

    result.hasRanTests(1);
    result.assertAllTestsPassed();
  }

  @Test public void resetVariablesForEachTest() throws Throwable {
    TestResult result = test(
        "<@test name='first'><#assign foo='bar'><@assert expected=foo?exists /></@test>" +
    /**/"<@test name='second'><@assert expected=!foo?exists/></@test>"
    );

    result.hasRanTests(2);
    result.assertAllTestsPassed();
  }

  @Test public void resetSettingsForEachTest() throws Throwable {
    TestResult result = test(
    /**/"<@test name='custom number format'>" +
        /**/"<#setting number_format='000.##'/>" +
        /**/"<@assert expected='001.23' actual=1.234?string/>" +
    /**/"</@test>" +
        /**/"<@test name='default'>" +
        /**/"<@assert expected='1.234' actual=1.234?string/>" +
    /**/"</@test>"
    );

    result.hasRanTests(2);
    result.assertAllTestsPassed();
  }

  @Test public void fixBugIfMacroAndAssignmentDefinedBeforeTest() throws Throwable {
    TestResult result = test(
  /**/   "<#assign foo='bar'>"
  /**/ + "<#macro _></#macro>"
  /**/ + "<@test></@test>"
    );

    result.hasRanTests(1);
    result.assertAllTestsPassed();
  }

  private TestResult test(String snippet) throws Throwable {
    return TestResult.test(scriptName, snippet);
  }
}
