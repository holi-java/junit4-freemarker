package com.holi.junit;

import com.holi.junit.scanner.Scanner;
import com.holi.junit.scanner.ScriptScanner;
import com.holi.junit.utils.StaticScript;
import com.holi.junit.utils.TestResult;
import org.junit.Test;
import org.junit.runners.model.InitializationError;

import static com.holi.junit.utils.StaticScriptScanner.scanAs;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.hasItem;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.internal.matchers.ThrowableMessageMatcher.hasMessage;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptRunnerTest {

  @Test public void throwsInitializationErrorWhenClassMissingScannerAnnotation() throws Throwable {
    try {
      runAs(MissingScannerAnnotation.class);
      fail("should failed");
    } catch (InitializationError expected) {
      assertThat(expected.getCauses(), hasItem(hasMessage(containsString("No public static @Scanner ScriptScanner methods"))));
    }
  }

  @Test public void scanScriptsBaseOnTheTestClassScanner() throws Throwable {
    TestResult result = runAs(AnnotatedWithScannerAnnotation.class);

    result.hasRanTests(1);
    result.hasRanTest("test", null);
  }

  @Test public void scanScriptsBaseOnMergingTheTestClassScannersIfMultiScannersDefined() throws Throwable {
    TestResult result = runAs(AnnotatedWithMultiScannerAnnotations.class);

    result.hasRanTests(2);
    result.hasRanTest("test1", null);
    result.hasRanTest("test2", null);
  }

  private TestResult runAs(Class<?> testClass) throws Throwable {
    return TestResult.test(new ScriptRunner(testClass));
  }

  public static class MissingScannerAnnotation {
  }

  public static class AnnotatedWithScannerAnnotation {

    public static @Scanner ScriptScanner scanner() {
      return scanAs(StaticScript.createScript("src/test/resources", "test.ftl", "test"));
    }
  }

  public static class AnnotatedWithMultiScannerAnnotations {

    public static @Scanner ScriptScanner scanner1() {
      return scanAs(StaticScript.createScript("src/test/resources", "test1.ftl", "test"));
    }

    public static @Scanner ScriptScanner scanner2() {
      return scanAs(StaticScript.createScript("src/test/resources", "test2.ftl", "test"));
    }
  }
}