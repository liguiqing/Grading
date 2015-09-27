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