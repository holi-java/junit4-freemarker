<@test name="removes the part of the string that is not after the last occurrence of the given substring">
  <#assign foo='fooz'>

  <@assert expected='' actual=foo?keep_after_last(foo)/>
  <@assert expected='' actual=foo?keep_after_last('x')/>
  <@assert expected='' actual=foo?keep_after_last('z')/>
  <@assert expected='z' actual=foo?keep_after_last('o')/>
</@test>
