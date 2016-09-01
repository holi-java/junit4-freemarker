<@test name='return true if it the entire string matches the pattern'>
  <#assign foo='foo'>

  <@assert expected=foo?matches(r'\w+')/>
  <@assert expected=!foo?matches(r'\w')/>
</@test>

