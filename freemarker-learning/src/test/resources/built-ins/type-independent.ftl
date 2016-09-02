<#assign foo='foo'>
<#assign bar='bar'>
<#assign other='other'>
<#assign default='.'>


<@test name='switch'>
  <@assert expected=other actual=foo?switch(foo,other)/>
</@test>

<@test name='throws exception when mismatched' expected='java.lang.Exception'>
${bar?switch(foo,other)}
</@test>

<@test name='using default when switch mismatched'>
  <@assert expected=other actual=foo?switch(foo,other,default)/>
  <@assert expected=default actual=bar?switch(foo,other,default)/>
</@test>