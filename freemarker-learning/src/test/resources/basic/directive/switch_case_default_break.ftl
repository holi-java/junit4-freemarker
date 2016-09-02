<#assign foo='foo'>
<#assign bar='bar'>
<#assign other='other'>

<@test name='empty switch'>
  <#assign value=foo>

  <@assert expected=''>
    <#compress>
      <#switch value></#switch>
    </#compress>
  </@assert>
</@test>


<@test name='switch-case'>
  <#assign value=foo>
  <@assert expected='bar'>
    <#compress>
      <#switch value>
        <#case foo>${bar}
      </#switch>
    </#compress>
  </@assert>

  <#assign value=bar>
  <@assert expected=''>
    <#compress>
      <#switch value>
        <#case foo>${bar}
      </#switch>
    </#compress>
  </@assert>
</@test>


<@test name='switch-cases'>
  <#assign value=foo>
  <@assert expected=bar+other>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t>
        <#case bar>${other}<#t>
      </#switch>
    </#compress>
  </@assert>

  <#assign value=bar>
  <@assert expected=other>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t>
        <#case bar>${other}<#t>
      </#switch>
    </#compress>
  </@assert>
</@test>


<@test name='switch-case-break'>
  <#assign value=foo>
  <@assert expected=bar>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t><#break>
        <#case bar>${other}<#t><#break>
      </#switch>
    </#compress>
  </@assert>
</@test>


<@test name='switch-default'>
  <#assign value=foo>

  <@assert expected=other>
    <#compress>
      <#switch value><#default>${other}</#switch>
    </#compress>
  </@assert>
</@test>


<@test name='switch-case-default'>
  <#assign value=foo>
  <@assert expected=bar+other>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t>
        <#default>${other}<#t>
      </#switch>
    </#compress>
  </@assert>

  <#assign value=bar>
  <@assert expected=other>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t>
        <#default>${other}<#t>
      </#switch>
    </#compress>
  </@assert>
</@test>

<@test name='switch-case-break-default'>
  <#assign value=foo>
  <@assert expected=bar>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t><#break>
        <#default>${other}<#t>
      </#switch>
    </#compress>
  </@assert>

  <#assign value=bar>
  <@assert expected=other>
    <#compress>
      <#switch value>
        <#case foo>${bar}<#t><#break>
        <#default>${other}<#t>
      </#switch>
    </#compress>
  </@assert>
</@test>

<@test name='switch-default-case-break'>
  <#assign value=foo>
  <@assert expected=bar>
    <#compress>
      <#switch value>
        <#default>${other}<#t>
        <#case foo>${bar}<#t><#break>
      </#switch>
    </#compress>
  </@assert>

  <#assign value=bar>
  <@assert expected=other>
    <#compress>
      <#switch value>
        <#default>${other}<#t>
        <#case foo>${bar}<#t><#break>
      </#switch>
    </#compress>
  </@assert>
</@test>