<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="monitor" title="评卷设置" css=["config"]>
  <#import "/taglib/commons/navigation.ftl" as nav>
  <@nav.navigation menus=menus menus2=menus2/>
  <div class="workspace ">
      <div class="container">
        <div class="row">         
          <aside class="col-xs-12 col-sm-12 col-md-10 col-lg-11 config-panel-container">
			<div class="config-panel">
	          <div class="col-xs-12">
	            <div class="col-md-4"></div>
	            <div class="col-md-4" style="text-align:center;color:#fff;"><h4>成绩单</h4></div>
	            <div class="col-md-4"></div>
	          </div>
	          <div class="config-wrapper">
	          
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
    <ul class="pull-right "><li>用户：</li><li>${(user.userName)!}</li></ul>
  </@footer.status>
</@doc.html>