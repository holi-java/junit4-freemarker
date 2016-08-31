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

/**
 * Created by selonj on 16-8-30.
 */
public class InstructionStackExpectationBuilder implements ExpectationBuilder {
  private ExpectationBuilder expectations;

  public InstructionStackExpectationBuilder(ExpectationBuilder expectations) {
    this.expectations = expectations;
  }

  @Override public Expectation create(ExpectationType type, Environment env, final Map params, final TemplateDirectiveBody body) throws TemplateException {
    return dumpInstructionStackWhenExpectationFails(type, env, params, body);
  }

  private Expectation dumpInstructionStackWhenExpectationFails(final ExpectationType type, final Environment env, final Map params, final TemplateDirectiveBody body) {
    final InstructionStack instructionStack = instructionStack(env);
    return new Expectation() {
      @Override public void checking() throws TemplateException, IOException {
        try {
          expectations.create(type, env, params, body).checking();
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
        return message == null ? dump() : (message + "\n" + dump());
      }

      @Override public String dump() {
        return instructionStack;
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
    String dump();

    String dump(Throwable exception);
  }

  private static class AssertInstructionStackError extends AssertionError {
    private InstructionStack instructionStack;

    public AssertInstructionStackError(InstructionStack instructionStack, AssertionError error) {
      super(error.getMessage());
      setStackTrace(error.getStackTrace());
      this.instructionStack = instructionStack;
    }

    public String toString() {
      return instructionStack.dump(this);
    }
  }

  private static class AssertInstructionStackException extends RuntimeException {
    private InstructionStack instructionStack;

    public AssertInstructionStackException(InstructionStack instructionStack, Exception error) {
      super(error.getMessage());
      setStackTrace(error.getStackTrace());
      this.instructionStack = instructionStack;
    }

    public String toString() {
      return instructionStack.dump(this);
    }
  }
}
