<@test name="removes the parameter substring from the ending of the string, or returns the original string if it doesn't ends with the parameter substring">
  <#assign foo='foo'>

  <@assert expected='' actual=foo?remove_ending(foo)/>
  <@assert expected='f' actual=foo?remove_ending('oo')/>
  <@assert expected='foo' actual=foo?remove_ending('fo')/>
</@test>
