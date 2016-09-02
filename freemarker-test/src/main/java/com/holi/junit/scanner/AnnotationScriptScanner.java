package com.holi.junit.scanner;

import com.holi.junit.Script;
import com.holi.junit.ScriptScanner;
import java.util.ArrayList;
import java.util.List;
import org.junit.runners.model.FrameworkMethod;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

/**
 * Created by selonj on 16-9-1.
 */
public class AnnotationScriptScanner implements ScriptScanner {
  @Override public List<Script> scan(TestClass testClass) throws Throwable {
    return ScriptScannerCollection.merge(scanners(allMethodsAnnotatedWith(Scanner.class, testClass))).scan(testClass);
  }

  private List<ScriptScanner> scanners(List<FrameworkMethod> methods) throws Throwable {
    ArrayList<ScriptScanner> scanners = new ArrayList<>();
    for (FrameworkMethod method : methods) scanners.add((ScriptScanner) method.invokeExplosively(null));
    return scanners;
  }

  private List<FrameworkMethod> allMethodsAnnotatedWith(Class<Scanner> annotationClass, TestClass testClass) throws InitializationError {
    List<FrameworkMethod> methods = testClass.getAnnotatedMethods(annotationClass);
    if (methods.isEmpty()) {
      throw new InitializationError("No public static @Scanner ScriptScanner methods defined in class " + testClass.getName() + "!");
    }
    return methods;
  }
}
