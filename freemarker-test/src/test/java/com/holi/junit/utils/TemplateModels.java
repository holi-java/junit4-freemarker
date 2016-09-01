package com.holi.junit.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.template.MapKeyValuePairIterator;
import freemarker.template.ObjectWrapper;
import freemarker.template.SimpleCollection;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateHashModelEx2;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateScalarModel;
import freemarker.template.TemplateSequenceModel;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * Created by selonj on 16-9-1.
 */
public class TemplateModels {
  public static TemplateModel scalar(final String value) {
    return new TemplateScalarModel() {
      @Override public String getAsString() throws TemplateModelException {
        return value;
      }
    };
  }

  public static TemplateModel booleanValue(final boolean value) {
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

  public static TemplateModel date(final Date date) {
    return new TemplateDateModel() {
      @Override public Date getAsDate() throws TemplateModelException {
        return date;
      }

      @Override public int getDateType() {
        return 0;
      }
    };
  }

  public static TemplateModel number(final Number number) {
    return new TemplateNumberModel() {
      @Override public Number getAsNumber() throws TemplateModelException {
        return number;
      }
    };
  }

  public static TemplateHashModelEx2 map(final Map map, final BeansWrapper wrapper) {
    return new TemplateHashModelEx2() {
      @Override public KeyValuePairIterator keyValuePairIterator() throws TemplateModelException {
        return new MapKeyValuePairIterator(map, wrapper);
      }

      @Override public int size() throws TemplateModelException {
        return map.size();
      }

      @Override public TemplateCollectionModel keys() throws TemplateModelException {
        return collection(map.keySet(), wrapper);
      }

      @Override public TemplateCollectionModel values() throws TemplateModelException {
        return collection(map.values(), wrapper);
      }

      @Override public TemplateModel get(String key) throws TemplateModelException {
        return wrapper.wrap(map.get(key));
      }

      @Override public boolean isEmpty() throws TemplateModelException {
        return map.isEmpty();
      }
    };
  }

  public static TemplateCollectionModel collection(Collection collection, BeansWrapper wrapper) {
    return new SimpleCollection(collection, wrapper);
  }
}
