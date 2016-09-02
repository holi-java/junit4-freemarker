<@test name="assign global variable">
  <#global foo='foo'/>

  <@assert expected='foo' actual=foo/>
  <@assert expected='foo' actual=.globals.foo/>
</@test>

