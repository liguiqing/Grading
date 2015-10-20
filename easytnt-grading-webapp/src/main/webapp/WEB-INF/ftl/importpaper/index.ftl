<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="importPaper/importPaper" title="导入试卷">
 <#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus2=menus2/>
  
 <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    <ul class="pull-right "></ul>
  </@footer.status>
</@doc.html>