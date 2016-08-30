package com.holi.junit.freemarker.matchers;

import java.io.PrintWriter;
import java.io.StringWriter;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

/**
 * Created by selonj on 16-8-30.
 */
public class ThrowableMatchers {
  public static Matcher<? super Throwable> hasStackTrace(Matcher<? super String> errorStackMatcher) {
    return new FeatureMatcher<Throwable, String>(errorStackMatcher, "error stack", "error stack") {
      @Override protected String featureValueOf(Throwable actual) {
        StringWriter out = new StringWriter();
        actual.printStackTrace(new PrintWriter(out));
        return out.toString();
      }
    };
  }
}
