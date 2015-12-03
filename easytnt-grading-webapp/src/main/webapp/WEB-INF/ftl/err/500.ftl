<#import "/taglib/html.ftl" as doc> 
  <@doc.html entryjs="error" title="">
  <#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=[]/>
  <div class="container-fluid"  class="workspace" >
    <div class="row">   
    <p class="bg-danger">${Request["message"]!"未知异常"}</p>
    <p class="bg-success"><a href="javascript:history.go(-1)">后退</a></p>
  </div></div>
  <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
  </@footer.status>
</@doc.html>