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
        <div class="pull-left status-bar">
			<ul><li>科目</li><li>语文</li><li>试卷总数</li><li>112</li></ul>
		</div>
		<div class="clearfix"></div>
    </footer>
</@doc.html>