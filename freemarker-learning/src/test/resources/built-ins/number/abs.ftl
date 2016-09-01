<@test name='return the absolute value of a number'>
  <@assert expected=1 actual=1?abs/>
  <@assert expected=1 actual=(-1)?abs/>
  <@assert expected=0 actual=(-0)?abs/>
  <@assert expected=0 actual=0?abs/>
</@test>