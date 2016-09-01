# JUnit4 FreeMarker Test Framework
> a test framework for freemarker, you can use this project for learning freemark step by step.

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