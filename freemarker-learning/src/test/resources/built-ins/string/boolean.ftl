<@test name='convert string to boolean using custom boolean format'>
  <#setting boolean_format='yes,no'>

  <@assert expected=true actual='true'?boolean/>
  <@assert expected=false actual='false'?boolean/>
  <@assert expected=true actual='yes'?boolean/>
  <@assert expected=false actual='no'?boolean/>
</@test>

<@test name='true'>
  <@assert expected=true actual='true'?boolean/>
</@test>

<@test name='false'>
  <@assert expected=false actual='false'?boolean/>
</@test>

<@test name='upper case is not supported' expected='freemarker.template.TemplateException'>
${'FALSE'?boolean}
</@test>