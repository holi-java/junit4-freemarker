<@test name='no trim'>

  <@assert expected='foo\nbar'>
  <#compress>
    foo<#nt>
    bar
  </#compress>
  </@assert>
</@test>