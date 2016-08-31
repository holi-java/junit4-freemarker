package com.holi.junit.utils;

import freemarker.core.Environment;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.utility.NullWriter;

/**
 * Created by selonj on 16-9-1.
 */
public class Environments {
  public static Environment as(String name, String sourceCode) {
    Configuration configuration = new Configuration(Configuration.VERSION_2_3_25);
    configuration.setLogTemplateExceptions(false);
    try {
      return new Template(name, sourceCode, configuration).createProcessingEnvironment(null, NullWriter.INSTANCE);
    } catch (Exception error) {
      throw new Error(error);
    }
  }
}
