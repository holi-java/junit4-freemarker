<@test name='rounds the number downwards '>
  <@assert expected=0 actual=0.5?floor/>
  <@assert expected=0 actual=0.3?floor/>
  <@assert expected=12 actual=12?floor/>
</@test>