package com.holi.junit.freemarker.matchers;

import org.hamcrest.Matcher;
import org.junit.runner.Description;

import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;

/**
 * Created by selonj on 16-8-30.
 */
public class DescriptionMatchers {
  public static Matcher<? super Description> testThatIs(String testName) {
    return allOf(isTest(), descriptionWithTestName(testName));
  }

  public static Matcher<? super Description> isTest() {
    return hasProperty("test", is(true));
  }

  public static Matcher<? super Description> descriptionWithTestName(String testName) {
    return hasProperty("methodName", equalTo(testName));
  }
}
