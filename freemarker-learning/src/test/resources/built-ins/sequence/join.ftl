<@test name='concatenates the items of a sequence to a single string, with the given separator'>
  <@assert expected='foo' actual=['foo']?join(',')/>
  <@assert expected='foo,bar' actual=['foo','bar']?join(',')/>
</@test>


<@test name='join(separator,empty)'>
  <@assert expected='foo' actual=['foo']?join(',','(empty)')/>
  <@assert expected='(empty)' actual=[]?join(',','(empty)')/>
</@test>

<@test name='join(separator,empty,ending)'>
  <@assert expected='foo.' actual=['foo']?join(',','*','.')/>
  <@assert expected='foo,bar.' actual=['foo','bar']?join(',','*','.')/>
  <@assert expected='*' actual=[]?join(',','*','.')/>
</@test>