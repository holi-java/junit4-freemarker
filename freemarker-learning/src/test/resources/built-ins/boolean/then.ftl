<#assign foo='foo'/>
<#assign bar='bar'/>


<@test name='like java operator ?:, e.g:foo?"foo":"bar"'>
  <@assert expected=foo actual=true?then(foo,bar)/>
  <@assert expected=bar actual=false?then(foo,bar)/>
</@test>

<@test name='throw exception if operator is null' expected='java.lang.Exception'>
${null?then(foo,bar)}
</@test>
