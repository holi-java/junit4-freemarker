<@test name='case insensitive'>
  <#assign foo='foO'/>

  <@assert expected='faO' actual=foo?replace('o','a')/>
  <@assert expected='faa' actual=foo?replace('o','a','i')/>
</@test>