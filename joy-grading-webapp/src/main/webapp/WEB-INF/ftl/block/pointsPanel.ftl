<#macro pointsPanel block={}>
<div class="">
  <form class="form-horizontal">
   <div class="panel panel-success point-panel-marking">
    <div class="panel-heading">
     <h6 class="panel-title"></h6>
    </div>

    <div class="panel-body point-panel-detail">
     <div class="form-group section-title">
       <label class="col-sm-6 col-md-6 col-lg-6 control-label " > 第五题</label>
     </div>
      <@pointInputGroup />
      <@pointInputGroup />
      <@pointInputGroup />
     <div class="form-group section-title">
       <label class="col-sm-6 col-md-6 col-lg-6 control-label " > 第六题</label>
     </div>
      <@pointInputGroup />
      <@pointInputGroup />
    </div>
    <div class="panel-footer ">
     <div class="row">
      <div class="col-sm-3 ">
       <button type="button" class=" button  button-plain button-border button-square" >满</button>
      </div>
      <div class="col-sm-3 ">
       <button type="button" class=" button  button-plain button-border button-square" >零</button>
      </div>
      <div class="col-sm-3 ">
       <button type="button" class=" button  button-plain button-border button-square" >优</button>
      </div>            
      <div class="col-sm-3 ">
       <button type="button" class=" button  button-plain button-border button-square" >样</button>
      </div>
     </div>
     <div class="clearfix"></div>
    </div>
   </div>
  </form>
 </div>
</#macro>

<#macro pointInputGroup inputId="inputSuccess"   point={"name":"point1","label":"(1)","disabled":"","from":0,"to":1,"interval":"0.5"}>
 <div class="form-group ">
  <label class="col-sm-4 col-md-4 col-lg-4 control-label " for="${inputId}"> ${point.label}<span class="point-to">${point.to}</span>分</label>
   <div class="col-sm-8 col-md-8 col-lg-8 input-group">
      <input type="text" class="form-control" aria-label="..." id="${inputId}" placeholder="请输入${point.from!"0"}到${point.to!""}">
      <div class="input-group-btn">
        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"> <span class="caret"></span></button>
        <ul class="dropdown-menu dropdown-menu-right">
          <li>
             <div class="row" >
               <div class="col-md-4"><a href="#1" class="nine-grid bg-primary">1</a></div>
               <div class="col-md-4"><a href="#2" class="nine-grid bg-primary">2</a></div>
               <div class="col-md-4"><a href="#3" class="nine-grid bg-primary">3</a></div>
             </div>
          </li>
          <li role="separator" class="divider"></li>
          <li >
             <div class="row" >
               <div class="col-md-4"><a href="#." class="nine-grid bg-primary">0</a></div>
               <div class="col-md-4"><a href="#0" class="nine-grid bg-primary"> . </a></div>
               <div class="col-md-4"><a href="##" class="nine-grid bg-success">a</a></div>
             </div>
          </li>
           <li role="separator" class="divider"></li>
        </ul>
      </div><!-- /btn-group --> 
    </div><!-- /input-group -->    
 </div> 
</#macro>