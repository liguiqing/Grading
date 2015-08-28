<#macro pointInputGroup dataToggle="inputSuccess" recommend=false 
    point={"name":"point1","label":"给分点","disabled":"","from":"0","to":"1","interval":"0.5","shortCuts":[{"name":"满分","key":"A","value":"1"},{"name":"零分","key":"D","value":"1"}]}>
  <div class="form-group  has-success has-feedback">
  <label class="control-label col-sm-4" for="${dataToggle}"> ${point.label}</label>
  <div class="col-sm-8">
   <div class="input-group">
    <input type="text" id="${dataToggle}" placeholder="${point.from}-${point.to}分" data-from="${point.from}"  ${point.disabled!""}
        data-to="${point.to}" data-interval="${point.interval}"  name="${point.name}"   class="form-control point-input" aria-label="...">
    <div class="input-group-btn">
     <span type="button" class="btn btn-default point-mark point-btn" data-toggle="${dataToggle}" 
           data-value="data-to" ${point.disabled!""}><i class="icon-ok icon-large"></i>
     </span> 
     <span type="button" class="btn btn-default point-mark point-btn" data-toggle="${dataToggle}" ${point.disabled!""}
           data-value="data-from"> <i class="icon-remove icon-large"></i>
     </span>
     <button type="button" class="btn btn-default dropdown-toggle point-mark-select-right" data-toggle="dropdown"
      aria-haspopup="true" aria-expanded="false">
      <span class="caret"></span>
     </button>
     <ul class="dropdown-menu dropdown-menu-right">
      <li><a href="#" class="point-btn" data-toggle="${dataToggle}" data-value="data-to" ${point.disabled!""}><i
        class="icon-star icon-large" style="color:#c83025;"></i><span>满分</span><i class="icon-key icon-large"></i><b>A</b></a></li>
      <li><a href="#" class="point-btn" data-toggle="${dataToggle}" data-value="data-from" ${point.disabled!""}><i
        class="icon-star-empty icon-large" style="color:#c83025;"></i><span>零分</span><i class="icon-key icon-large"></i><b>D</b></li>
      <li role="separator" class="divider"></li>
      <#if recommend=true><li><a href="#"><i class="icon-thumbs-up icon-large"></i>推荐</a></li></#if>
      <li><a href="#"><i class="icon-edit icon-large"></i>给分说明</a></li>
     </ul>
    </div>
   </div>
  </div>
 </div> 
 <div class="row point-desc" >
   <div class="col-sm-4"></div><div class="col-sm-8">${point.from}-${point.to}分，最小分值：${point.interval}分</div>
 </div>
</#macro>