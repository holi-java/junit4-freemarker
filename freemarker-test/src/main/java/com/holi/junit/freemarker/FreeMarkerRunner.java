package com.holi.junit.freemarker;

import com.holi.junit.freemarker.internal.blocks.AssertBlock;
import com.holi.junit.freemarker.internal.expectation.FreeMarkerExpectationBuilder;
import com.holi.junit.freemarker.internal.expectation.InstructionStackExpectationBuilder;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.utility.NullWriter;
import java.io.Reader;
import java.util.Arrays;
import java.util.List;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.ParentRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.Statement;

/**
 * Created by selonj on 16-8-30.
 */
public class FreeMarkerRunner extends ParentRunner<Script> {

  private Script script;

  public FreeMarkerRunner(Class<?> testClass) throws InitializationError {
    super(testClass);
  }

  public FreeMarkerRunner(Class<?> testClass, Script script) throws InitializationError {
    super(testClass);
    this.script = script;
  }

  @Override protected List<Script> getChildren() {
    return Arrays.asList(script);
  }

  @Override protected Description describeChild(Script child) {
    return Description.createTestDescription(getTestClass().getJavaClass(), testName(child));
  }

  private String testName(Script script) {
    return script.getName();
  }

  @Override protected void runChild(Script child, RunNotifier notifier) {
    Description description = describeChild(child);
    runLeaf(testBlock(child), description, notifier);
  }

  private Statement testBlock(final Script script) {
    return new Statement() {
      @Override public void evaluate() throws Throwable {
        script.open(template(script.getName())).process();
      }

      private Action<Reader, Environment> template(final String name) {
        final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
        configuration.setLogTemplateExceptions(false);
        return new Action<Reader, Environment>() {
          @Override public Environment call(Reader it) {
            try {
              Environment environment = new Template(name, it, configuration).createProcessingEnvironment(null, NullWriter.INSTANCE);
              AssertBlock block = new AssertBlock(new InstructionStackExpectationBuilder(new FreeMarkerExpectationBuilder(), environment));
              environment.setVariable(block.getName(), block);
              return environment;
            } catch (Exception e) {
              throw new Error(e);
            }
          }
        };
      }
    };
  }
}
