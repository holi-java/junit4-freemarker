<@test name="access matched result with the groups">
  <#assign foo='java 18'?matches(r'(\w+)\s+(\d+)')>

  <@assert expected='java 18,java,18'>
    <#compress>
    ${foo?groups[0]},${foo?groups[1]},${foo?groups[2]}
    </#compress>
  </@assert>
</@test>
