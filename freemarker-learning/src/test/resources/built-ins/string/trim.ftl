<@test name="returns the string without leading and trailing white-space">
  <@assert expected='foo' actual=' foo'?trim/>
  <@assert expected='foo' actual='  foo'?trim/>
  <@assert expected='foo' actual='foo '?trim/>
  <@assert expected='foo' actual='foo  '?trim/>
  <@assert expected='foo' actual='  foo  '?trim/>
</@test>