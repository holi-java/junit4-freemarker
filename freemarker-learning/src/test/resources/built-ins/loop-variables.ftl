<#assign foo='foo'/>
<#assign bar='bar'/>
<#assign list=[foo,bar]/>

<@test name='counter'>
  <@assert expected='1,2'>
    <#compress>
      <#list list as item>${item?counter}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>

<@test name='index'>
  <@assert expected='0,1'>
    <#compress>
      <#list list as item>${item?index}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>


<@test name='is_first'>
  <@assert expected='true,false'>
    <#compress>
      <#list list as item>${item?is_first?c}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>

<@test name='has_next'>
  <@assert expected='true,false'>
    <#compress>
      <#list list as item>${item?has_next?c}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>


<@test name='is_even_item'>
  <@assert expected='false,true'>
    <#compress>
      <#list list as item>${item?is_even_item?c}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>

<@test name='is_odd_item'>
  <@assert expected='true,false'>
    <#compress>
      <#list list as item>${item?is_odd_item?c}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>


<@test name='item_parity'>
  <@assert expected='odd,even'>
    <#compress>
      <#list list as item>${item?item_parity}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>

<@test name='item_parity_cap'>
  <@assert expected='Odd,Even'>
    <#compress>
      <#list list as item>${item?item_parity_cap}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>


<@test name='item_cycle'>
  <#assign list=1..6>
  <@assert expected='r,g,b,r,g,b'>
    <#compress>
      <#list list as item>${item?item_cycle('r','g','b')}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>


<@test name='list?size < item_cycle?size'>
  <#assign list=1..2>
  <@assert expected='r,g'>
    <#compress>
      <#list list as item>${item?item_cycle('r','g','b')}<#sep>,</#list>
    </#compress>
  </@assert>
</@test>

<@test name='throw exception with empty item_cycle' expected='java.lang.Exception'>
  <#list list as item>${item?item_cycle()}<#sep>,</#list>
</@test>