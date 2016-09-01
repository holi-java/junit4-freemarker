<@test name="keeps the part before the last occurrence of substring">
  <#assign foo='fooz'>

  <@assert expected='' actual=foo?keep_before_last(foo)/>
  <@assert expected='fooz' actual=foo?keep_before_last('x')/>
  <@assert expected='foo' actual=foo?keep_before_last('z')/>
  <@assert expected='fo' actual=foo?keep_before_last('o')/>
</@test>
