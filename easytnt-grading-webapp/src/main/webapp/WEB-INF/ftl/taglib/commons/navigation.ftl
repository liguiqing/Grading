<#macro navigation menus=[] brand={} menus2=[]>
<nav id="navigation" class="navbar navbar-default navbar-fixed-top  navbar-inverse tool-bar" role="navigation" >
	    <div class="container">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-navbar-collapse-1">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <#if brand.name??><a class="navbar-brand" href="${request.contextPath}/index" ></a></#if>
	        </div>
	        <div class="navbar-collapse collapse" id="bs-navbar-collapse-1" style="height: 1px;">
	            <#if (menus?size > 0)>
	            <ul class="nav navbar-nav">
	                <li class="active hidden"></li>
	                <#list menus as menu>
	                <li class=""><a class="smoothScroll" href="#${menu.url!""}">${menu.name}</a></li>
	                </#list>
	            </ul>
	            </#if>
	            <#if (menus2?size > 0)>
	            <ul class="nav navbar-nav navbar-right">
	                <#list menus2 as menu>
	                <li class=""><a class="smoothScroll" href="#${menu.url!""}">${menu.name}</a></li>
	                </#list>
	            </ul>
	            </#if>
	        </div>
	    </div>
</nav>
</#macro>