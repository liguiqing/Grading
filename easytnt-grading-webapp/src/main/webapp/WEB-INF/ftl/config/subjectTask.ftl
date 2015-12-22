<div class="subject-container">
<div class="row">
  <div class=" col-md-4">
    <ul><li >正为<b>${subject.name}</b>分配评卷任务</li></ul>
  </div>
</div>
<div class="row" style="display:none;">  
  <div class="col-md-12">
	<ul class="list-group list-task">
	  <#list cuttings as cutTo>
	  <li class="list-group-item" data-cuttoid="${cutTo.id}" data-sid="${subject.id}" data-pid="${paperId}">
	    <ul class="col-md-12 nav navbar-nav" >
	      <li ><a href="javascript:void(0);">${cutTo.name!""}</a></li>
	      <li class="col-md-12">
	        <ul class="col-md-12 nav navbar-nav">
	        <#list teachers as t>
		      <li class="col-md-3 col-radio">
		        <label class="control-label <#if t.leader = 1>leader</#if>">
		          <input class="form-control" type="checkbox" data-tid="${t.teacherId}">${t.teacherAccount}[${t.teacherName!}]
		        </label>
		      </li>
	        </#list>
	        </ul>
	      </li>      	    
	    </ul>
	    <div class="clearfix"></div>
	  </li>
	  </#list>
	</ul>
  </div>
  
</div>  
</div>  