<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="teamMonitor" title=title>
<#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus2=menus2/>
  <#include "teamMonitor.ftl" >
  <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    <ul class="pull-right "><li>组长：</li><li>谢逊</li><li>科目：</li><li>语文</li><li>任务量：</li><li>10240</li></ul>
  </@footer.status>
</@doc.html>