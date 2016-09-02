<@test name='returns the size of the sequence'>
  <#assign foo='foo'/>
  <#assign bar='bar'/>
  <#assign unknown='.'/>
  <#assign list=[foo,bar]/>

  <@assert expected=2 actual=list?size/>
  <@assert expected=0 actual=[]?size/>
</@test>
