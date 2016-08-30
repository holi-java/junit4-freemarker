package com.holi.junit.freemarker.expectation.context;

import com.holi.junit.freemarker.ExpectationContext;
import freemarker.ext.util.WrapperTemplateModel;
import freemarker.template.SimpleScalar;
import freemarker.template.TemplateBooleanModel;
import freemarker.template.TemplateCollectionModel;
import freemarker.template.TemplateDateModel;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateException;
import freemarker.template.TemplateHashModelEx;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.TemplateModelIterator;
import freemarker.template.TemplateNumberModel;
import freemarker.template.TemplateSequenceModel;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by selonj on 16-8-30.
 */
class EqualityContext implements ExpectationContext {
  private final TemplateDirectiveBody body;
  private final Map params;

  public EqualityContext(Map params, TemplateDirectiveBody body) {
    this.params = params;
    this.body = body;
  }

  @Override public String body() throws IOException, TemplateException {
    StringWriter out = new StringWriter();
    eval(out);
    return out.toString();
  }

  private void eval(Writer out) throws TemplateException, IOException {
    body.render(out);
  }

  public Object actualValue() throws TemplateModelException {
    return get(PARAM_ACTUAL);
  }

  public Object expectedValue() throws TemplateModelException {
    return get(PARAM_EXPECTED);
  }

  private Object get(Object name) throws TemplateModelException {
    return unwrap(params.get(name));
  }

  private Object unwrap(Object value) throws TemplateModelException {
    if (value == null) return null;
    if (value instanceof WrapperTemplateModel) return ((WrapperTemplateModel) value).getWrappedObject();
    if (value instanceof SimpleScalar) return ((SimpleScalar) value).getAsString();
    if (value instanceof TemplateBooleanModel) return ((TemplateBooleanModel) value).getAsBoolean();
    if (value instanceof TemplateDateModel) return ((TemplateDateModel) value).getAsDate();
    if (value instanceof TemplateNumberModel) return ((TemplateNumberModel) value).getAsNumber().toString();
    if (value instanceof TemplateCollectionModel) return toList(((TemplateCollectionModel) value).iterator());
    if (value instanceof TemplateSequenceModel) return toList(iterator((TemplateSequenceModel) value));
    if (value instanceof TemplateHashModelEx) return toMap((TemplateHashModelEx) value);
    throw new IllegalArgumentException(value.getClass().getName() + " value not support for assert !");
  }

  private Map toMap(TemplateHashModelEx hash) throws TemplateModelException {
    HashMap map = new HashMap();
    TemplateModelIterator keys = hash.keys().iterator();
    TemplateModelIterator values = hash.values().iterator();
    while (keys.hasNext()) map.put(unwrap(keys.next()), unwrap(values.next()));
    return map;
  }

  private TemplateModelIterator iterator(final TemplateSequenceModel sequence) {
    return new TemplateModelIterator() {
      private int index;

      @Override public TemplateModel next() throws TemplateModelException {
        return sequence.get(index++);
      }

      @Override public boolean hasNext() throws TemplateModelException {
        return index < sequence.size();
      }
    };
  }

  private List<Object> toList(TemplateModelIterator it) throws TemplateModelException {
    ArrayList<Object> list = new ArrayList<>();
    while (it.hasNext()) list.add(unwrap(it.next()));
    return list;
  }

  public boolean hasActualValue() {
    return params.containsKey(PARAM_ACTUAL);
  }
}
