<#assign format='yyyy-MM-dd HH:mm:ss' />
<#setting date_format=format />
<#setting datetime_format=format />
<#setting locale='en_US'>
<#setting time_zone='Asia/Chongqing'>

<#assign foo='2016-09-01 06:03:59'/>
<#assign date=foo?date />
<#assign datetime=foo?datetime/>

<@test name='precondition'>
  <@assert expected=foo actual=date?string/>
  <@assert expected=foo actual=datetime?string/>
</@test>

<@test name="date?string">
  <@assert expected=foo actual=date?string/>
  <@assert expected=foo actual=datetime?string/>
</@test>

<@test name="date?string.full">
  <@assert expected='Thursday, September 1, 2016' actual=date?string.full/>
  <@assert expected='Thursday, September 1, 2016 6:03:59 AM CST' actual=datetime?string.full/>
</@test>

<@test name="date?string.full_full">
  <@assert expected='Thursday, September 1, 2016' actual=date?string.full_full/>
  <@assert expected='Thursday, September 1, 2016 6:03:59 AM CST' actual=datetime?string.full_full/>
</@test>

<@test name="date?string.full_long">
  <@assert expected='Thursday, September 1, 2016' actual=date?string.full_long/>
  <@assert expected='Thursday, September 1, 2016 6:03:59 AM CST' actual=datetime?string.full_long/>
</@test>

<@test name="date?string.full_medium">
  <@assert expected='Thursday, September 1, 2016' actual=date?string.full_medium/>
  <@assert expected='Thursday, September 1, 2016 6:03:59 AM' actual=datetime?string.full_medium/>
</@test>

<@test name="date?string.full_short">
  <@assert expected='Thursday, September 1, 2016' actual=date?string.full_short/>
  <@assert expected='Thursday, September 1, 2016 6:03 AM' actual=datetime?string.full_short/>
</@test>

<@test name="date?string.iso">
  <@assert expected='2016-09-01' actual=date?string.iso/>
  <@assert expected='2016-09-01T06:03:59+08:00' actual=datetime?string.iso/>
</@test>

<@test name="date?string.iso_h_u">
  <@assert expected='2016-08-31' actual=date?string.iso_h_u/>
  <@assert expected='2016-08-31T22Z' actual=datetime?string.iso_h_u/>
</@test>

<@test name="date?string.long">
  <@assert expected='September 1, 2016' actual=date?string.long/>
  <@assert expected='September 1, 2016 6:03:59 AM CST' actual=datetime?string.long/>
</@test>

<@test name="date?string.long_long">
  <@assert expected='September 1, 2016' actual=date?string.long_long/>
  <@assert expected='September 1, 2016 6:03:59 AM CST' actual=datetime?string.long_long/>
</@test>

<@test name="date?string.long_full">
  <@assert expected='September 1, 2016' actual=date?string.long_full/>
  <@assert expected='September 1, 2016 6:03:59 AM CST' actual=datetime?string.long_full/>
</@test>

<@test name="date?string.long_medium">
  <@assert expected='September 1, 2016' actual=date?string.long_medium/>
  <@assert expected='September 1, 2016 6:03:59 AM' actual=datetime?string.long_medium/>
</@test>

<@test name="date?string.long_short">
  <@assert expected='September 1, 2016' actual=date?string.long_short/>
  <@assert expected='September 1, 2016 6:03 AM' actual=datetime?string.long_short/>
</@test>

<@test name="date?string.xs">
  <@assert expected='2016-09-01+08:00' actual=date?string.xs/>
  <@assert expected='2016-09-01T06:03:59+08:00' actual=datetime?string.xs/>
</@test>
