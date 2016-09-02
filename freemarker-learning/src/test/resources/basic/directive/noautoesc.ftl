<#ftl output_format='HTML'>

<@test name='does not auto escape expression as html format'>
  <@assert expected='&amp;'>${'&'}</@assert>
  <@assert expected='&'><#noautoesc>${'&'}</#noautoesc></@assert>
</@test>