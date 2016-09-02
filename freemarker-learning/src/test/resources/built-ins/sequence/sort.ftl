<#assign foo='foo'/>
<#assign bar='bar'/>
<#assign unknown='.'/>
<#assign list=[foo,bar]/>

<@test name='returns the sequence sorted in ascending order. '>
  <@assert expected=[bar,foo] actual=list?sort/>
  <@assert expected=[] actual=[]?sort/>
</@test>


