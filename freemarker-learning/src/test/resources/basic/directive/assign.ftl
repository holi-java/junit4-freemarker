<@test name="assign variable">
  <#assign foo='foo'/>
  <@assert expected='foo' actual=foo/>
</@test>

<@test name="assign multi variables">
  <#assign foo='foo' bar='bar'/>
  <@assert expected='foo' actual=foo/>
  <@assert expected='bar' actual=bar/>
</@test>

<@test name="assign increment variable">
  <#assign x=1 />
  <#assign x++ />

  <@assert expected=2 actual=x/>
</@test>

<@test name="assign multi-variables with increment variable">
  <#assign x=1 />
  <#assign foo='bar' x++ />

  <@assert expected=2 actual=x/>
  <@assert expected='bar' actual=foo/>
</@test>

<@test name="assign deincrement variable">
  <#assign x=1 />
  <#assign x-- />

  <@assert expected=0 actual=x/>
</@test>

<@test name="assign variable to other namespace">
  <#import "_lib.ftl" as my/>
  <@assert expected='long' actual=my.date_format/>

  <#assign date_format='short' in my />

  <@assert expected='short' actual=my.date_format/>
</@test>

<@test name="assign variable with body">
  <#assign bar='bar'>
  <#assign foo>${bar}</#assign>

  <@assert expected='bar' actual=foo/>

  <#assign bar='barz'>
  <@assert expected='bar' actual=foo/>
</@test>

