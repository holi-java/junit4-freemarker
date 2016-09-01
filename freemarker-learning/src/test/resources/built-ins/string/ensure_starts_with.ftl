<@test name="append the substring if string not starts with the substring">
  <#assign foo='foo'>

  <@assert expected='foo' actual=foo?ensure_starts_with(foo)/>
  <@assert expected='fzfoo' actual=foo?ensure_starts_with('fz')/>
</@test>
