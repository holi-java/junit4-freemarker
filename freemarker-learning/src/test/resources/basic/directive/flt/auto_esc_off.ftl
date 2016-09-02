<#ftl output_format='HTML' auto_esc=false>

<@test name="text block">
  <@assert expected='&'>&</@assert>
</@test>

<@test name="expression">
  <@assert expected='&'>${'&'}</@assert>
</@test>

<@test name="escape">
  <@assert expected='&amp;'>${'&'?esc}</@assert>
</@test>