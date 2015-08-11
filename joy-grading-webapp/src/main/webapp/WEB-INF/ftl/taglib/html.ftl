<#macro html entryjs="" title="">
<!DOCTYPE html>
<html lang="zh-cn">
<head>
  <title>${title}</title>	
  <meta charset="utf-8" />   
  <#import "meta.ftl" as meta> 
  <@meta.meta/>
</head>
<body entry="${entryjs}" rootPath="${request.contextPath}/" style="overflow:hidden">
  <#nested >
</body>
<#import "script.ftl" as script>
<@script.script entry="${entryjs}"/>
</html>
</#macro>