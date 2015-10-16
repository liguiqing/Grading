<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="grading" title=title>
<#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=menus/>
  <#include "markingPanel.ftl" >
  <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    <ul class="pull-right "><li>阅卷员：</li><li>张无忌</li><li>科目：</li><li>语文</li><li>任务量：</li><li>1122/2309</li></ul>
  </@footer.status>
</@doc.html>