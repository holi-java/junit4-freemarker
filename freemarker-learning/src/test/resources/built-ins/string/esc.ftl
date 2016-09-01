<#ftl output_format='HTML'>

<@test name="escapes the value with the current output format">
  <#assign foo='a&b'>

  <@assert expected='a&amp;b' actual=foo?esc?markup_string/>
</@test>
