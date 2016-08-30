package com.holi.junit.freemarker.expectation;

import freemarker.ext.beans.BeanModel;
import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.beans.SimpleMapModel;
import freemarker.template.Configuration;
import freemarker.template.SimpleCollection;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateSequenceModel;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
public class Variables {
  public static Map with(Variable... variables) {
    HashMap result = new HashMap();
    for (Variable variable : variables) {
      result.put(variable.name, variable.value);
    }
    return result;
  }

  public static Variable actualValue(String value) {
    return Variable.valueOf("actual", scalar(value));
  }

  public static Variable expectedValue(boolean value) {
    return expectedValue(booleanModel(value));
  }

  public static Variable expectedValue(String value) {
    return expectedValue(scalar(value));
  }

  public static Variable expectedValue(Date date) {
    return expectedValue(dateModel(date));
  }

  public static Variable expectedValue(Number number) {
    return expectedValue(numberModel(number));
  }

  public static Variable expectedValue(List list) {
    return expectedValue(new SimpleCollection(list, wrapper()));
  }

  public static Variable expectedValue(Object[] array) {
    return expectedValue(sequenceModel(array));
  }

  public static Variable expectedValue(Map map) {
    return expectedValue(new SimpleMapModel(map, wrapper()));
  }

  private static BeansWrapper wrapper() {
    BeansWrapperBuilder configuration = new BeansWrapperBuilder(Configuration.VERSION_2_3_25);
    configuration.setSimpleMapWrapper(false);
    return configuration.build();
  }

  public static Variable expectedValue(TemplateModel value) {
    return Variable.valueOf("expected", value);
  }

  public static Variable expectedNullValue() {
    return expectedValue((TemplateModel) null);
  }

  private static TemplateModel scalar(String value) {
    return new SimpleScalar(value);
  }

  private static TemplateModel booleanModel(final boolean value) {
    return new TemplateBooleanModel() {
      @Override public boolean getAsBoolean() throws TemplateModelException {
        return value;
      }
    };
  }

  private static TemplateModel sequenceModel(final Object[] array) {
    return new TemplateSequenceModel() {
      @Override public TemplateModel get(int index) throws TemplateModelException {
        Object value = array[index];
        if (value instanceof TemplateModel) return (TemplateModel) value;
        if (value instanceof String) {
          return scalar((String) value);
        }
        if (value instanceof Boolean || boolean.class.isInstance(value)) {
          return booleanModel((Boolean) value);
        }
        if (value instanceof Date) {
          return dateModel((Date) value);
        }
        if (value instanceof Number) {
          return numberModel((Number) value);
        }
        return new BeanModel(value, wrapper());
      }

      @Override public int size() throws TemplateModelException {
        return array.length;
      }
    };
  }

  private static TemplateModel dateModel(final Date date) {
    return new TemplateDateModel() {
      @Override public Date getAsDate() throws TemplateModelException {
        return date;
      }

      @Override public int getDateType() {
        return 0;
      }
    };
  }

  private static TemplateModel numberModel(final Number number) {
    return new TemplateNumberModel() {
      @Override public Number getAsNumber() throws TemplateModelException {
        return number;
      }
    };
  }
}
