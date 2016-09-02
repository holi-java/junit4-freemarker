<@test name='returns the sequence of hashes sorted by the given hash subvariable in ascending order'>
  <#macro usernames users>
    <#compress>
      <#list users as user>${user.name}<#sep>,</#list>
    </#compress>
  </#macro>

  <#assign users=[
      {"name":'zhangsan',"age":13},
      {"name":'lisi',"age":18}
  ]>

  <@assert expected='lisi,zhangsan'><@usernames users=users?sort_by('name')/></@assert>
  <@assert expected='zhangsan,lisi'><@usernames users=users?sort_by('age')/></@assert>
</@test>


