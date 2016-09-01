<@test name='the substring to find is a regular expression'>
  <#assign foo='foo'/>

  <@assert expected='foo' actual=foo?replace('o|f','a')/>
  <@assert expected='foo' actual=foo?replace(r'o|f','a')/>
  <@assert expected='aaa' actual=foo?replace('o|f','a','r')/>
</@test>