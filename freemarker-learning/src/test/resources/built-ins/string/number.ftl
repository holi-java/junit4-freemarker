<@test name="convert string to a number">
  <@assert expected=1 actual='1'?number/>
  <@assert expected=1.2 actual='1.2'?number/>
  <@assert expected=-1 actual='-1'?number/>
  <@assert expected=123 actual='1.23E2'?number/>
</@test>


<@test name="sepecial values">
  <@assert expected='NaN'?number?is_number/>
  <@assert expected='INF'?number?is_number/>
  <@assert expected='-INF'?number?is_number/>
  <@assert expected='Infinity'?number?is_number/>
  <@assert expected='-Infinity'?number?is_number/>
</@test>


<@test name="throws exception with invalid number format" expected='freemarker.template.TemplateException'>
${'NAN'?number}
</@test>