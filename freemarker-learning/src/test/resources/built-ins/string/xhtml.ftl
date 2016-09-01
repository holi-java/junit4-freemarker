<@test name="escapes the value with xhtml format">
  <@assert expected='&lt;' actual='<'?xhtml/>
  <@assert expected='&gt;' actual='>'?xhtml/>
  <@assert expected='&amp;' actual='&'?xhtml/>
  <@assert expected='&quot;' actual='"'?xhtml/>
  <@assert expected='&#39;' actual="'"?xhtml/>
  <@assert expected=' ' actual=' '?xhtml/>
</@test>
