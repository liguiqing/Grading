<#macro status>
<footer class="navbar-fixed-bottom navbar-inverse status-bar">
    <#nested>
	<div class="clearfix">
		<input id="saveBtn" type="button" value="保存" style="color:black;">
		<input id="previousBtn" type="button" value="上一页" style="color:black;" disabled="disabled"/>
		<input id="nextBtn" type="button" value="下一页" style="color:black;"/>
	</div>
</footer>
</#macro>
