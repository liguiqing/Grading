 <div id="monitorPanel" class="workspace ">
      <div class="container-fluid">
        <div class="row">         
          <aside class="col-xs-12 col-sm-12 col-md-10 col-lg-11 monitor-panel-container">
          <#import "/block/monitorPanel.ftl" as monitor>
          <@monitor.monitorPanel/>
          </aside>
          <aside class="col-md-2 col-lg-1 sidebar-menu-container">
              <ul class="sidebar-menu">
                <li><a href="#"><b>首    页</b></a></li>
                <li><a href="${request.contextPath}/marking/1/1/uuid"><b>评    卷</b></a></li>
                <li><a href="#"><b>进    度</b></a></li>
                <li><a href="#"><b>评卷设置</b></a></li>
                <li><a href="#"><b>个人中心</b></a></li>
              </ul>
          </aside>
        </div>
      </div>
  </div>