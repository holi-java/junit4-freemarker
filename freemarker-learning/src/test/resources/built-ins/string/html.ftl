<@test name="escapes the value with html format">
  <@assert expected='&lt;' actual='<'?html/>
  <@assert expected='&gt;' actual='>'?html/>
  <@assert expected='&amp;' actual='&'?html/>
  <@assert expected='&quot;' actual='"'?html/>
  <@assert expected='&#39;' actual="'"?html/>
  <@assert expected=' ' actual=' '?html/>
</@test>
