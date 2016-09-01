<@test 'convert boolean to string if comstom boolean format set'>
  <#setting boolean_format='yes,no'>
  <@assert expected='yes' actual=true?string/>
  <@assert expected='no' actual=false?string/>
</@test>

<@test 'convert boolean to string'>
  <@assert expected='true' actual=true?string/>
  <@assert expected='false' actual=false?string/>
</@test>


<@test 'convert boolean to string(TRUE,FALSE)'>
  <@assert expected='true' actual=true?string/>
  <@assert expected='YES' actual=true?string('YES','NO')/>
  <@assert expected='NO' actual=false?string('YES','NO')/>
</@test>