<@test name="number to date">
  <@assert expected=1?number_to_date?is_date/>
</@test>

<@test name="number to datetime">
  <@assert expected=1?number_to_datetime?is_datetime/>
</@test>

<@test name="number to time">
  <@assert expected=1?number_to_time?is_time/>
</@test>