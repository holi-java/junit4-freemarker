package com.holi.junit.scanner;

import com.holi.junit.Script;
import com.holi.junit.ScriptScanner;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.runners.model.TestClass;

/**
 * Created by selonj on 16-9-1.
 */
public class FileSystemScriptScanner implements ScriptScanner {
  private File root;
  private ScriptMatcher scriptMatcher;

  public FileSystemScriptScanner(File root, ScriptMatcher scriptMatcher) {
    this.root = root;
    this.scriptMatcher = scriptMatcher;
  }

  @Override public List<Script> scan(TestClass testClass) {
    if (!root.exists()) return Collections.emptyList();
    return scan(root);
  }

  private List<Script> scan(File directory) {
    List<Script> scripts = new ArrayList<>();
    for (File file : directory.listFiles()) {
      if (file.isDirectory()) {
        scripts.addAll(scan(file));
        continue;
      }
      if (scriptMatcher.matches(file)) scripts.add(createScript(file));
    }
    return scripts;
  }

  private Script createScript(final File file) {
    return new ScriptFile(root, file);
  }
}
