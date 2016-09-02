<@test name='arithmetic'>
  <@assert expected=3 actual='1+2'?eval/>
</@test>

<@test name='string concat'>
  <@assert expected="12" actual='"1"+2'?eval/>
</@test>
