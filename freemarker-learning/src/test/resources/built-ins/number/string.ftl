<@test name='converts a number to a string'>
  <@assert expected='123456789' actual=123456789?c/>
  <@assert expected='123,456,789' actual=123456789?string/>
</@test>

<@test name='number'>
  <@assert expected='123,456,789' actual=123456789?string.number/>
</@test>

<@test name='currency'>
  <@assert expected='$123,456,789.00' actual=123456789?string.currency/>
</@test>

<@test name='percent'>
  <@assert expected='12,345,678,900%' actual=123456789?string.percent/>
</@test>

<@test name='computer'>
  <@assert expected='123456789' actual=123456789?string.computer/>
</@test>