<@test name='a sequence that contains all the lookup keys in the hash.'>
  <#assign mapping={'a':1,'b':2,'c':3}/>

  <@assert expected=['a','b','c'] actual=mapping?keys/>
</@test>