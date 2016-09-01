<@test name="number">
  <@assert expected='1' actual=1?string/>
</@test>

<@test name="boolean">
  <@assert expected='true' actual=true?string/>
  <@assert expected='yes' actual=true?string('yes','no')/>
  <@assert expected='no' actual=false?string('yes','no')/>
</@test>

<@test name="date">
  <#assign now=.now/>

  <@assert expected=now?string>${now}</@assert>
</@test>

<@test name="not supports for range" expected='java.lang.Exception'>
${(1..3)?string}
</@test>

<@test name="not supports for list" expected='java.lang.Exception'>
${[1,2,3]?string}
</@test>

<@test name="not supports for map" expected='java.lang.Exception'>
${{"foo":"bar"}?string}
</@test>