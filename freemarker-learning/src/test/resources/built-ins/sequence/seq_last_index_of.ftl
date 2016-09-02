<@test name='returns the index of the last occurrence of a value in the sequence'>
  <#assign foo='foo'/>
  <#assign bar='bar'/>
  <#assign unknown='.'/>
  <#assign list=[foo,bar,foo]/>

  <@assert expected=2 actual=list?seq_last_index_of(foo) />
  <@assert expected=-1 actual=list?seq_index_of(unknown) />
</@test>
