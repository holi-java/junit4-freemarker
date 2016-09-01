<@test name="split a string into a sequence of strings along the occurrences of another string">
  <@assert expected=['foo','bar','barz'] actual='foo bar barz'?split(' ')/>
  <@assert expected=['foo,bar;barz'] actual='foo,bar;barz'?split(',|;')/>
</@test>
