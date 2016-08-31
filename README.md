# JUnit4 FreeMarker Test Framework


## Test Syntax

```ftl
<@test name="test some feature">
  <#assign foo="bar">
  
  <@assert expected='bar' actual=foo/>
  <@assert expected=foo=='bar'/>
  <@assert expected=foo>bar</@assert>
</@test>

<@test name="matching test will failed with exception" expected="java.lang.Exception">${invalid_expression}</@test>
```