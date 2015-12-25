<#macro html entryjs="" title="" css=[]>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <title>${title}</title>	
  <#include "meta.ftl"> 
  <#list css as c>
    <link rel="stylesheet" href="${request.contextPath}/static/css/${c}.css"/>
  </#list>
</head>
<#compress>
<body entry="${entryjs}" rootPath="${request.contextPath}/" style="overflow:hidden">
  <#nested >
</body>
</#compress>
<#import "script.ftl" as script>
<@script.script entry="${entryjs}"/>
</html>
</#macro>