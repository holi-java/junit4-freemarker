<@test name='local variables only shared in directive'>
  <#macro testLocal>
    <#local foo='bar'/>

    <@assert expected='bar' actual=foo/>
  </#macro>

  <@testLocal />

  <@assert expected=!foo??/>
</@test>