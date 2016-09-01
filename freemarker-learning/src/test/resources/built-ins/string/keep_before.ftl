<@test name="removes the part of the string that starts with the given substring">
  <#assign foo='fooz'>

  <@assert expected='' actual=foo?keep_before(foo)/>
  <@assert expected='fooz' actual=foo?keep_before('x')/>
  <@assert expected='foo' actual=foo?keep_before('z')/>
  <@assert expected='f' actual=foo?keep_before('o')/>
</@test>
