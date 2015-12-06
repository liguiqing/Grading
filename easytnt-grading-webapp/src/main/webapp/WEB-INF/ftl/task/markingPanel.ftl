 <div id="markingPanel" class="workspace ">
  <input type="hidden" id="imgServer" value="${imgServer!"http://localhost:8888"}/">
  <div class="container-fluid">
    <div class="row">
      <aside class="col-xs-9 col-sm-9 col-md-9 col-lg-9 img-panel-container">
      <#import "imgPanel.ftl" as img>
      <@img.imgPanel/>
      </aside>
      <aside class="col-xs-3 col-sm-3 col-md-3 col-lg-3 point-panel-container">
      <#import "pointsPanel.ftl" as points>
      <@points.pointsPanel sections=sections/>
      </aside>
    </div>
  </div>
</div>