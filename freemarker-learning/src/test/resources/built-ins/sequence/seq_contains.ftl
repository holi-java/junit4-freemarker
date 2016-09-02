<@test name='checking the sequence contains the specified value'>
  <#assign foo='foo'/>
  <#assign bar='bar'/>
  <#assign unknown='.'/>
  <#assign list=[foo,bar,foo]/>

  <@assert expected=list?seq_contains(foo)/>
  <@assert expected=!list?seq_contains(unknown)/>
</@test>
