<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="grading" title=title>
<#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=menus/>
  <#include "markingPanel.ftl" >
  <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    <ul class="pull-right "><li>阅卷员：</li><li>${referees.name!}</li><li>科目：</li><li>${task.subjectName!S}</li><li>任务量：</li><li><b>${task.refereesTotal!0}</b>/<b>${task.subjectTotal!0}</b></li></ul>
  </@footer.status>
</@doc.html>