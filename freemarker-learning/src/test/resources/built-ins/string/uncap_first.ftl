<@test name="un-capitalize the first word">
  <@assert expected="fOO BAR" actual="FOO BAR"?uncap_first/>
</@test>

<@test name="un-capitalize the first word ignore whitespace">
  <@assert expected=" fOO BAR" actual=" FOO BAR"?uncap_first/>
</@test>