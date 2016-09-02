<@test name='a sequence that contains all the lookup values in the hash.'>
  <#assign mapping={'a':1,'b':2,'c':3}/>

  <@assert expected=[1,2,3] actual=mapping?values/>
</@test>