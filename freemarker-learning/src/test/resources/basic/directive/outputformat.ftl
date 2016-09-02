<@test name='sets the output format to the specified one'>
  <@assert expected='&'><#outputformat "HTML">&</#outputformat></@assert>
  <@assert expected='&amp;'><#outputformat "HTML">${'&'}</#outputformat></@assert>
</@test>
