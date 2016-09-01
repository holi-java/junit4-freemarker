<@test name="capitalize the first word">
  <@assert expected="Foo bar" actual="foo bar"?cap_first/>
</@test>

<@test name="capitalize the first word ignore whitespace">
  <@assert expected=" Foo bar" actual=" foo bar"?cap_first/>
</@test>