package com.holi.junit.freemarker.blocks;

import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateScalarModel;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

/**
 * Created by selonj on 16-9-1.
 */
public class EnvironmentSnapshot {

  private Map settings;
  private Map namespace;
  private Environment env;

  public EnvironmentSnapshot(Environment env) throws TemplateModelException {
    this.env = env;
    namespace = env.getCurrentNamespace().toMap();
    settings = new HashMap(env.getSettings());
  }

  public static EnvironmentSnapshot from(Environment env) throws TemplateModelException {
    return new EnvironmentSnapshot(env);
  }

  public void reset() throws TemplateException {
    clear();
    env.getCurrentNamespace().putAll(namespace);
    env.setSettings(properties(settings));
  }

  private Properties properties(Map map) {
    Properties properties = new Properties();
    Iterator<Map.Entry> itr = map.entrySet().iterator();
    while (itr.hasNext()) {
      Map.Entry pair = itr.next();
      properties.put(pair.getKey(), pair.getValue());
    }
    return properties;
  }

  private void clear() throws TemplateException {
    clearCurrentNamespace();
    clearSettings();
  }

  private void clearCurrentNamespace() throws TemplateModelException {
    Environment.Namespace namespace = env.getCurrentNamespace();
    TemplateModelIterator keys = namespace.keys().iterator();
    while (keys.hasNext()) {
      String key = key(keys.next());
      namespace.remove(key);
    }
  }

  private void clearSettings() throws TemplateException {
    for (Object name : env.getSettings().keySet()) {
      env.setSetting((String) name,null);
    }
  }

  private String key(TemplateModel key) throws TemplateModelException {
    if (key == null) return null;
    return ((TemplateScalarModel) key).getAsString();
  }
}
