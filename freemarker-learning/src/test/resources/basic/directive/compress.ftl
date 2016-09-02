<@test name="removing superfluous white-spaces">
  <#assign expression>
    foo
    bar
  </#assign>

  <#assign compressed>
    <#compress>
    ${expression}
    </#compress>
  </#assign>

  <@assert expected=expression!='foo\nbar'/>
  <@assert expected=compressed=='foo\nbar'/>
</@test>




