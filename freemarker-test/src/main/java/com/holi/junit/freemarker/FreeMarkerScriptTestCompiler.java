package com.holi.junit.freemarker;

import com.holi.junit.AbstractScriptTest;
import com.holi.junit.Action;
import com.holi.junit.Script;
import com.holi.junit.ScriptTest;
import com.holi.junit.ScriptTestCompiler;
import com.holi.junit.Test;
import com.holi.junit.freemarker.blocks.AssertBlock;
import com.holi.junit.freemarker.expectation.BlockStack;
import com.holi.junit.freemarker.expectation.stack.BlockStackCollection;
import com.holi.junit.freemarker.expectation.stack.CurrentEnvironmentActivation;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import com.holi.junit.freemarker.expectation.stack.FixtureCleanUpStack;
import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.blocks.TestBlock;
import com.holi.junit.freemarker.blocks.TestCollector;
import com.holi.junit.freemarker.expectation.stack.TopBlockStack;
import com.holi.junit.freemarker.expectation.ExpectationStackBuilder;
import com.holi.junit.freemarker.expectation.FreeMarkerExpectationBuilder;
import com.holi.junit.freemarker.expectation.InstructionStackExpectationBuilder;
import freemarker.cache.FileTemplateLoader;
import freemarker.core.Environment;
import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
import freemarker.template.utility.NullWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import org.junit.runner.Description;
import org.junit.runner.notification.RunNotifier;
import org.junit.runners.model.Statement;

/**
 * Created by selonj on 16-8-31.
 */
public class FreeMarkerScriptTestCompiler implements ScriptTestCompiler {
  @Override public ScriptTest compile(Script script) throws Exception {
    List<Test> tests = collectTests(script.open(template(script)));

    if (tests.isEmpty()) return createGlobalScriptTest(script);
    return createScriptSuiteTest(script, tests);
  }

  private List<Test> collectTests(Template template) throws IOException, TemplateException {
    final ArrayList<Test> tests = new ArrayList<>();
    AtomicBoolean compilationCompleted = new AtomicBoolean(false);
    compiler(template, collectTestsOnCompilationStage(tests, compilationCompleted)).process();
    compilationCompleted.set(true);
    return tests;
  }

  private Environment compiler(Template template, TestCollector collector) throws TemplateException, IOException {
    Environment env = template.createProcessingEnvironment(null, NullWriter.INSTANCE);
    env.setVariable("$exception", as(Throwable.class, (BeansWrapper) env.getObjectWrapper()));
    for (JUnitBlock block : testBlocks(collector, env)) env.setGlobalVariable(block.getName(), block);
    return env;
  }

  private TemplateModel as(final Class<Throwable> exception, BeansWrapper wrapper) {
    return new BeanModel(exception, wrapper);
  }

  private List<? extends JUnitBlock> testBlocks(TestCollector collector, Environment env) {
    BlockStack stack = new BlockStackCollection(new TopBlockStack(env), new CurrentEnvironmentActivation(env), new FixtureCleanUpStack(env));
    ExpectationBuilder expectations = createExpectationBuilder(env, stack);
    return Arrays.asList(testBlock(collector, expectations), assertBlock(expectations));
  }

  private ExpectationStackBuilder createExpectationBuilder(Environment env, BlockStack stack) {
    return new ExpectationStackBuilder(new InstructionStackExpectationBuilder(new FreeMarkerExpectationBuilder(), env), stack);
  }

  private TestCollector collectTestsOnCompilationStage(final List<Test> tests, final AtomicBoolean compilationCompleted) {
    return new TestCollector() {
      @Override public void add(final Test test) {
        if (compilationCompleted.get()) throw new TestOutOfCompilationStageException(test);
        tests.add(test);
      }
    };
  }

  private Action<Reader, Template> template(final Script script) {
    return new Action<Reader, Template>() {
      @Override public Template call(Reader it) throws IOException {
        return new Template(script.getName(), it, configuration(script));
      }
    };
  }

  private Configuration configuration(Script script) throws IOException {
    final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
    configuration.setTemplateLoader(new FileTemplateLoader(script.baseDir()));
    configuration.setLogTemplateExceptions(false);
    return configuration;
  }

  private AssertBlock assertBlock(ExpectationBuilder expectations) {
    return new AssertBlock(expectations);
  }

  private TestBlock testBlock(TestCollector collector, ExpectationBuilder expectations) {
    return new TestBlock(collector, expectations);
  }

  private ScriptTest createGlobalScriptTest(final Script script) {
    return new AbstractScriptTest(script) {

      @Override public void run(RunNotifier notifier) {
        runTest(testAlwaysPassingBlock(), getDescription(), notifier);
      }

      private Statement testAlwaysPassingBlock() {
        return new Statement() {
          @Override public void evaluate() throws Throwable {

          }
        };
      }
    };
  }

  private ScriptTest createScriptSuiteTest(final Script script, final List<Test> tests) {
    return new AbstractScriptTest(script) {
      @Override public Description getDescription() {
        Description description = super.getDescription();
        for (Test test : tests) {
          description.addChild(testDescription(test));
        }
        return description;
      }

      private Description testDescription(Test test) {
        return Description.createTestDescription(testName(script), test.getName());
      }

      @Override public void run(RunNotifier notifier) {
        for (Test test : tests) runTest(testBlock(test), testDescription(test), notifier);
      }

      private Statement testBlock(final Test test) {
        return new Statement() {
          @Override public void evaluate() throws Throwable {
            test.run();
          }
        };
      }
    };
  }
}
