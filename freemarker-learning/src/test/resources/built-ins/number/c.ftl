<@test name='This built-in converts a number to string for a "computer language" '>
  <@assert expected='123456789' actual=123456789?c/>
  <@assert expected='123,456,789' actual=123456789?string/>
</@test>