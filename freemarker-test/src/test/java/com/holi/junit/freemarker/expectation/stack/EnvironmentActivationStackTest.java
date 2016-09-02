package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.utils.Environments;
import freemarker.core.Environment;
import org.junit.Test;

import static freemarker.core.Environment.getCurrentEnvironment;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-2.
 */
public class EnvironmentActivationStackTest {
  private static final JUnitBlock UNUSED_BLOCK = null;
  private final Environment env = Environments.as("test.ftl", "");
  private final EnvironmentActivationStack activation = new EnvironmentActivationStack(env);

  @Test public void activeCurrentEnvironment() throws Exception {
    assertThat(getCurrentEnvironment(), is(nullValue()));

    activation.push(UNUSED_BLOCK);
    assertThat(getCurrentEnvironment(), is(env));

    activation.pop(UNUSED_BLOCK);
    assertThat(getCurrentEnvironment(), is(nullValue()));
  }
}