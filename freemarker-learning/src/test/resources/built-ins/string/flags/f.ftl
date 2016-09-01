<@test name='first only'>
  <#assign foo='foo'/>

  <@assert expected='faa' actual=foo?replace('o','a')/>
  <@assert expected='fao' actual=foo?replace('o','a','f')/>
</@test>