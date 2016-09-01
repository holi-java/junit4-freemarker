<@test name="escapes the string with the escaping rules of Java language string literals">
  <#assign string='"foo":\'bar\''>

  <@assert expected='\\"foo\\":\'bar\'' actual=string?j_string/>
</@test>
