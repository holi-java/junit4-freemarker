package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.expectation.BlockStack;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;

/**
 * Created by selonj on 16-9-2.
 */
public class CurrentEnvironmentActivation implements BlockStack {
  private final Environment env;
  private static final Method SET_CURRENT_ENVIRONMENT = getSetCurrentEnvironmentMethod();
  private ThreadLocal<Environment> last = new ThreadLocal<>();

  private static Method getSetCurrentEnvironmentMethod() {
    Method[] methods = Environment.class.getDeclaredMethods();
    Class[] PARAMETER_TYPES = {Environment.class};
    for (Method candidate : methods) {
      if (!Modifier.isStatic(candidate.getModifiers())) continue;
      if (candidate.getReturnType() != Void.TYPE) continue;
      if (Arrays.equals(candidate.getParameterTypes(), PARAMETER_TYPES)) {
        return candidate;
      }
    }
    return null;
  }

  public CurrentEnvironmentActivation(Environment env) {
    this.env = env;
  }

  @Override public void push(JUnitBlock block) throws TemplateException {
    last.set(Environment.getCurrentEnvironment());
    setCurrentEnvironment(env);
  }

  @Override public void pop(JUnitBlock block) throws TemplateException {
    try {
      setCurrentEnvironment(last.get());
    } finally {
      last.remove();
    }
  }

  private void setCurrentEnvironment(Environment env) throws TemplateException {
    boolean accessible = SET_CURRENT_ENVIRONMENT.isAccessible();
    SET_CURRENT_ENVIRONMENT.setAccessible(true);
    try {
      SET_CURRENT_ENVIRONMENT.invoke(null, env);
    } catch (IllegalAccessException e) {
      fails(e);
    } catch (InvocationTargetException e) {
      fails(e.getCause());
    } finally {
      SET_CURRENT_ENVIRONMENT.setAccessible(accessible);
    }
  }

  private void fails(Throwable cause) throws TemplateException {
    throw new TemplateException(cause, env);
  }
}
