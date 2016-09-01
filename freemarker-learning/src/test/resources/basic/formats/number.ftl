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

<#list ['x','@','*'] as other>
  <@test name=other>
    <@assert expected=other+1 actual=x?string[other]/>
    <@assert expected=other+other+1 actual=x?string[other+other]/>
    <@assert expected='1.'+other+other actual=x?string['.'+other+other]/>
  </@test>
</#list>

<@test name='format by settings'>
  <@assert expected='1.234' actual=x?string/>
</@test>

<@test name='format by settings'>
  <#setting number_format='000.##'/>
  <@assert expected='001.23' actual=x?string/>
</@test>

