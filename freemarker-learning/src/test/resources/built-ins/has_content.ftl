<#assign foo='foo'>
<#assign list=[foo]>
<#assign map={foo:foo}>

<@test name="null">
  <@assert expected=!null?has_content/>
</@test>

<@test name="string">
  <@assert expected=foo?has_content/>
  <@assert expected=!""?has_content/>
</@test>

<@test name="sequence">
  <@assert expected=list?has_content/>
  <@assert expected=![]?has_content/>
</@test>

<@test name="hash">
  <@assert expected=map?has_content/>
  <@assert expected=!{}?has_content/>
</@test>

<@test name="number">
  <@assert expected=1?has_content/>
  <@assert expected=0?has_content/>
</@test>

<@test name="boolean">
  <@assert expected=true?has_content/>
  <@assert expected=false?has_content/>
</@test>

<@test name="range">
  <@assert expected=(1..2)?has_content/>
  <@assert expected=!(1..<1)?has_content/>
</@test>