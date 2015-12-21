<#macro script entry="">
  <#if entry != "">  	
  <script type="text/javascript">
	window["app"]={contextPath:'${request.contextPath}',rootPath:'${request.contextPath}/',entry:'${entry}'};
  </script>
  </#if>
  <script type="text/javascript" data-main="${request.contextPath}/static/script/main"  src="${request.contextPath}/static/script/require.js"></script>
   <script type="text/javascript">
   var v =+ + new Date;
	requirejs.config({
		urlArgs : "v="+v,
	});
  </script>
</#macro>

