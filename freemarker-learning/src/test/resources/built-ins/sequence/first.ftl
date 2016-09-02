<@test name='the first subvariable of the sequence'>
  <@assert expected='foo' actual=['foo','bar']?first/>
  <@assert expected=null actual=[]?first/>
</@test>
