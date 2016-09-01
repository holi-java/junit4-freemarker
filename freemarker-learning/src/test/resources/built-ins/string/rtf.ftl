<@test name="escapes the string as Rich text">
  <@assert expected='a' actual='a'?rtf/>

  <@assert expected='\\\\' actual='\\'?rtf/>
  <@assert expected='\\{' actual='{'?rtf/>
  <@assert expected='\\}' actual='}'?rtf/>

  <@assert expected='[' actual='['?rtf/>
  <@assert expected=']' actual=']'?rtf/>
</@test>
