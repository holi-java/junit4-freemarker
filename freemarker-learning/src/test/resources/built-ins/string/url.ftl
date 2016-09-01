<@test name="throws exception if the output encoding or the URL encoding charset are not spcified" expected='freemarker.template.TemplateException'>
${"foo bar"?url}
</@test>

<@test name="all non-US-ASCII and reserved URL characters will be escaped with %XX by output encoding">
  <#setting output_encoding='UTF-8'>
  <@assert expected="http%3A%2F%2Fexample.com%2Ffoo%20bar" actual="http://example.com/foo bar"?url/>
</@test>

<@test name="all non-US-ASCII and reserved URL characters will be escaped with %XX by url escaping charset">
  <#setting url_escaping_charset='UTF-8'>
  <@assert expected="http%3A%2F%2Fexample.com%2Ffoo%20bar" actual="http://example.com/foo bar"?url/>
</@test>
