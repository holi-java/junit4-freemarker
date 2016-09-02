package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.blocks.Expectation;
import com.holi.junit.freemarker.blocks.Expectation.ExpectationType;
import com.holi.junit.freemarker.blocks.ExpectationBuilder;
import com.holi.junit.freemarker.blocks.JUnitBlock;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.ASSERTION;
import static com.holi.junit.freemarker.blocks.Expectation.ExpectationType.EXCEPTION;
import static com.holi.junit.freemarker.expectation.context.ExpectationContexts.createExpectationContext;
import static com.holi.junit.freemarker.expectation.factory.ExpectationFactories.createBodyEqualityExpectationFactory;
import static com.holi.junit.freemarker.expectation.factory.ExpectationFactories.createBodyWithExpectedExpectationFactory;
import static com.holi.junit.freemarker.expectation.factory.ExpectationFactories.createBodyWithNoExceptionExpectationFactory;
import static com.holi.junit.freemarker.expectation.factory.ExpectationFactories.createEqualityExpectationFactory;
import static com.holi.junit.freemarker.expectation.factory.ExpectationFactories.createPredicationExpectationFactory;

/**
 * Created by selonj on 16-8-30.
 */
public class FreeMarkerExpectationBuilder implements ExpectationBuilder {
  private Map<ExpectationType, List<ExpectationFactory>> registry = new HashMap<>();

  public FreeMarkerExpectationBuilder() {
    add(ASSERTION, createEqualityExpectationFactory());
    add(ASSERTION, createPredicationExpectationFactory());
    add(ASSERTION, createBodyEqualityExpectationFactory());
    add(EXCEPTION, createBodyWithNoExceptionExpectationFactory());
    add(EXCEPTION, createBodyWithExpectedExpectationFactory());
  }



  private void add(ExpectationType type, ExpectationFactory factory) {
    List<ExpectationFactory> factories = registry.get(type);
    if (factories == null) factories = new ArrayList<>();
    factories.add(factory);
    registry.put(type, factories);
  }

  @Override public Expectation create(JUnitBlock block, final Map params, TemplateDirectiveBody body) throws TemplateException {
    return factory(block.getExpectationType(), specification(params, body)).create(createExpectationContext(params, body));
  }

  private ExpectationFactory factory(ExpectationType type, ExpectationSpecification specification) {
    for (ExpectationFactory candidate : factories(type)) {
      if (candidate.accept(specification)) return candidate;
    }
    return displayHelpMessage(type);
  }

  private ExpectationFactory displayHelpMessage(final ExpectationType type) {
    return new ExpectationFactory() {
      @Override public void describeTo(StringBuilder out) {

      }

      @Override public boolean accept(ExpectationSpecification specification) {
        return true;
      }

      @Override public Expectation create(ExpectationContext context) {
        throw new IllegalArgumentException(helpMessage());
      }

      private String helpMessage() {
        return help().append(header()).append(allValidForms()).toString();
      }

      private StringBuilder help() {
        return new StringBuilder();
      }

      private CharSequence header() {
        return "You can use the following <@" + type.blockName() + "> forms:\n";
      }

      private CharSequence allValidForms() {
        StringBuilder forms = new StringBuilder();
        for (ExpectationFactory factory : factories(type)) {
          factory.describeTo(forms);
          forms.append("\n");
        }
        return forms;
      }
    };
  }

  private List<ExpectationFactory> factories(ExpectationType type) {
    List<ExpectationFactory> result = registry.get(type);
    return result == null ? Collections.<ExpectationFactory>emptyList() : result;
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
}
