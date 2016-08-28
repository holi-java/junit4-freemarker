# JUnit4 FreeMarker Test Framework


## Test Syntax

```ftl
<@context name="something">
  <@test name="test some feature">
    <@assert that="var">value</@assert>
    <@assert>${var=='value'}</@assert>
    <@assert expected="java.lang.Exception">${invalid_expression}</@assert>
  </@test>
</@context> 
```