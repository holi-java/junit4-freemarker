<@test name='returns a sequence that contains all words of the string in the order as they appear in the string'>
  <@assert expected=['the',',first','...','and','last']
            actual='the ,first ... and last'?word_list/>
</@test>