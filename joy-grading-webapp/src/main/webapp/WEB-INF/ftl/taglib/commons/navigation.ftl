<#macro navigation menus=[]>
<nav id="navigation" class="navbar navbar-default navbar-fixed-top  navbar-inverse tool-bar" role="navigation" >
	    <div class="container">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-navbar-collapse-1">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="${request.contextPath}/index" >
	                <img src="###" >
	            </a>
	        </div>
	        <div class="navbar-collapse collapse" id="bs-navbar-collapse-1" style="height: 1px;">
	            <ul class="nav navbar-nav">
	                <li class="active hidden"></li>
	                <#list menus as menu>
	                <li class=""><a class="smoothScroll" href="#${menu.url!""}">${menu.name}</a></li>
	                </#list>
	            </ul>
	            <ul class="nav navbar-nav navbar-right">
	                <li><a href="/login">Logot</a></li>
	            </ul>
	        </div>
	    </div>
</nav>
</#macro>