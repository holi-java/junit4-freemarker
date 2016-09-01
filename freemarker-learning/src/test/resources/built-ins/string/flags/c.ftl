<@test name='permits whitespace and comments in regular expressions'>
  <#assign foo='foo\r\nfoo'/>

  <@assert expected='faa\r\nfaa' actual=foo?replace('o','a','')/>
  <@assert expected='faa\r\nfaa' actual=foo?replace('#comment\no','a','rc')/>
  <@assert expected='faa\r\nfaa' actual=foo?replace(' o','a','rc')/>
  <@assert expected='faa\r\nfaa' actual=foo?replace('\r\no','a','rc')/>
</@test>