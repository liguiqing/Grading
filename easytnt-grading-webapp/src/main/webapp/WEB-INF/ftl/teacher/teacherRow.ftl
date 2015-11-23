<#list teachers as t>
  <tr>
    <td><a href="javascript:void(0);" data-rr-name="teacher" data-rr-account="${t.teacherAccount}" data-rr-tid="${t.teacherId}" data-rr-sid="${t.subject.id}" data-rr-scode="${t.subject.subjectCode}" data-rr-leader="${t.leader!0}">${t.subject.name!}</a></td>
    <td>${t.teacherName!}</td>
    <td>${t.teacherAccount!}</td>
    <td><a href="javascript:void(0);" data-rr-name="resetPass">重置密码</a>|<a href="${request.contextPath}/task/assignto/${t.subject.id}?worker=${t.teacherAccount}">分配任务</a></td>
  </tr>
</#list>