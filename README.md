# JUnit4 FreeMarker Test Framework


## Test Syntax

```ftl
<@test name="test something">
  <@assert that="var">value</@assert>
  <@assert>${var=='value'}</@assert>
  <@assert expected="java.lang.Exception">${exception.message=='error message'}</@assert>
</@test>
```