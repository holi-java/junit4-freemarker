<@test name='multi-line mode for regular expressions'>
  <#assign foo='foo\r\nfoo'/>

  <@assert expected='faa\r\nfaa' actual=foo?replace('o','a','r')/>
  <@assert expected='faa\r\nfaa' actual=foo?replace('o','a','rm')/>
</@test>