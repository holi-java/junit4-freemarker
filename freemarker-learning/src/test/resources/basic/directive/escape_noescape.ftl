<#assign _title='java & freemarker'>
<#assign html_title='java &amp; freemarker'>
<#assign book={'title':_title}>


<@test name="escape">
  <@assert expected=html_title>
    <#compress>
      <#escape book as book?html>${book.title}</#escape>
    </#compress>
  </@assert>
</@test>

<@test name="escape when variable not defined">
  <@assert expected=html_title>
    <#compress>
      <#escape book as book?html>${_title}</#escape>
    </#compress>
  </@assert>
</@test>

<@test name="noescape in escapse block">
  <@assert expected=_title>
    <#compress>
      <#escape book as book?html>
        <#noescape>${_title}</#noescape>
      </#escape>
    </#compress>
  </@assert>
</@test>

