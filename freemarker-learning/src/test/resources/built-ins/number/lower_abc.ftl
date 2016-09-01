<@test name='converts 1, 2, 3, etc., to the string "a", "b", "c", etc'>
  <@assert expected='a' actual=1?lower_abc/>
  <@assert expected='aa' actual=(26+1)?lower_abc/>
  <@assert expected='ba' actual=(26*2+1)?lower_abc/>
  <@assert expected='ca' actual=(26*3+1)?lower_abc/>
</@test>