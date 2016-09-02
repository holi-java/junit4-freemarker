<#assign foo= ['a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j']>

<@test name='this built-in splits a sequence into multiple sequences of the size given with the 1st parameter'>
  <@assert
    expected= [
      ['a', 'b', 'c'],
      ['d', 'e', 'f'],
      ['g', 'h', 'i'],
      ['j']
    ]
    actual=foo?chunk(3)/>
</@test>

<@test name='return [foo] if chunk(foo?size)'>
  <@assert expected=[foo] actual=foo?chunk(foo?size)/>
</@test>

<@test name='return [foo] if chunk(>foo?size)'>
  <@assert expected=[foo] actual=foo?chunk(foo?size+1)/>
</@test>

<@test name='throws exception if chunk(<=0)' expected='java.lang.Exception'>
${foo?chunk(-1)}
</@test>