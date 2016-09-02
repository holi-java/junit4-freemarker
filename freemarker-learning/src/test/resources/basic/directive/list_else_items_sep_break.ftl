<@test name="list">
  <#assign list=1..3>

  <@assert expected='1,2,3,'>
    <#compress>
      <#list list as item>
        ${item},<#t>
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list a map">
  <#assign map={'foo':'bar','key':'value'}>

  <@assert expected='foo,key,'>
    <#compress>
      <#list map as key,value>
      ${key},<#t>
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list-sep adds a separator except the last element">
  <#assign list=1..3>

  <@assert expected='1,2,3'>
    <#compress>
      <#list list as item>
        ${item}<#t>
        <#sep>,<#t>
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list-break to exit the list block">
  <#assign list=1..3>

  <@assert expected='1,'>
    <#compress>
      <#list list as item>
        ${item},
        <#break>
      </#list>
    </#compress>
  </@assert>
</@test>


<@test name="list-else with a list has elements">
  <#assign list=1..3>

  <@assert expected='1,2,3,'>
    <#compress>
      <#list list as item><#t>
        ${item},<#t>
        <#else>no items<#t>
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list-else with an empty list">
  <#assign list=[]>

  <@assert expected='no items'>
    <#compress>
      <#list list as item>
        ${item},
        <#else>no items
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list-items print (or do) something before the first list item, and after the last list item">
  <#assign list=1..3>

  <@assert expected='[1,2,3,]'>
    <#compress>
      <#list list>
        [<#t>
        <#items as item>${item},<#t></#items><#t>
        ]<#t>
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list-items does not print the before & after if with an empty list">
  <#assign list=[]>

  <@assert expected=''>
    <#compress>
      <#list list>
        [
        <#items as item>${item},</#items>
        ]
      </#list>
    </#compress>
  </@assert>
</@test>

<@test name="list-items-else with an empty list">
  <#assign list=[]>

  <@assert expected='no items'>
    <#compress>
      <#list list>
        [
        <#items as item>${item},</#items>
        ]
      <#else>no items
      </#list>
    </#compress>
  </@assert>
</@test>