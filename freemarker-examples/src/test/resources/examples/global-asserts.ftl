<#include "_foo.ftl">

<#--assert expected value equals to actual value-->
<@assert expected='bar' actual=foo/>

<#-- assert expected value as boolean expression-->
<@assert expected=true/>

<#-- assert expected value equals to body-->
<@assert expected=foo>bar</@assert>

<@assert expected='1, 2, 3'>
  <#compress>
    <#list [1,2,3] as item>${item}<sep>, </#list>
  </#compress>
</@assert>