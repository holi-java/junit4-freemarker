package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.expectation.BlockStack;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by selonj on 16-9-1.
 */
public class FixtureCleanUpStack implements BlockStack {

  private Environment env;
  private ThreadLocal<Map<JUnitBlock, EnvironmentSnapshot>> snapshotStack = new ThreadLocal<Map<JUnitBlock, EnvironmentSnapshot>>() {
    @Override protected Map<JUnitBlock, EnvironmentSnapshot> initialValue() {
      return new HashMap<>();
    }
  };

  public FixtureCleanUpStack(Environment env) {
    this.env = env;
  }

  @Override public void push(JUnitBlock block) throws TemplateException {
    save(block);
  }

  @Override public void pop(JUnitBlock block) throws TemplateException {
    try {
      snapshot(block).reset();
    } finally {
      removeSnapshot(block);
    }
  }

  private void save(JUnitBlock block) throws TemplateModelException {
    snapshots().put(block, EnvironmentSnapshot.from(env));
  }

  private Map<JUnitBlock, EnvironmentSnapshot> snapshots() {
    return snapshotStack.get();
  }

  private EnvironmentSnapshot snapshot(JUnitBlock block) {
    return snapshots().get(block);
  }

  private void removeSnapshot(JUnitBlock block) {
    snapshots().remove(block);
  }
}
