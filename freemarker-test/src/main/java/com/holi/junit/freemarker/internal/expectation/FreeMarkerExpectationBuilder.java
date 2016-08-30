package com.holi.junit.freemarker.internal.expectation;

import com.holi.junit.freemarker.Expectation;
import com.holi.junit.freemarker.ExpectationBuilder;
import com.holi.junit.freemarker.ExpectationContext;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.holi.junit.freemarker.internal.expectation.context.ExpectationContexts.createExpectationContext;
import static com.holi.junit.freemarker.internal.expectation.factory.ExpectationFactories.createBodyEqualityExpectationFactory;
import static com.holi.junit.freemarker.internal.expectation.factory.ExpectationFactories.createEqualityExpectationFactory;
import static com.holi.junit.freemarker.internal.expectation.factory.ExpectationFactories.createPredicationExpectationFactory;

/**
 * Created by selonj on 16-8-30.
 */
public class FreeMarkerExpectationBuilder implements ExpectationBuilder {
  private List<ExpectationFactory> factories = new ArrayList<>();
  private ExpectationFactory mismatchedFactory = new DisplayHelpMessage();

  public FreeMarkerExpectationBuilder() {
    add(createEqualityExpectationFactory());
    add(createPredicationExpectationFactory());
    add(createBodyEqualityExpectationFactory());
  }

  private void add(ExpectationFactory factory) {
    factories.add(factory);
  }

  @Override public Expectation create(final Map params, TemplateDirectiveBody body) throws TemplateException {
    return getFactory(specification(params, body)).create(createExpectationContext(params, body));
  }

  private ExpectationFactory getFactory(ExpectationSpecification specification) {
    for (ExpectationFactory candidate : factories) {
      if (candidate.accept(specification)) return candidate;
    }
    return mismatchedFactory;
  }

  private String helpMessage() {
    return help().append(header()).append(allAvailableForms()).toString();
  }

  private StringBuilder help() {
    return new StringBuilder();
  }

  private CharSequence header() {
    return "You can use the following <@assert> forms:\n";
  }

  private CharSequence allAvailableForms() {
    StringBuilder forms = new StringBuilder();
    for (ExpectationFactory factory : factories) {
      factory.describeTo(forms);
      forms.append("\n");
    }
    return forms;
  }

  private ExpectationSpecification specification(final Map params, final TemplateDirectiveBody body) {
    return new ExpectationSpecification() {
      @Override public boolean hasExpectedValue() {
        return has(ExpectationContext.PARAM_EXPECTED);
      }

      @Override public boolean hasActualValue() {
        return has(ExpectationContext.PARAM_ACTUAL);
      }

      private boolean has(String name) {
        return params.containsKey(name);
      }

      @Override public boolean hasBody() {
        return body != null;
      }
    };
  }

  private class DisplayHelpMessage implements ExpectationFactory {
    @Override public void describeTo(StringBuilder out) {

    }

    @Override public boolean accept(ExpectationSpecification specification) {
      return true;
    }

    @Override public Expectation create(ExpectationContext context) {
      throw new IllegalArgumentException(helpMessage());
    }
  }
}
