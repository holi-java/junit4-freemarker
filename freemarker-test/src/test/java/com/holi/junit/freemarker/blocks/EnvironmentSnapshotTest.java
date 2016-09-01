package com.holi.junit.freemarker.blocks;

import com.holi.junit.utils.Environments;
import freemarker.core.Environment;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.junit.Test;

import static freemarker.core.Configurable.OUTPUT_ENCODING_KEY;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

/**
 * Created by selonj on 16-9-1.
 */
public class EnvironmentSnapshotTest {
  private final Environment env = Environments.as("test.ftl", "");
  private final EnvironmentSnapshot snapshot = EnvironmentSnapshot.from(env);

  public EnvironmentSnapshotTest() throws TemplateModelException {
  }

  @Test public void savesNamespace() throws Exception {
    set("foo", "bar");
    assertThat(get("foo"), equalTo("bar"));

    snapshot.reset();

    assertThat(get("foo"), is(nullValue()));
  }

  @Test public void savesSettings() throws Exception {
    setOutputEncoding("UTF-8");
    assertThat(getOutputEncoding(), equalTo("UTF-8"));

    snapshot.reset();

    assertThat(getOutputEncoding(), is(nullValue()));
  }

  private String getOutputEncoding() {
    return env.getSetting(OUTPUT_ENCODING_KEY);
  }

  private void setOutputEncoding(String encoding) throws TemplateException {
    env.setOutputEncoding(encoding);
  }

  private void set(String name, String value) {
    currentNamespace().put(name, value);
  }

  private Environment.Namespace currentNamespace() {
    return env.getCurrentNamespace();
  }

  private String get(String name) throws TemplateModelException {
    TemplateModel result = currentNamespace().get(name);
    return result == null ? null : ((TemplateScalarModel) result).getAsString();
  }
}