<@test name="escapes the string with the escaping rules of javascript language string literals">
  <#assign string='"foo":\'bar\''>

  <@assert expected='\\"foo\\":\\\'bar\\\'' actual=string?js_string/>
</@test>
