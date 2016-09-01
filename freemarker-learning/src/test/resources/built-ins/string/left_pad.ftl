<#assign foo='foo'>

<@test name="inserts spaces on the beginning of the string until it reaches the length">
  <@assert expected='  foo' actual=foo?left_pad(5)/>
  <@assert expected='foo' actual=foo?left_pad(1)/>
  <@assert expected='foo' actual=foo?left_pad(-1)/>
</@test>

<@test name="inserts special sequence on the beginning of the string until it reaches the length">
  <@assert expected='12foo' actual=foo?left_pad(5,'12')/>
  <@assert expected='foo' actual=foo?left_pad(3,'12')/>
  <@assert expected='12foo' actual=foo?left_pad(5,'1234')/>
</@test>

<@test name="left pad special sequence can't be empty when string length less than the length" expected='freemarker.template.TemplateException'>
${'any'?left_pad(5,'')}
</@test>

<@test name="left pad special sequence can't be empty when string length larger than or equal to the length">
${'any'?left_pad(3,'')}
</@test>