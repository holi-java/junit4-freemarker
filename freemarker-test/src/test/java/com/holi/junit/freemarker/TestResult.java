package com.holi.junit.freemarker;

import com.holi.junit.freemarker.matchers.DescriptionMatchers;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.hamcrest.Matcher;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.InitializationError;

import static com.holi.junit.freemarker.matchers.FailureMatchers.failureWithDescription;
import static com.holi.junit.freemarker.matchers.FailureMatchers.failureWithException;
import static com.holi.junit.freemarker.matchers.ThrowableMatchers.hasStackTrace;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.any;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-29.
 */
public class TestResult {
  private final List<Description> started = new ArrayList<>();
  private final List<Description> ran = new ArrayList<>();
  private final List<Failure> failures = new ArrayList<>();

  public TestResult(final String script, final String snippet) throws Exception {
    test(new Script() {
      public String getName() {
        return script;
      }

      @Override public <R> R open(Action<Reader, R> action) {
        return action.call(new StringReader(snippet));
      }
    });
  }

  public static TestResult test(String script, String snippet) throws Exception {
    return new TestResult(script, snippet);
  }

  private void test(Script script) throws InitializationError {
    RunNotifier notifier = new RunNotifier();
    notifier.addFirstListener(collectTests());
    createTest(script).run(notifier);
    assertAllStartedTestsHaveBeenFinished();
  }

  private void assertAllStartedTestsHaveBeenFinished() {
    assertThat(started, is(empty()));
  }

  private FreeMarkerRunner createTest(Script script) throws InitializationError {
    return new FreeMarkerRunner(TestResult.class, script);
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

  public void hasRanTest(String testName) {
    assertThat(ran, hasItem(DescriptionMatchers.testThatIs(testName)));
  }

  public void assertAllTestsPassed() {
    assertThat(failures, is(empty()));
  }

  public void assertTestFailed(String testName) {
    assertTestFailedWithErrorStack(testName, any(String.class));
  }

  public void assertTestFailedWithErrorStack(String testName, Matcher<? super String> errorStackMatcher) {
    assertThat(failures, hasItem(allOf(failureWithDescription(DescriptionMatchers.testThatIs(testName)), failureWithException(hasStackTrace(errorStackMatcher)))));
  }
}
