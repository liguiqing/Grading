<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="${js!'config'}" title="评卷设置" css=["pager","config","bootstrap/bootstrap-select","bootstrap/iCheck/all"]>
  <#import "/taglib/commons/navigation.ftl" as nav>
  <@nav.navigation menus=[] menus2=menus2/>
  <div class="workspace ">
      <div class="container">
        <div class="row">         
          <aside class="col-xs-12 col-sm-12 col-md-10 col-lg-11 config-panel-container">
			<div class="config-panel">
	          <ul class="nav navbar-nav">
	          <#list menus3 as menu>
	            <li class="<#if menu.actived=true>config-actived</#if>"><a class="smoothScroll" href="${request.contextPath}/${menu.url!""}">${menu.name}</a></li>
	          </#list>
	          </ul>
	          <div class="config-wrapper">
	          <#if page?exists>
	          <#include "/config/"+page+".ftl" />
	          </#if>
	          </div>		  
			</div>       
          </aside>
          <aside class="col-md-2 col-lg-1 sidebar-menu-container">
            <#include "/share/rightSideMenu.ftl">  
          </aside>
        </div>
      </div>
  </div>  
  <#import "/taglib/commons/status.ftl" as footer> 
  <@footer.status>
    
  </@footer.status>
</@doc.html>