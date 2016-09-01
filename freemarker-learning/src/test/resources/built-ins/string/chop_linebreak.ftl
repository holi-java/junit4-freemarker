<@test name="remove all the last line breaks">
  <@assert expected="foo bar" actual="foo bar\r\n"?chop_linebreak/>
</@test>
