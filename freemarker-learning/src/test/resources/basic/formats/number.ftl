<#assign x = 1.234>

<@test name='0'>
  <@assert expected='1' actual=x?string['0']/>
  <@assert expected='01' actual=x?string['00']/>
  <@assert expected='1.' actual=x?string['0.']/>
  <@assert expected='1.23' actual=x?string['0.00']/>
  <@assert expected='1.2340' actual=x?string['0.0000']/>
</@test>

<@test name='#'>
  <@assert expected='1' actual=x?string['#']/>
  <@assert expected='1' actual=x?string['##']/>
  <@assert expected='1.' actual=x?string['#.']/>
  <@assert expected='1.23' actual=x?string['#.##']/>
  <@assert expected='1.234' actual=x?string['#.####']/>
</@test>

<@test name='others'>
  <@assert expected='@1' actual=x?string['@']/>
  <@assert expected='@@1' actual=x?string['@@']/>
  <@assert expected='@1.' actual=x?string['@.']/>
  <@assert expected='@1.@@' actual=x?string['@.@@']/>
  <@assert expected='@1.@@@' actual=x?string['@.@@@']/>
</@test>

<@test name='format by custom format settings'>
  <#setting number_format='000.##'/>
  <@assert expected='001.23' actual=x?string/>
</@test>

<@test name='format by default number format'>
  <@assert expected='1.234' actual=x?string/>
</@test>

<@test name="extended Java decimal format">
  <@assert expected='10_003'
  actual=10002.5?string[",000;; roundingMode=halfUp groupingSeparator=_"]/>
</@test>
