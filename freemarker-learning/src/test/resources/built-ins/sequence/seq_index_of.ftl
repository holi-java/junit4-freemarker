<@test name='returns the index of the first occurrence of a value in the sequence'>
  <#assign foo='foo'/>
  <#assign bar='bar'/>
  <#assign unknown='.'/>
  <#assign list=[foo,bar,foo]/>

  <@assert expected=0 actual=list?seq_index_of(foo) />
  <@assert expected=-1 actual=list?seq_index_of(unknown) />
</@test>
