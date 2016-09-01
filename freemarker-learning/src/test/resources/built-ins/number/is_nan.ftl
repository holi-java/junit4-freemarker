<@test name='checking a number is NaN '>
  <@assert expected=false actual=1?is_nan/>
  <@assert expected=true actual='NaN'?number?is_nan/>
  <@assert expected='NaN'?number actual='NaN'?number/>
  <@assert expected=false actual='INF'?number?is_nan/>
  <@assert expected=false actual='-INF'?number?is_nan/>
</@test>