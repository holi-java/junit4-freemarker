<@test name="cast string to a date">
  <#assign date='2016-09-01 08:00:00'?date('yyyy-MM-dd HH:mm:ss')>

  <@assert expected=date?is_date/>
  <@assert expected=!date?is_datetime/>
  <@assert expected=!date?is_time/>
</@test>

<@test name="cast string to a time">
  <#assign time='08:00:00'?time('HH:mm:ss')>

  <@assert expected=time?is_date/>
  <@assert expected=!time?is_datetime/>
  <@assert expected=time?is_time/>
</@test>


<@test name="cast string to a datetime">
  <#assign time='08:00:00'?datetime('HH:mm:ss')>

  <@assert expected=time?is_date/>
  <@assert expected=time?is_datetime/>
  <@assert expected=!time?is_time/>
</@test>
