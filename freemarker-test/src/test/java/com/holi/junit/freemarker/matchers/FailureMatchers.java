package com.holi.junit.freemarker.matchers;

import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;

import static org.hamcrest.Matchers.hasProperty;

/**
 * Created by selonj on 16-8-30.
 */
public class FailureMatchers {
  public static Matcher<? super Failure> failureWithDescription(Matcher<? super Description> matcher) {
    return hasProperty("description", matcher);
  }

  public static Matcher<? super Failure> failureWithException(Matcher<? super Throwable> matcher) {
    return hasProperty("exception", matcher);
  }
}
