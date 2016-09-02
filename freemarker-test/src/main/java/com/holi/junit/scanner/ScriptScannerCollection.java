package com.holi.junit.scanner;

import com.holi.junit.Script;
import com.holi.junit.ScriptScanner;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.junit.runners.model.TestClass;

import static java.util.Arrays.asList;

/**
 * Created by selonj on 16-9-1.
 */
public class ScriptScannerCollection implements ScriptScanner {
  private List<ScriptScanner> scanners;

  public ScriptScannerCollection(List<ScriptScanner> scanners) {
    this.scanners = scanners;
  }

  public static ScriptScanner merge(List<ScriptScanner> scanners) {
    return new ScriptScannerCollection(scanners);
  }

  public static ScriptScanner merge(ScriptScanner... scanners) {
    return merge(asList(scanners));
  }

  @Override public List<Script> scan(TestClass testClass) throws Throwable {
    Set<Script> scripts = new LinkedHashSet<>();
    for (ScriptScanner scanner : scanners) scripts.addAll(scanner.scan(testClass));
    return toList(scripts);
  }

  private ArrayList<Script> toList(Set<Script> scripts) {
    return new ArrayList<>(scripts);
  }
}
