<@test 'convert boolean to string if comstom boolean format set'>
  <#setting boolean_format='yes,no'>
  <@assert expected='true' actual=true?c/>
  <@assert expected='false' actual=false?c/>
</@test>

<@test 'convert boolean to string'>
  <@assert expected='true' actual=true?c/>
  <@assert expected='false' actual=false?c/>
</@test>