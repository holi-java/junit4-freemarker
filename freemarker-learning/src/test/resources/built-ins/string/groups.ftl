<@test name="access matched result with the groups">
  <#assign foo='java 18'?matches(r'(\w+)\s+(\d+)')>

  <@assert expected=foo/>
  <@assert expected='java 18' actual=foo?groups[0]/>
  <@assert expected='java' actual=foo?groups[1]/>
  <@assert expected='18' actual=foo?groups[2]/>
</@test>
