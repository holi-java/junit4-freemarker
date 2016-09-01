<@test name="check string ends with substring">
  <#assign foo='foo'>

  <@assert expected=foo?ends_with(foo)/>
  <@assert expected=foo?ends_with('oo')/>
  <@assert expected=!foo?ends_with('fo')/>
</@test>
