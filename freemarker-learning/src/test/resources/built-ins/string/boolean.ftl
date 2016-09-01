<@test name='true'>
  <@assert expected=true actual='true'?boolean/>
</@test>

<@test name='false'>
  <@assert expected=false actual='false'?boolean/>
</@test>

<@test name='upper case is not supported' expected='freemarker.template.TemplateException'>
${'FALSE'?boolean}
</@test>