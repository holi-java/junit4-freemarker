<@test name='converts 1, 2, 3, etc., to the string "A", "B", "C", etc'>
  <@assert expected='A' actual=1?upper_abc/>
  <@assert expected='AA' actual=(26+1)?upper_abc/>
  <@assert expected='BA' actual=(26*2+1)?upper_abc/>
  <@assert expected='CA' actual=(26*3+1)?upper_abc/>
</@test>