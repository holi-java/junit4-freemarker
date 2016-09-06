package com.holi.junit.scanner;

import com.holi.junit.core.Script;
import java.util.ArrayList;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

import static com.holi.junit.scanner.ScriptScannerCollection.merge;

/**
 * Created by selonj on 16-9-1.
 */
public class AnnotationScriptScanner implements ScriptScanner {
  @Override public List<Script> scan(TestClass testClass) throws Throwable {
    return merge(scanners(allScannerFactoryMethods(testClass))).scan(testClass);
  }

  private List<FrameworkMethod> allScannerFactoryMethods(TestClass testClass) throws InitializationError {
    List<FrameworkMethod> methods = testClass.getAnnotatedMethods(Scanner.class);
    if (methods.isEmpty()) {
      throw new InitializationError("No public static @Scanner ScriptScanner methods defined in class " + testClass.getName() + "!");
    }
    return methods;
  }

  private List<ScriptScanner> scanners(List<FrameworkMethod> factoryMethods) throws Throwable {
    ArrayList<ScriptScanner> scanners = new ArrayList<>();
    for (FrameworkMethod method : factoryMethods) scanners.add((ScriptScanner) method.invokeExplosively(null));
    return scanners;
  }
}
