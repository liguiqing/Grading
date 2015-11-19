<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="cuttingDefine/cuttingDefine" title="切割定义" css=["cutting/common","cutting/resize"]>
 <#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=menus/>
  
<#include "./CuttingDefineUI.ftl">
<#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    <ul class="pull-left">
    	<div class="clearfix">
			<input id="saveBtn" class="btn btn-default btn-sm" type="button" value="保存" style="color:black;">
			<input id="alignBtn" class="btn btn-default btn-sm" type="button" value="对齐" style="color:black;">
		</div>
    </ul>
  </@footer.status>
</@doc.html>