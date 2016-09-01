<#ftl output_format='HTML'>

<@test name="makes escapes the value with the current output format disabled">
  <#assign foo='a&b'>

  <@assert expected='a&amp;b'>${foo}</@assert>
  <@assert expected='a&b'>${foo?no_esc}</@assert>
</@test>
