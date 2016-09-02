<@test name="parses a string as an FTL template, and returns an user-defined directive that executes that template">
  <#assign template=r'${1+2}'?interpret>

  <@assert expected='3'><@template/></@assert>
</@test>