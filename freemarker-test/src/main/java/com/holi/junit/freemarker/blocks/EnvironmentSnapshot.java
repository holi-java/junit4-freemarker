package com.holi.junit.freemarker.blocks;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateScalarModel;
import java.util.Map;

/**
 * Created by selonj on 16-9-1.
 */
public class EnvironmentSnapshot {

  private Map namespace;
  private Environment env;

  public EnvironmentSnapshot(Environment env) throws TemplateModelException {
    this.env = env;
    namespace = env.getCurrentNamespace().toMap();
  }

  public static EnvironmentSnapshot from(Environment env) throws TemplateModelException {
    return new EnvironmentSnapshot(env);
  }

  public void reset() throws TemplateException {
    clear();
    env.getCurrentNamespace().putAll(namespace);
  }

  private void clear() throws TemplateException {
    Environment.Namespace namespace1 = env.getCurrentNamespace();
    TemplateModelIterator keys = namespace1.keys().iterator();
    while (keys.hasNext()) {
      String key = key(keys.next());
      namespace1.remove(key);
    }
  }

  private String key(TemplateModel key) throws TemplateModelException {
    if (key == null) return null;
    return ((TemplateScalarModel) key).getAsString();
  }
}
