<@test name='Enables dot-all mode for regular expressions (same as Perl singe-line mode).'>
  <#assign foo='foo\r\nfoo'/>

  <@assert expected='foo\r\nfoo' actual=foo?replace('.','a')/>
  <@assert expected='aaa\r\naaa' actual=foo?replace('.','a','r')/>
  <@assert expected='aaaaaaaa' actual=foo?replace('.','a','rs')/>
</@test>