package com.holi.junit.freemarker.matchers;

import com.holi.junit.Test;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;

/**
 * Created by selonj on 16-8-31.
 */
public class TestMatchers {
  public static Matcher<Test> testThatIs(String testName) {
    return testThatIs(equalTo(testName));
  }

  private static Matcher<Test> testThatIs(final Matcher<? super String> matcher) {
    return new FeatureMatcher<Test, String>(matcher, "test", "test") {
      @Override protected String featureValueOf(Test actual) {
        return actual.getName();
      }
    };
  }

  public static Matcher<Test> testThatFailedWithException(Throwable error) {
    return new FeatureMatcher<Test, Throwable>(equalTo(error), "test failed with ", "") {
      @Override protected Throwable featureValueOf(Test actual) {
        try {
          actual.run();
          return null;
        } catch (Throwable error) {
          return error;
        }
      }
    };
  }
}
