<#--variables in tests are not shared but <#global> variables.-->
<@test name='first'>
  <#assign foo='bar'/>
  <#global global='value'/>

  <@assert expected='bar' actual=foo/>
  <@assert expected='value' actual=global/>
</@test>

<@test name='last'>
  <@assert expected=null actual=foo/>
  <@assert expected='value' actual=global/>
</@test>