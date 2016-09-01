<@test name="escapes the string with the escaping rules of json language string literals">
  <#assign string='"foo":\'bar\''>

  <@assert expected='\\"foo\\":\'bar\'' actual=string?json_string/>
</@test>
