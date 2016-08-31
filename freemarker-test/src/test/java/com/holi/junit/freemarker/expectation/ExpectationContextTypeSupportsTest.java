package com.holi.junit.freemarker.expectation;

import com.holi.junit.freemarker.expectation.context.ExpectationContexts;
import com.holi.junit.utils.Variable;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateModel;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

import static com.holi.junit.utils.Variables.expectedNullValue;
import static com.holi.junit.utils.Variables.expectedValue;
import static com.holi.junit.utils.Variables.with;
import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

/**
 * Created by selonj on 16-8-30.
 */
public class ExpectationContextTypeSupportsTest {

  private static final TemplateDirectiveBody UNUSED_BODY = null;

  @Test public void supportsNull() throws Exception {
    assertThat(context(expectedNullValue()).expectedValue(), is(nullValue()));
  }

  @Test public void supportsScalarModel() throws Exception {
    assertThat(context(expectedValue("foo")).expectedValue(), equalTo((Object) "foo"));
  }

  @Test public void supportsBooleanModel() throws Exception {
    assertThat(context(expectedValue(true)).expectedValue(), equalTo((Object) true));
  }

  @Test public void supportsDateModel() throws Exception {
    Date date = new Date();
    assertThat(context(expectedValue(date)).expectedValue(), equalTo((Object) date));
  }

  @Test public void supportsNumberModel() throws Exception {
    assertThat(context(expectedValue(1.2)).expectedValue(), equalTo((Object) BigDecimal.valueOf(1.2)));
  }

  @Test public void supportsCollectionModel() throws Exception {
    List<Object> list = asList((Object) 1, "foo", new Date(), true);
    assertThat(context(expectedValue(list)).expectedValue(), equalTo((Object) list));
  }

  @Test public void supportsMapModel() throws Exception {
    Map map = new HashMap() {{
      put("string", "foo");
      put("number", 1);
      put("date", new Date());
      put("boolean", true);
      put("collection", asList(1, 2, 3));
    }};

    assertThat(context(expectedValue(map)).expectedValue(), equalTo((Object) map));
  }

  @Test public void supportsSequenceModel() throws Exception {
    Object[] array = {"foo", new Date(), true};
    assertThat(context(expectedValue(array)).expectedValue(), equalTo((Object) asList(array)));
  }

  @Test(expected = IllegalArgumentException.class) public void throwsIllegalArgumentExceptionIfTypeNotSupports() throws Exception {
    context(expectedValue(unsupportedValue())).expectedValue();
  }

  private TemplateModel unsupportedValue() {
    return new TemplateModel() {
    };
  }

  private ExpectationContext context(Variable variable) {
    return ExpectationContexts.createExpectationContext(with(variable), UNUSED_BODY);
  }
}