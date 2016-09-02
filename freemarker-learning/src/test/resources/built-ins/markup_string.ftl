<#ftl output_format='HTML'>
<@test name="returns the markup stored inside a markup output value as string">
  <@assert expected="&amp;" actual="&"?esc?markup_string/>
</@test>