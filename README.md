# JUnit4 FreeMarker Test Framework


## Test Syntax

```ftl
<@context name="something">
  <@test name="test some feature">
    <#assign foo="bar">
    
    <@assert expected='bar' actual=foo/>
    <@assert expected=foo=='bar'/>
    <@assert expected=foo>bar</@assert>
  </@test>
</@context> 
```