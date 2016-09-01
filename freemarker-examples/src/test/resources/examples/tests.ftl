<@test /><#--test missing name is allowed-->

<@test name="empty test is allowed"/>

<@test name="assertion test">
  <@assert expected=true/>
</@test>

<@test expected="freemarker.template.TemplateException" name="matching failed exception ( because freemarker can't eval boolean value )" >
${true}
</@test>

<@test expected="java.lang.Exception" name="matching exception with parent exception type" >
${true}
</@test>