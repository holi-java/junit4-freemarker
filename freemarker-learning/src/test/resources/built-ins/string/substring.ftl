<#assign foo='foo'>

<@test name="substring(start)">
  <@assert expected='oo' actual=foo?substring(1)/>
  <@assert expected='' actual=foo?substring(foo?length)/>
</@test>

<@test name="substring(start,end)">
  <@assert expected='o' actual=foo?substring(1,2)/>
  <@assert expected='oo' actual=foo?substring(1,foo?length)/>
</@test>

<@test name="throw exception when substring(-1)" expected='freemarker.template.TemplateException'>
${foo?substring(-1)}
</@test>

<@test name="throw exception when substring(start,-1)" expected='freemarker.template.TemplateException'>
${foo?substring(0,-1)}
</@test>

<@test name="throw exception when substring(length+1)" expected='freemarker.template.TemplateException'>
${foo?substring(foo?length+1)}
</@test>

<@test name="throw exception when substring(start,length+1)" expected='freemarker.template.TemplateException'>
${foo?substring(0,foo?length+1)}
</@test>

<@test name="throw exception when substring(start,end) if start < end" expected='freemarker.template.TemplateException'>
${foo?substring(3,2)}
</@test>