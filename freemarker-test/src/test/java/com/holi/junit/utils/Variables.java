package com.holi.junit.utils;

import freemarker.ext.beans.BeansWrapper;
import freemarker.ext.beans.BeansWrapperBuilder;
import freemarker.ext.beans.SimpleMapModel;
import freemarker.template.Configuration;
import freemarker.template.SimpleCollection;
import freemarker.template.TemplateModel;
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
    return Variable.valueOf("actual", TemplateModels.scalar(value));
  }

  public static Variable expectedValue(boolean value) {
    return expectedValue(TemplateModels.booleanModel(value));
  }

  public static Variable expectedValue(String value) {
    return expectedValue(TemplateModels.scalar(value));
  }

  public static Variable expectedValue(Date date) {
    return expectedValue(TemplateModels.dateModel(date));
  }

  public static Variable expectedValue(Number number) {
    return expectedValue(TemplateModels.numberModel(number));
  }

  public static Variable expectedValue(List list) {
    return expectedValue(new SimpleCollection(list, wrapper()));
  }

  public static Variable expectedValue(Object[] array) {
    return expectedValue(TemplateModels.sequence(array, wrapper()));
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

  public static Variable testName(String testName) {
    return testName(TemplateModels.scalar(testName));
  }

  public static Variable testNameMissing() {
    return testName((TemplateModel) null);
  }

  private static Variable testName(TemplateModel testName) {
    return Variable.valueOf("name", testName);
  }
}
