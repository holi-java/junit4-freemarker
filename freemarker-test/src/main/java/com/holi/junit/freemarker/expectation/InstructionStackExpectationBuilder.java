package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.Expectation.ExpectationType;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import freemarker.core.Environment;
import freemarker.core.TemplateElement;
import freemarker.core._CoreAPI;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.sym.error;

/**
 * Created by selonj on 16-8-30.
 */
public class InstructionStackExpectationBuilder implements ExpectationBuilder {
  private ExpectationBuilder expectations;
  private final Environment env;

  public InstructionStackExpectationBuilder(ExpectationBuilder expectations, Environment env) {
    this.expectations = expectations;
    this.env = env;
  }

  @Override public Expectation create(ExpectationType type, final Map params, final TemplateDirectiveBody body) throws TemplateException {
    return dumpInstructionStackWhenExpectationFails(type, params, body);
  }

  private Expectation dumpInstructionStackWhenExpectationFails(final ExpectationType type, final Map params, final TemplateDirectiveBody body) {
    return createExpectationWithInstructionStack(type, params, body, instructionStack(env));
  }

  private Expectation createExpectationWithInstructionStack(final ExpectationType type, final Map params, final TemplateDirectiveBody body, final InstructionStack instructionStack) {
    return new Expectation() {
      @Override public void checking() throws TemplateException, IOException {
        try {
          expectations.create(type, params, body).checking();
        } catch (AssertInstructionStackException | AssertInstructionStackError error) {
          throw error;
        } catch (AssertionError error) {
          throw new AssertInstructionStackError(instructionStack, error);
        } catch (RuntimeException error) {
          throw new AssertInstructionStackException(instructionStack, error);
        }
      }
    };
  }

  private InstructionStack instructionStack(Environment env) {
    final String instructionStack = dumpInstructionStack(env);
    return new InstructionStack() {
      @Override public String dump(Throwable exception) {
        String message = exception.getLocalizedMessage();
        return message == null ? instructionStack : (message + "\n" + instructionStack);
      }
    };
  }

  private String dumpInstructionStack(Environment env) {
    TemplateElement[] stack = _CoreAPI.getInstructionStackSnapshot(env);
    StringWriter buff = new StringWriter();
    _CoreAPI.outputInstructionStack(stack, false, buff);
    return buff.toString();
  }

  private interface InstructionStack {

    String dump(Throwable exception);
  }

  private static class AssertInstructionStackError extends AssertionError {
    private AssertionError error;
    private InstructionStack instructionStack;

    public AssertInstructionStackError(InstructionStack instructionStack, AssertionError error) {
      super(error.getMessage());
      setStackTrace(error.getStackTrace());
      this.error = error;
      this.instructionStack = instructionStack;
    }

    public String toString() {
      String message = getLocalizedMessage();
      String s = error.getClass().getName();
      return (message == null ? s+":" : s) + instructionStack.dump(this);
    }
  }

  private static class AssertInstructionStackException extends RuntimeException {
    private final Exception error;
    private InstructionStack instructionStack;

    public AssertInstructionStackException(InstructionStack instructionStack, Exception error) {
      super(error.getMessage());
      setStackTrace(error.getStackTrace());
      this.error = error;
      this.instructionStack = instructionStack;
    }

    public String toString() {
      String message = getLocalizedMessage();
      String s = error.getClass().getName();
      return (message == null ? s+":" : s) + instructionStack.dump(this);
    }
  }
}
