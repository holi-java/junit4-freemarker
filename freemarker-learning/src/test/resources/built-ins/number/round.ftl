<@test name='rounds to the nearest whole number'>
  <@assert expected=1 actual=0.5?round/>
  <@assert expected=0 actual=0.3?round/>
  <@assert expected=12 actual=12?round/>
</@test>