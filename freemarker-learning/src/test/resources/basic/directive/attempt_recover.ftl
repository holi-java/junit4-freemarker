<@test name="recover block never be executed if the execution of the attempt block successful">
  <#assign foo='bar'>
  <@assert expected='bar'>
    <#compress>
      <#attempt>${foo}<#recover>null</#attempt>
    </#compress>
  </@assert>
</@test>

<@test name="recover block be executed if an error occurs during the execution of the attempt block">
  <@assert expected='nil'>
    <#compress>
      <#attempt>${foo}<#recover>nil</#attempt>
    </#compress>
  </@assert>
</@test>