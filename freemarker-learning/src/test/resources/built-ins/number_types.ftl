<@test name='byte'>
  <@assert expected=1 actual=1?byte/>
  <@assert expected=1 actual=1.2?byte/>
  <@assert expected=-128 actual=128?byte/>
  <@assert expected=127 actual=-129?byte/>
</@test>

<@test name='short'>
  <@assert expected=1 actual=1?short/>
  <@assert expected=1 actual=1.2?short/>
  <@assert expected=-32768 actual=32768?short/>
  <@assert expected=32767 actual=-32769?short/>
</@test>

<@test name='int'>
  <@assert expected=1 actual=1?int/>
  <@assert expected=1 actual=1.2?int/>
  <@assert expected=-2147483648 actual=2147483648?int/>
  <@assert expected=2147483647 actual=-2147483649?int/>
</@test>

<@test name='long'>
  <@assert expected=1 actual=1.2?long/>
</@test>

<@test name='float'>
  <@assert expected=1.2 actual=1.2?float/>
</@test>

<@test name='double'>
  <@assert expected=1.2 actual=1.2?double/>
</@test>

