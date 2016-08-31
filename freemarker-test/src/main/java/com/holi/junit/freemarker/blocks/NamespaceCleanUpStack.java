package com.holi.junit.freemarker.blocks;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModelException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by selonj on 16-9-1.
 */
public class NamespaceCleanUpStack implements BlockStack {

  private Environment env;
  private ThreadLocal<Map<JUnitBlock, Map>> snapshotStack = new ThreadLocal<Map<JUnitBlock, Map>>() {
    @Override protected Map<JUnitBlock, Map> initialValue() {
      return new HashMap<>();
    }
  };

  public NamespaceCleanUpStack(Environment env) {
    this.env = env;
  }

  @Override public void push(JUnitBlock block) throws TemplateException {
    save(block, namespace());
  }

  @Override public void pop(JUnitBlock block) throws TemplateException {
    try {
      clear(namespace());
      merge(namespace(), snapshot(block));
    } finally {
      removeSnapshot(block);
    }
  }

  private void save(JUnitBlock block, Environment.Namespace namespace) throws TemplateModelException {
    snapshots().put(block, namespace.toMap());
  }

  private Map<JUnitBlock, Map> snapshots() {
    return snapshotStack.get();
  }

  private Environment.Namespace namespace() {
    return env.getCurrentNamespace();
  }

  private void merge(Environment.Namespace namespace, Map snapshot) {
    namespace.putAll(snapshot);
  }

  private void clear(Environment.Namespace namespace) throws TemplateModelException {
    for (Object key : namespace.toMap().keySet()) namespace.remove((String) key);
  }

  private Map snapshot(JUnitBlock block) {
    return snapshots().get(block);
  }

  private void removeSnapshot(JUnitBlock block) {
    snapshots().remove(block);
  }
}
