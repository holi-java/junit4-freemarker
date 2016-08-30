package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.Expectation;
import com.holi.junit.freemarker.ExpectationBuilder;
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
  private Environment env;

  public InstructionStackExpectationBuilder(ExpectationBuilder expectations, Environment env) {
    this.expectations = expectations;
    this.env = env;
  }

  @Override public Expectation create(final Map params, final TemplateDirectiveBody body) throws TemplateException {
    return new Expectation() {
      @Override public void checking() throws TemplateException, IOException {
        try {
          expectations.create(params, body).checking();
        } catch (AssertionError error) {
          throw new AssertInstructionStackError(instructionStack(), error);
        } catch (RuntimeException error) {
          throw new AssertInstructionStackException(instructionStack(), error);
        }
      }
    };
  }

  private InstructionStack instructionStack() {
    InstructionStack stack = new InstructionStack() {
      private String instructionStack = null;

      @Override public String dump(Throwable exception) {
        String message = exception.getLocalizedMessage();
        return message == null ? dump() : (message + "\n" + dump());
      }

      @Override public String dump() {
        if (instructionStack != null) return instructionStack;
        return instructionStack = dumpInstructionStack();
      }

      private String dumpInstructionStack() {
        TemplateElement[] stack = _CoreAPI.getInstructionStackSnapshot(env);
        StringWriter buff = new StringWriter();
        _CoreAPI.outputInstructionStack(stack, false, buff);
        return buff.toString();
      }
    };
    stack.dump();
    return stack;
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
