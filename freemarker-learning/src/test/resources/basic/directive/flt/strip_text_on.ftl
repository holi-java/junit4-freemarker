<#ftl output_format='HTML' strip_text=true>

<@test name="text block">
  <@assert expected=' & '> & </@assert>
</@test>

<@test name="expression">
  <@assert expected=' &amp; '>${' & '}</@assert>
</@test>

<@test name="escape">
  <@assert expected=' &amp; '>${' & '?esc}</@assert>
</@test>