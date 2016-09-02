<@test name='difine a macro'>
  <#macro foo>bar</#macro>

  <@assert expected=foo?is_directive/>
  <@assert expected='bar'><@foo/></@assert>
</@test>

<@test name='macro-return'>
  <#macro foo>foo<#return>bar</#macro>

  <@assert expected='foo'><@foo/></@assert>
</@test>

<@test name='macro with default params'>
  <#macro foo value='foo'>${value}</#macro>

  <@assert expected='foo'><@foo/></@assert>
  <@assert expected='bar'><@foo value='bar'/></@assert>
</@test>


<@test name='pass named parameters into macro with a variable number of positional parameters'>
  <#macro list items...>
    <@assert expected=items?is_hash/>
  </#macro>

  <@list items=1..3/>
</@test>

<@test name='pass sequence as parameter into macro with a variable number of positional parameters'>
  <#macro list items...>
    <@assert expected=items?is_sequence/>
    <@assert expected=3 actual=items?size/>
  </#macro>

  <@list 1,2,3/>
</@test>

<@test name='render body by <#nested>'>
  <#macro out><#nested></#macro>
  <#assign foo='foo'>

  <@assert expected=foo><@out>foo</@out></@assert>
  <@assert expected=foo><@out>${foo}</@out></@assert>
  <@assert expected=''><@out/></@assert>
</@test>
