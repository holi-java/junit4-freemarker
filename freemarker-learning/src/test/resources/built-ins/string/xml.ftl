<@test name="escapes the value with xml format">
  <@assert expected='&lt;' actual='<'?xml/>
  <@assert expected='&gt;' actual='>'?xml/>
  <@assert expected='&amp;' actual='&'?xml/>
  <@assert expected='&quot;' actual='"'?xml/>
  <@assert expected='&apos;' actual="'"?xml/>
  <@assert expected=' ' actual=' '?xml/>
</@test>
