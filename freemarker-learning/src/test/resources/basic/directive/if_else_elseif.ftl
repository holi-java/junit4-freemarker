<@test name="if">
  <@assert expected='foo'><#if true>foo</#if></@assert>
  <@assert expected=''><#if false>foo</#if></@assert>
</@test>

<@test name="if-else">
  <@assert expected='foo'><#if true>foo<#else>bar</#if></@assert>
  <@assert expected='bar'><#if false>foo<#else>bar</#if></@assert>
</@test>


<@test name="if-elseif">
  <#assign foo='foo'/>
  <@assert expected='foo'><#if foo=='foo'>foo<#elseif foo=='bar'>bar</#if></@assert>

  <#assign foo='bar'/>
  <@assert expected='bar'><#if foo=='foo'>foo<#elseif foo=='bar'>bar</#if></@assert>
</@test>


<@test name="if-elseif-else">
  <#assign foo='foo'/>
  <@assert expected='foo'><#if foo=='foo'>foo<#elseif foo=='bar'>bar<#else>other</#if></@assert>

  <#assign foo='bar'/>
  <@assert expected='bar'><#if foo=='foo'>foo<#elseif foo=='bar'>bar<#else>other</#if></@assert>

  <#assign foo='mismatched'/>
  <@assert expected='other'><#if foo=='foo'>foo<#elseif foo=='bar'>bar<#else>other</#if></@assert>
</@test>

