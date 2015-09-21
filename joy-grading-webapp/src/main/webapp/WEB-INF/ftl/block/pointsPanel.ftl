<#macro pointsPanel block={}>
<div class="point-panel">
  <form class="form-horizontal">
   <div class="panel panel-success point-panel-marking">
    <div class="panel-heading">
     <h3 class="panel-title">
      <span>${block.name!}</span>
      <div class="pull-right point-panel-toolbar">
       <i class="icon glyphicon icon-cog"></i>
      </div>
     </h3>
    </div>

    <div class="panel-body point-panel-detail">
    <@pointInputGroup />
    </div>
    <div class="panel-footer point-panel-total">
     <div class="row">
      <div class="col-sm-6 point-record">
       <button type="button" class=" button button-action button-rounded ">记分</button>
      </div>
      <div class="col-sm-6 point-reset">
       <button type="button" class="  button button-action button-rounded " disabled="disabled">重改</button>
      </div>
     </div>
     <div class="clearfix"></div>
    </div>
   </div>
  </form>
 </div>
</#macro>

<#macro pointInputGroup inputId="inputSuccess"   point={"name":"point1","label":"给分点","disabled":"","from":0,"to":1,"interval":"0.5"}>
 <div class="form-group ">
  <label class="col-sm-4 col-md-4 col-lg-3 control-label " for="${inputId}"> ${point.label}</label>
  <div class="col-sm-8 col-md-8 col-lg-9">
   <div class="input-group">
      <input type="text" class="form-control" aria-label="..." id="${inputId}" placeholder="请输入${point.from!"0"}到${point.to!""}">
      <div class="input-group-btn">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Action <span class="caret"></span></button>
        <ul class="dropdown-menu dropdown-menu-right">
          <li class="nine-grid">
            <ul><li><a href="#1">1</a></li><li><a href="#2">2</a></li><li><a href="#3">3</a></li></ul>
          </li>
          <li class="nine-grid">
            <ul><li><a href="#0">0</a></li><li><a href="#dot">.</a></li><li><a href="##" class="btn btn-success">确定</a></li></ul>
          </li>          
        </ul>
      </div><!-- /btn-group -->
    </div><!-- /input-group -->    
 </div> 
</#macro>