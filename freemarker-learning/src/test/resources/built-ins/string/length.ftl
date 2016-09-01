<@test name="returns the length of string">
  <@assert expected=0 actual=''?length/>
  <@assert expected=3 actual='foo'?length/>
</@test>
