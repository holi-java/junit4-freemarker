<@test name="check string contains substring">
  <#assign foo='foo'>

  <@assert expected=foo?contains(foo)/>
  <@assert expected=foo?contains('oo')/>
  <@assert expected=!foo?contains('xo')/>
</@test>
