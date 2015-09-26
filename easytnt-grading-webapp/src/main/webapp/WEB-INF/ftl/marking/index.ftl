<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="marking/marking" title=title>
<#import "/taglib/commons/navigation.ftl" as nav> 
  <@nav.navigation menus=menus/>
  <div id="markingPanel" class="workspace ">
      <div class="container-fluid">
        <div class="row">
          <aside class="col-xs-9 col-sm-9 col-md-9 col-lg-9 img-panel">
          <#import "/block/imgPanel.ftl" as img>
          <@img.imgPanel/>
          </aside>
          <aside class="col-xs-3 col-sm-3 col-md-3 col-lg-3 point-panel">
          <#import "/block/pointsPanel.ftl" as points>
          <@points.pointsPanel/>
          </aside>
        </div>
      </div>
  </div>
  <footer class="navbar-fixed-bottom navbar-inverse status-bar">
    <ul class="pull-right "><li>阅卷员：</li><li>张无忌</li><li>科目：</li><li>语文</li><li>任务量：</li><li>1122/2309</li></ul>
	<div class="clearfix"></div>
  </footer>
</@doc.html>