<#assign format='yyyy-MM-dd HH:mm:ss' />
<#setting locale='en_US'>
<#setting time_zone='Asia/Chongqing'>
<#setting date_format=format />
<#setting datetime_format=format />

<#assign foo='2016-09-01 06:03:59'/>
<#assign date=foo?date />
<#assign datetime=foo?datetime/>

<@test name='precondition'>
  <@assert expected=foo actual=date?string/>
  <@assert expected=foo actual=datetime?string/>
</@test>

<@test name='iso'>
  <@assert expected='2016-08-31' actual=date?iso('utc')/>
  <@assert expected='2016-08-31T22:03:59Z' actual=datetime?iso('utc')/>
</@test>

<@test name='iso_utc'>
  <@assert expected='2016-08-31' actual=date?iso_utc/>
  <@assert expected='2016-08-31T22:03:59Z' actual=datetime?iso_utc/>
</@test>

<@test name='iso_utc_h'>
  <@assert expected='2016-08-31' actual=date?iso_utc_h/>
  <@assert expected='2016-08-31T22Z' actual=datetime?iso_utc_h/>
</@test>

<@test name='iso_utc_h_nz'>
  <@assert expected='2016-08-31' actual=date?iso_utc_h_nz/>
  <@assert expected='2016-08-31T22' actual=datetime?iso_utc_h_nz/>
</@test>

<@test name='iso_utc_m'>
  <@assert expected='2016-08-31' actual=date?iso_utc_m/>
  <@assert expected='2016-08-31T22:03Z' actual=datetime?iso_utc_m/>
</@test>

<@test name='iso_utc_m_nz'>
  <@assert expected='2016-08-31' actual=date?iso_utc_m_nz/>
  <@assert expected='2016-08-31T22:03' actual=datetime?iso_utc_m_nz/>
</@test>

<@test name='iso_utc_ms'>
  <@assert expected='2016-08-31' actual=date?iso_utc_ms/>
  <@assert expected='2016-08-31T22:03:59Z' actual=datetime?iso_utc_ms/>
</@test>

<@test name='iso_utc_ms_nz'>
  <@assert expected='2016-08-31' actual=date?iso_utc_ms_nz/>
  <@assert expected='2016-08-31T22:03:59' actual=datetime?iso_utc_ms_nz/>
</@test>
