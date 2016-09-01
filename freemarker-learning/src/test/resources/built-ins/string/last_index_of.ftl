<@test name="returns the index within this string of the last occurrence of the specified substring">
  <#assign foo='foo'>

  <@assert expected=2 actual=foo?last_index_of('o')/>
  <@assert expected=-1 actual=foo?index_of('x')/>
</@test>
