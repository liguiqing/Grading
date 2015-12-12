<#list examinees as examinee>
  <tr>
    <td>${examinee.examinneName!""}</td>
    <td>${examinee.examinneUuid!""}</td>
    <td>${examinee.studentNumber!""}</td>
    <td>${examinee.clazzName!""}</td>
  </tr>
</#list>