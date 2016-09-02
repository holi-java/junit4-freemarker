<@test name='import'>
  <#import '_lib.ftl' as lib>
  <@assert expected='bar' actual=lib.foo/>
</@test>

<@test name='variables shared'>
  <#import '_lib.ftl' as lib1>
  <#import '_lib.ftl' as lib2>

  <#assign foo='foo' in lib2>

  <@assert expected='foo' actual=lib1.foo/>
  <@assert expected='foo' actual=lib2.foo/>
</@test>