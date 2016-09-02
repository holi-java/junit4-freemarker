<#ftl output_format='HTML' auto_esc=true >

<@test name="is_string">
  <@assert expected=""?is_string/>
  <@assert expected=!1?is_string/>
  <@assert expected=![]?is_string/>
</@test>

<@test name="throws exception when is_string operator is null" expected=$exception>
${null?is_string}
</@test>


<@test name="is_number">
  <@assert expected=!""?is_number/>
  <@assert expected=1?is_number/>
  <@assert expected=![]?is_number/>
</@test>

<@test name="throws exception when is_number operator is null" expected=$exception>
${null?is_number}
</@test>


<@test name="is_boolean">
  <@assert expected=true?is_boolean/>
  <@assert expected=!'true'?is_boolean/>
  <@assert expected=![]?is_boolean/>
</@test>

<@test name="throws exception when is_boolean operator is null" expected=$exception>
${null?is_boolean}
</@test>

<#assign date_format='yyyy-MM-dd HH:mm:ss'>
<#setting date_format=date_format>
<#setting datetime_format=date_format>

<#assign foo='2016-09-01 08:03:59'>
<#assign date='2016-09-01'?date('yyyy-MM-dd')>
<#assign datetime=foo?datetime(date_format)>
<#assign time='08:03:59'?time('HH:mm:ss')>

<@test name="is_date">
  <@assert expected=date?is_date/>
  <@assert expected=datetime?is_date/>
  <@assert expected=time?is_date/>
  <@assert expected=!foo?is_date/>
</@test>

<@test name="is_date_like">
  <@assert expected=date?is_date_like/>
  <@assert expected=datetime?is_date_like/>
  <@assert expected=time?is_date_like/>
  <@assert expected=!foo?is_date_like/>
</@test>

<@test name="is_date_only">
  <@assert expected=date?is_date_only/>
  <@assert expected=!datetime?is_date_only/>
  <@assert expected=!time?is_date_only/>
  <@assert expected=!foo?is_date_only/>
</@test>

<@test name="is_time">
  <@assert expected=!date?is_time/>
  <@assert expected=!datetime?is_time/>
  <@assert expected=time?is_time/>
  <@assert expected=!foo?is_time/>
</@test>

<@test name="is_datetime">
  <@assert expected=!date?is_datetime/>
  <@assert expected=datetime?is_datetime/>
  <@assert expected=!time?is_datetime/>
  <@assert expected=!foo?is_datetime/>
</@test>

<@test name="is_unknown_date_like">
  <@assert expected=!date?is_unknown_date_like/>
  <@assert expected=!datetime?is_unknown_date_like/>
  <@assert expected=!time?is_unknown_date_like/>
  <@assert expected=!foo?is_unknown_date_like/>
</@test>

<@test name="is_method">
  <@assert expected='java.lang.Throwable' actual=$exception.getName()/>
  <@assert expected=$exception.getName?is_method/>
  <@assert expected=!$exception.name?is_method/>
</@test>

<@test name="is_hash">
  <@assert expected={}?is_hash/>
  <@assert expected=![]?is_hash/>
</@test>

<@test name="is_hash_ex">
  <@assert expected={}?is_hash_ex/>
  <@assert expected=![]?is_hash_ex/>
</@test>

<@test name="is_sequence">
  <@assert expected=[]?is_sequence/>
  <@assert expected=(1..3)?is_sequence/>
</@test>

<@test name="is_collection">
  <@assert expected=!{}?is_collection/>
  <@assert expected=![]?is_collection/>
  <@assert expected=!(1..3)?is_collection/>
</@test>

<@test name="is_collection_ex">
  <@assert expected=!{}?is_collection_ex/>
  <@assert expected=![]?is_collection_ex/>
  <@assert expected=!(1..3)?is_collection_ex/>
</@test>

<@test name="is_enumerable">
  <@assert expected=!{}?is_enumerable/>
  <@assert expected=[]?is_enumerable/>
  <@assert expected=(1..3)?is_enumerable/>
</@test>

<@test name="is_indexable">
  <@assert expected=!{}?is_indexable/>
  <@assert expected=[]?is_indexable/>
  <@assert expected=(1..3)?is_indexable/>
</@test>

<@test name="is_directive">
  <@assert expected=test?is_directive/>
  <@assert expected=assert?is_directive/>
  <@assert expected=!""?is_directive/>
</@test>

<@test name="is_markup_output">
  <@assert expected=!""?is_markup_output/>
  <@assert expected=""?esc?is_markup_output/>
  <@assert expected=!""?esc?markup_string?is_markup_output/>
</@test>