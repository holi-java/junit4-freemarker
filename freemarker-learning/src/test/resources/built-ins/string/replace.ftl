<@test name="replace all occurrences of a string in the original string with another string">
  <#assign foo='foo'/>

  <@assert expected='foo' actual=foo?replace('z','o')/>
  <@assert expected='bar' actual=foo?replace(foo,'bar')/>
  <@assert expected='foo' actual=foo?replace(foo,foo)/>
  <@assert expected='fzz' actual=foo?replace('o','z')/>
</@test>
