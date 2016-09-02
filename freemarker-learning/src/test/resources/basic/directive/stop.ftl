<@test name='aborts template processing with the given (optional) error message'
        expected='freemarker.core.StopException'>
  <#stop "fails" >
</@test>