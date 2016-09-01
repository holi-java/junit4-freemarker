<@test name='checking a number is floating point infinite '>
  <@assert expected=false actual=1?is_infinite/>
  <@assert expected=false actual='NaN'?number?is_infinite/>
  <@assert expected=true actual='INF'?number?is_infinite/>
  <@assert expected=true actual='-INF'?number?is_infinite/>
</@test>