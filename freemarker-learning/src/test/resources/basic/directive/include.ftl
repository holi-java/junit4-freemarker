<@test name='include share variables'>
  <@assert expected=!foo??/>

  <#include '_lib.ftl' />
  <@assert expected=foo??/>
  <@assert expected='bar' actual=foo/>
</@test>

<@test name='included template will be parsed'>
  <#assign foo='bar'/>
  <@assert expected='bar'><#include '_foo.ftl'/></@assert>
</@test>