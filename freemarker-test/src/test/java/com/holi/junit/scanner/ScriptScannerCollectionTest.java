package com.holi.junit.scanner;

import com.holi.junit.core.Script;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import org.junit.runners.model.TestClass;

import static com.holi.junit.scanner.ScriptScannerCollection.merge;
import static com.holi.junit.utils.StaticScript.createScript;
import static com.holi.junit.utils.StaticScriptScanner.scanAs;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptScannerCollectionTest {

  private static final TestClass UNUSED_TEST_CLASS = null;
  private final Script script1 = script("test1.ftl");
  private final Script script2 = script("test2.ftl");

  @Test public void mergeAllScripts() throws Throwable {
    ScriptScanner scanner = merge(Arrays.asList(scanAs(script1), scanAs(script2)));

    List<Script> scripts = scanner.scan(UNUSED_TEST_CLASS);

    assertThat(scripts, hasSize(2));
    assertThat(scripts, hasItem(script1));
    assertThat(scripts, hasItem(script2));
  }

  @Test public void removeDuplicatedScripts() throws Throwable {
    ScriptScanner scanner = merge(Arrays.asList(scanAs(script1, script1), scanAs(script2)));

    List<Script> scripts = scanner.scan(UNUSED_TEST_CLASS);

    assertThat(scripts, hasSize(2));
    assertThat(scripts, hasItem(script1));
    assertThat(scripts, hasItem(script2));
  }

  private Script script(String name) {
    return createScript("src/test/resources", name, "");
  }
}