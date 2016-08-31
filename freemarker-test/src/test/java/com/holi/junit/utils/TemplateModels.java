package com.holi.junit.utils;

import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateSequenceModel;
import java.util.Date;

/**
 * Created by selonj on 16-9-1.
 */
public class TemplateModels {
  public static TemplateModel scalar(String value) {
    return new SimpleScalar(value);
  }

  public static TemplateModel booleanModel(final boolean value) {
    return new TemplateBooleanModel() {
      @Override public boolean getAsBoolean() throws TemplateModelException {
        return value;
      }
    };
  }

  public static TemplateModel sequence(final Object[] array, final ObjectWrapper wrapper) {
    return new TemplateSequenceModel() {
      @Override public TemplateModel get(int index) throws TemplateModelException {
        return wrapper.wrap(array[index]);
      }

      @Override public int size() throws TemplateModelException {
        return array.length;
      }
    };
  }

  public static TemplateModel dateModel(final Date date) {
    return new TemplateDateModel() {
      @Override public Date getAsDate() throws TemplateModelException {
        return date;
      }

      @Override public int getDateType() {
        return 0;
      }
    };
  }

  public static TemplateModel numberModel(final Number number) {
    return new TemplateNumberModel() {
      @Override public Number getAsNumber() throws TemplateModelException {
        return number;
      }
    };
  }
}
