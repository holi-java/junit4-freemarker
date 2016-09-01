<@test name="append the substring if string not ends with the substring">
  <#assign foo='foo'>

  <@assert expected='foo' actual=foo?ensure_ends_with(foo)/>
  <@assert expected='foooz' actual=foo?ensure_ends_with('oz')/>
</@test>
