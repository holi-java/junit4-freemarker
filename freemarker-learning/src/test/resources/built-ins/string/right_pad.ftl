<#assign foo='foo'>

<@test name="inserts spaces on the end of the string until it reaches the length">
  <@assert expected='foo  ' actual=foo?right_pad(5)/>
  <@assert expected='foo' actual=foo?right_pad(1)/>
  <@assert expected='foo' actual=foo?right_pad(-1)/>
</@test>

<@test name="inserts special sequence on the end of the string until it reaches the length">
  <@assert expected='foo21' actual=foo?right_pad(5,'12')/>
  <@assert expected='foo' actual=foo?right_pad(3,'12')/>
  <@assert expected='foo41' actual=foo?right_pad(5,'1234')/>
</@test>

<@test name="right pad special sequence can't be empty when string length less than the length" expected='freemarker.template.TemplateException'>
${'any'?right_pad(5,'')}
</@test>

<@test name="right pad special sequence can't be empty when string length larger than or equal to the length">
${'any'?right_pad(3,'')}
</@test>