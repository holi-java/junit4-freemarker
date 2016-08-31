package com.holi.junit.freemarker.matchers;

import com.holi.junit.Script;
import java.io.File;
import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasProperty;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptMatchers {
  public static Matcher<Script> scriptThatIs(String name) {
    return hasProperty("name", equalTo(name));
  }

  public static Matcher<Script> scriptWithBaseDir(File baseDir) {
    return new FeatureMatcher<Script, File>(equalTo(baseDir), "script baseDir", "") {
      @Override protected File featureValueOf(Script actual) {
        return actual.baseDir();
      }
    };
  }
}
