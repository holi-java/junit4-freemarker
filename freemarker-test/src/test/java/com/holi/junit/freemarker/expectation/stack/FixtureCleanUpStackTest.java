package com.holi.junit.freemarker.expectation.stack;

import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.expectation.stack.FixtureCleanUpStack;
import com.holi.junit.utils.Environments;
import com.holi.junit.utils.JUnitBlocks;
import freemarker.core.Environment;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateScalarModel;
import org.junit.Test;

import static com.holi.junit.utils.TemplateModels.scalar;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-9-1.
 */
public class FixtureCleanUpStackTest {
  private final JUnitBlock testBlock = JUnitBlocks.blockNamed("test");
  private final JUnitBlock assertBlock = JUnitBlocks.blockNamed("assert");
  private final Environment env = Environments.as("test.ftl", "valid");
  private final FixtureCleanUpStack stack = new FixtureCleanUpStack(env);

  @Test public void resetNamespaceAfterBlockPop() throws Exception {
    stack.push(testBlock);
    set("foo", "bar");
    assertThat(get("foo"), equalTo("bar"));

    stack.pop(testBlock);

    assertThat(get("foo"), is(nullValue()));
  }

  @Test public void resetNamespaceSeparately() throws Exception {
    stack.push(testBlock);
    set("foo", "bar");
    assertThat(get("foo"), equalTo("bar"));//parent

    stack.push(assertBlock);
    set("foo", "other");
    assertThat(get("foo"), equalTo("other"));//child

    stack.pop(assertBlock);
    assertThat(get("foo"), equalTo("bar"));//parent

    stack.pop(testBlock);
    assertThat(get("foo"), is(nullValue()));//root
  }

  private String get(String name) throws TemplateModelException {
    if (currentNamespace().containsKey(name)) {
      return ((TemplateScalarModel) currentNamespace().get(name)).getAsString();
    }
    return null;
  }

  private void set(String name, String value) {
    currentNamespace().put(name, scalar(value));
  }

  private Environment.Namespace currentNamespace() {
    return env.getCurrentNamespace();
  }
}