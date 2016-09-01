<@test name="removes the parameter substring from the beginning of the string, or returns the original string if it doesn't start with the parameter substring">
  <#assign foo='foo'>

  <@assert expected='' actual=foo?remove_beginning(foo)/>
  <@assert expected='oo' actual=foo?remove_beginning('f')/>
  <@assert expected='foo' actual=foo?remove_beginning('o')/>
</@test>
