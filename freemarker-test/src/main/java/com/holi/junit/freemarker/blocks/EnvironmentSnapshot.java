package com.holi.junit.freemarker.blocks;

import freemarker.core.ArithmeticEngine;
import freemarker.core.Configurable;
import freemarker.core.Environment;
import freemarker.core.TemplateClassResolver;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import freemarker.template.TemplateHashModelEx2;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateScalarModel;
import freemarker.template.Version;
import freemarker.template._TemplateAPI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

/**
 * Created by selonj on 16-9-1.
 */
public class EnvironmentSnapshot {
  private static final Map<String, String> defaults = new HashMap<>();

  static {
    defaults(Configuration.VERSION_2_3_25);
  }

  private static void defaults(Version version) {
    boolean logTemplateExceptionEnabled = _TemplateAPI.getDefaultLogTemplateExceptions(version);
    TemplateExceptionHandler exceptionHandler = _TemplateAPI.getDefaultTemplateExceptionHandler(version);
    defaults(logTemplateExceptionEnabled, Configurable.LOG_TEMPLATE_EXCEPTIONS_KEY_CAMEL_CASE, Configurable.LOG_TEMPLATE_EXCEPTIONS_KEY_SNAKE_CASE);
    defaults(exceptionHandler.getClass(), Configurable.TEMPLATE_EXCEPTION_HANDLER_KEY_CAMEL_CASE, Configurable.TEMPLATE_EXCEPTION_HANDLER_KEY_SNAKE_CASE);

    defaults(Locale.getDefault().toString(), Configurable.LOCALE_KEY_CAMEL_CASE, Configurable.LOCALE_KEY_SNAKE_CASE);
    defaults(TimeZone.getDefault().getID(), Configurable.TIME_ZONE_KEY_CAMEL_CASE, Configurable.TIME_ZONE_KEY_SNAKE_CASE);
    defaults("null", Configurable.SQL_DATE_AND_TIME_TIME_ZONE_KEY_CAMEL_CASE, Configurable.SQL_DATE_AND_TIME_TIME_ZONE_KEY_SNAKE_CASE);
    defaults("number", Configurable.NUMBER_FORMAT_KEY_CAMEL_CASE, Configurable.NUMBER_FORMAT_KEY_SNAKE_CASE);
    defaults("", Configurable.TIME_FORMAT_KEY_CAMEL_CASE, Configurable.TIME_FORMAT_KEY_SNAKE_CASE);
    defaults("", Configurable.DATE_FORMAT_KEY_CAMEL_CASE, Configurable.DATE_FORMAT_KEY_SNAKE_CASE);
    defaults("", Configurable.DATETIME_FORMAT_KEY_CAMEL_CASE, Configurable.DATETIME_FORMAT_KEY_SNAKE_CASE);
    defaults(String.valueOf(0), Configurable.CLASSIC_COMPATIBLE_KEY_CAMEL_CASE, Configurable.CLASSIC_COMPATIBLE_KEY_SNAKE_CASE);
    defaults(ArithmeticEngine.BIGDECIMAL_ENGINE.getClass().getName(), Configurable.ARITHMETIC_ENGINE_KEY_CAMEL_CASE, Configurable.ARITHMETIC_ENGINE_KEY_SNAKE_CASE);
    defaults(true, Configurable.AUTO_FLUSH_KEY_CAMEL_CASE, Configurable.AUTO_FLUSH_KEY_SNAKE_CASE);
    defaults(TemplateClassResolver.UNRESTRICTED_RESOLVER.getClass().getName(), Configurable.NEW_BUILTIN_CLASS_RESOLVER_KEY_CAMEL_CASE, Configurable.NEW_BUILTIN_CLASS_RESOLVER_KEY_SNAKE_CASE);
    defaults(true, Configurable.SHOW_ERROR_TIPS_KEY_CAMEL_CASE, Configurable.SHOW_ERROR_TIPS_KEY_SNAKE_CASE);
    defaults(false, Configurable.API_BUILTIN_ENABLED_KEY_CAMEL_CASE, Configurable.API_BUILTIN_ENABLED_KEY_SNAKE_CASE);
    defaults("true,false", Configurable.BOOLEAN_FORMAT_KEY_CAMEL_CASE, Configurable.BOOLEAN_FORMAT_KEY_SNAKE_CASE);
  }

  private static void defaults(String value, String... keys) {
    for (String key : keys) defaults.put(key, value);
  }

  private static void defaults(Class value, String... keys) {
    defaults(value.getName(), keys);
  }

  private static void defaults(boolean value, String... keys) {
    defaults(Boolean.toString(value), keys);
  }

  private Map<String, String> settings;
  private Map namespace;
  private Environment env;

  public EnvironmentSnapshot(Environment env) throws TemplateModelException {
    this.env = env;
    namespace = toMap(env.getCurrentNamespace().keyValuePairIterator());
    settings = new HashMap<>(env.getSettings());
  }

  public static EnvironmentSnapshot from(Environment env) throws TemplateModelException {
    return new EnvironmentSnapshot(env);
  }

  public void reset() throws TemplateException {
    clearNamespace();
    resetNamespaceVariables();
    clearSettings();
    resetSettings();
  }

  private void clearNamespace() throws TemplateModelException {
    Environment.Namespace namespace = env.getCurrentNamespace();
    for (String key : toList(namespace.keys().iterator())) namespace.remove(key);
  }

  private void resetNamespaceVariables() {
    env.getCurrentNamespace().putAll(namespace);
  }

  private void clearSettings() throws TemplateException {
    for (Object key : env.getSettings().keySet()) {
      env.setSetting((String) key, defaults.get(key));
    }
  }

  private void resetSettings() throws TemplateException {
    for (Map.Entry<String, String> setting : settings.entrySet()) {
      env.setSetting(setting.getKey(), setting.getValue());
    }
  }

  private static List<String> toList(TemplateModelIterator itr) throws TemplateModelException {
    List<String> result = new ArrayList<>();
    while (itr.hasNext()) result.add(string(itr.next()));
    return result;
  }

  private static Map<String, TemplateModel> toMap(TemplateHashModelEx2.KeyValuePairIterator itr) throws TemplateModelException {
    Map<String, TemplateModel> map = new HashMap<>();
    while (itr.hasNext()) {
      TemplateHashModelEx2.KeyValuePair pair = itr.next();
      map.put(string(pair.getKey()), pair.getValue());
    }
    return map;
  }

  private static String string(TemplateModel key) throws TemplateModelException {
    if (key == null) return null;
    return ((TemplateScalarModel) key).getAsString();
  }
}
