<@test name="returns the index within this string of the first occurrence of the specified substring">
  <#assign foo='foo'>

  <@assert expected=1 actual=foo?index_of('o')/>
  <@assert expected=-1 actual=foo?index_of('x')/>
</@test>
