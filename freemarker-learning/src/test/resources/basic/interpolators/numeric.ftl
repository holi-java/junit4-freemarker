<@test name="cast number to string">
  <@assert expected='1'>#{1}</@assert>
</@test>

<@test name="cann't used for other types" expected='freemarker.core.NonNumericalException'>
<#assign foo='1'>
#{foo}
</@test>