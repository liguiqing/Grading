<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="cuttingDefine/cuttingDefine" title="切割定义" css=["cutting/common","cutting/resize"]>
 <#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=menus/>
  
<#include "./CuttingDefineUI.ftl">
<#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    <ul class="pull-right">
    	<div class="clearfix">
    		<span>缩放</span>
			<input class="sliderui" type="text" data-slider-min="0" data-slider-max="100" data-slider-step="1" data-slider-value="100" data-slider-enabled="false"/>
  			&nbsp;
  			<input class="slider-enabled" type="checkbox"/>
  			<span>启用</span>
  			&emsp;
			<input id="saveBtn" class="btn btn-default btn-sm" type="button" value="保存" style="color:black;">
			<input id="alignBtn" class="btn btn-default btn-sm" type="button" value="对齐" style="color:black;">
		</div>
    </ul>
  </@footer.status>
</@doc.html>