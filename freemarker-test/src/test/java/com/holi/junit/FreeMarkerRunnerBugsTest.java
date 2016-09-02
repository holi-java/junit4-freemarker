package com.holi.junit;

import com.holi.junit.utils.TestResult;
import org.junit.Test;

/**
 * Created by selonj on 16-8-29.
 */
public class FreeMarkerRunnerBugsTest {

  public static final String scriptName = "test.ftl";
  public static final String test = "test";

  @Test public void fixBugIfMacroAndAssignmentDefinedBeforeTest() throws Throwable {
    TestResult result = test(
  /**/   "<#assign foo='bar'>"
  /**/ + "<#macro _></#macro>"
  /**/ + "<@test></@test>"
    );

    result.hasRanTests(1);
    result.assertAllTestsPassed();
  }

  @Test public void fixBugAssertCannotUseGroupsBuiltInCorrectly() throws Throwable {
    TestResult result = test(
    /**/   "<#assign foo='*'?matches('(.)')>"
    /**/ + "<@assert expected='*' actual=foo?groups[1]/>"
    /**/ + "<@assert expected='*' actual=foo?groups[1]/>"
    );

    result.hasRanTests(1);
    result.assertAllTestsPassed();
  }

  @Test public void fixBugCurrentEnvironmentWasNullWhenRunTestBlock() throws Throwable {
    TestResult result = test(
    /**/   "<@test name='foo'>\n"
    /**/ + "  <@assert expected=[1] actual=[1]?sort/>\n"
    /**/ + "</@test>\n"
    );

    result.hasRanTests(1);
    result.assertAllTestsPassed();
  }

  private TestResult test(String snippet) throws Throwable {
    return TestResult.test(scriptName, snippet);
  }
}
