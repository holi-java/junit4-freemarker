package com.holi.junit.freemarker;

import com.holi.junit.AbstractScriptTest;
import com.holi.junit.Action;
import com.holi.junit.Script;
import com.holi.junit.ScriptTest;
import com.holi.junit.ScriptTestCompiler;
import com.holi.junit.Test;
import com.holi.junit.freemarker.blocks.AssertBlock;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import com.holi.junit.freemarker.blocks.JUnitBlock;
import com.holi.junit.freemarker.blocks.TestBlock;
import com.holi.junit.freemarker.blocks.TestCollector;
import com.holi.junit.freemarker.expectation.FreeMarkerExpectationBuilder;
import com.holi.junit.freemarker.expectation.InstructionStackExpectationBuilder;
import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
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
    List<Test> tests = collectTests(script.open(template(script.getName())));

    if (tests.isEmpty()) return createGlobalScriptTest(script);
    return createScriptSuiteTest(script, tests);
  }

  private List<Test> collectTests(Template template) throws IOException, TemplateException {
    final ArrayList<Test> tests = new ArrayList<>();
    AtomicBoolean compilationFinished = new AtomicBoolean(false);

    compiler(template, collectTestsOnCompilationStage(into(tests), compilationFinished)).process();

    compilationFinished.set(true);
    return tests;
  }

  private Environment compiler(Template template, TestCollector collector) throws TemplateException, IOException {
    Environment env = template.createProcessingEnvironment(null, NullWriter.INSTANCE);
    for (JUnitBlock block : testBlocks(env, collector)) env.setGlobalVariable(block.getName(), block);
    return env;
  }

  private TestCollector collectTestsOnCompilationStage(final TestCollector collector, final AtomicBoolean finished) {
    return new TestCollector() {
      @Override public void add(Test test) {
        if (isOutOfCompilationStage()) throw new TestOutOfCompilationStage();
        collector.add(test);
      }

      private boolean isOutOfCompilationStage() {
        return finished.get();
      }
    };
  }

  private TestCollector into(final ArrayList<Test> tests) {
    return new TestCollector() {
      @Override public void add(final Test test) {
        tests.add(test);
      }
    };
  }

  private Action<Reader, Template> template(final String script) {
    return new Action<Reader, Template>() {
      @Override public Template call(Reader it) throws IOException {
        return new Template(script, it, configuration());
      }
    };
  }

  private Configuration configuration() {
    final Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
    configuration.setLogTemplateExceptions(false);
    return configuration;
  }

  private List<? extends JUnitBlock> testBlocks(Environment environment, TestCollector collector) {
    ExpectationBuilder expectations = new InstructionStackExpectationBuilder(new FreeMarkerExpectationBuilder(), environment);
    return Arrays.asList(testBlock(collector, expectations), assertBlock(expectations));
  }

  private AssertBlock assertBlock(ExpectationBuilder expectations) {
    return new AssertBlock(expectations);
  }

  private TestBlock testBlock(TestCollector collector, ExpectationBuilder expectations) {
    return new TestBlock(collector,expectations);
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
