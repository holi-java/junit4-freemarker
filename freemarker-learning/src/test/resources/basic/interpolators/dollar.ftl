<@test name="string">
  <@assert expected='bar'>${'bar'}</@assert>
</@test>

<@test name="cast number to string">
  <@assert expected='1'>${1}</@assert>
</@test>

<@test name="cast date to string">
${'2016-09-01'?date('yyyy-MM-dd')}
</@test>

<@test name="cann't cast boolean to string" expected='java.lang.Exception'>
${true}
</@test>

<@test name="cann't cast range to string" expected='java.lang.Exception'>
${1..2}
</@test>

<@test name="cann't cast sequence to string" expected='java.lang.Exception'>
<#assign list=[1,2,3]/>
${list}
</@test>

<@test name="cann't cast sequence to string" expected='java.lang.Exception'>
<#assign map={'foo':'bar'}/>
${map}
</@test>

<@test name="throw exception if variable not exists" expected='java.lang.Exception'>
${missingValue}
</@test>