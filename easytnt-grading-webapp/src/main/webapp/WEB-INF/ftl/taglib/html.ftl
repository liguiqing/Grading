<#macro html entryjs="" title="">
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <title>${title}</title>	
  <#import "meta.ftl" as meta> 
  <@meta.meta/>
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