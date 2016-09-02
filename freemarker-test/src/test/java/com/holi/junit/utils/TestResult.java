package com.holi.junit.utils;

import com.holi.junit.ScriptRunner;
import com.holi.junit.ScriptTest;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

import static com.holi.junit.freemarker.matchers.DescriptionMatchers.testThatIs;
import static com.holi.junit.freemarker.matchers.FailureMatchers.failureWithDescription;
import static com.holi.junit.freemarker.matchers.FailureMatchers.failureWithException;
import static com.holi.junit.freemarker.matchers.ThrowableMatchers.hasStackTrace;
import static com.holi.junit.utils.StaticScript.createScript;
import static com.holi.junit.utils.StaticScriptScanner.scanAs;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-29.
 */
public class TestResult {
  private final List<Description> started = new ArrayList<>();
  private final List<Description> ran = new ArrayList<>();
  private final List<Failure> failures = new ArrayList<>();

  private TestResult(TestEntryPoint entryPoint) throws Throwable {
    run(entryPoint);
  }

  public static TestResult test(String script, String snippet) throws Throwable {
    return test(new ScriptRunner(TestResult.class, scanAs(createScript("src/test/resources", script, snippet))));
  }

  public static TestResult test(final ScriptTest test) throws Throwable {
    return runAs(new TestEntryPoint() {
      @Override public void run(RunNotifier notifier) throws Throwable {
        test.run(notifier);
      }
    });
  }

  public static TestResult test(final ScriptRunner runner) throws Throwable {
    return runAs(new TestEntryPoint() {
      @Override public void run(RunNotifier notifier) throws Throwable {
        runner.run(notifier);
      }
    });
  }

  public static TestResult runAs(TestEntryPoint entryPoint) throws Throwable {
    return new TestResult(entryPoint);
  }

  private interface TestEntryPoint {
    void run(RunNotifier notifier) throws Throwable;
  }

  private void run(TestEntryPoint test) throws Throwable {
    RunNotifier notifier = new RunNotifier();
    notifier.addFirstListener(collectTests());
    test.run(notifier);
    assertAllStartedTestsHaveBeenFinished();
  }

  private void assertAllStartedTestsHaveBeenFinished() {
    assertThat(started, is(empty()));
  }

  private RunListener collectTests() {
    return new RunListener() {
      @Override public void testStarted(Description description) throws Exception {
        started.add(description);
      }

      @Override public void testFinished(Description description) throws Exception {
        checkingTestStarted(description);
        started.remove(description);
        ran.add(description);
      }

      private void checkingTestStarted(Description description) {
        if (!isTestStarted(description)) {
          throw new IllegalStateException("test has not started: " + description);
        }
      }

      @Override public void testFailure(Failure failure) throws Exception {
        checkingTestStarted(failure.getDescription());
        failures.add(failure);
      }

      private boolean isTestStarted(Description description) {
        return started.contains(description);
      }
    };
  }

  public void hasRanTest(String script, String testName) {
    assertThat(ran, hasItem(testThatIs(script, testName)));
  }

  public void assertAllTestsPassed() {
    assertThat(failures, is(empty()));
  }

  public void hasTestFailed(String script, String testName) {
    hasTestFailedWithErrorStack(script, testName, any(String.class));
  }

  public void hasTestFailedWithErrorStack(String script, String testName, Matcher<? super String> errorStackMatcher) {
    assertThat(failures, hasItem(allOf(failureWithDescription(testThatIs(script, testName)), failureWithException(hasStackTrace(errorStackMatcher)))));
  }

  public void hasRanTests(int testCount) {
    assertThat(ran, hasSize(testCount));
  }
}
