<#import "/taglib/html.ftl" as doc> 
  <@doc.html entryjs="error" title="">
  <#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=[]/>
  <div class="container-fluid"  id="workspace">
    <div class="row" style="padding:100px;margin:100px;">  
      <p class="bg-danger" style="padding:15px;">${Request["message"]!"未知异常"}</p>
      <p class="bg-success" style="padding:15px;"><a href="javascript:history.go(-1)">后退</a></p>
    </div>
  </div>
  <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
  </@footer.status>
</@doc.html>