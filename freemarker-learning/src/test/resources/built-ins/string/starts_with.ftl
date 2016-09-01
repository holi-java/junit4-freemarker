<@test name="check string start with substring">
  <#assign foo='foo'>

  <@assert expected=foo?starts_with(foo)/>
  <@assert expected=foo?starts_with('fo')/>
  <@assert expected=!foo?starts_with('oo')/>
</@test>
