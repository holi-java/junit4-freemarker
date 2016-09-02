<#ftl output_format='HTML' auto_esc=false>

<@test name="turns on auto-escaping in the nested section">
  <@assert expected='&'>&</@assert>
  <@assert expected='&amp;'><#autoesc>${'&'}</#autoesc></@assert>
</@test>

<@test name="not auto-escaping text block">
  <@assert expected='&'><#autoesc>&</#autoesc></@assert>
</@test>