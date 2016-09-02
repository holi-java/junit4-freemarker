<@test name='does not search FTL tags and interpolations and other special character sequences in the body of this directive'>
  <@assert expected=r'${foo}'><#noparse>${foo}</#noparse></@assert>
</@test>