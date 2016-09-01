<@test name='rounds the number upwards '>
  <@assert expected=1 actual=0.5?ceiling/>
  <@assert expected=1 actual=0.3?ceiling/>
  <@assert expected=12 actual=12?ceiling/>
</@test>