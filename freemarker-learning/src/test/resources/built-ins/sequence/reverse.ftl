<@test name='the first subvariable of the sequence'>
  <@assert expected='bar' actual=['foo','bar']?last/>
  <@assert expected=null actual=[]?last/>
</@test>
