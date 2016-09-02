<@test name='function'>
  <#function avg x,y>
    <#return (x+y)/2/>
  </#function>

  <@assert expected=3 actual=avg(4,2)?int/>
</@test>


<@test name='function no return'>
  <#function nil x></#function>

  <@assert expected=null actual=nil(1)/>
</@test>