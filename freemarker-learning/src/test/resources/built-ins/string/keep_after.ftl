<@test name="removes the part of the string that is not after the first occurrence of the given substring">
  <#assign foo='fooz'>

  <@assert expected='' actual=foo?keep_after(foo)/>
  <@assert expected='' actual=foo?keep_after('x')/>
  <@assert expected='' actual=foo?keep_after('z')/>
  <@assert expected='oz' actual=foo?keep_after('o')/>
</@test>
