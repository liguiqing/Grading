<div class="subject-container">
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>考试名称</th><th>开始时间</th><th>结束时间</th><th>操作</th>
  	  </tr>
  	</thead>
  	<tbody>
  	<#list examList as t>
  	  <tr>
  	    <td><a href="javascript:void(0)" data-rr-name="examName" data-rr-id="${t.id}" data-rr-oid="${t.oid}">${t.desc.name}</a></td>
  	    <td>${t.desc.from?string("yyyy-MM-dd")}</a></td>
	    <td>${t.desc.to?string("yyyy-MM-dd")}</a></td>
	    <td><a href="javascript:void(0);" data-rr-name="deleteExam">删除</a>
	    <#if t.status=0>
	      <a href="javascript:void(0);" data-rr-name="reportingExam" style="margin-left:15px;">统计成绩</a>
	    <#else>
	      <a href="${request.contextPath}/static/html/${t.id}/index.html" target="_blank" style="margin-left:15px;">查看成绩</a>
	    </#if>
	    </td>
  	  </tr>
	 </#list> 	  
  	  <tr class="bg-warning">
  	    <td><a href="javascript:void(0)" id="newExam"><i class="icon-plus"></i></a></td><td></td><td></td><td class="doing"></td>
  	  </tr>
  	</tbody>
  </table>
  <div class="subject-editor">
	<div class="col-md-4"></div>
	<form class="form-horizontal col-md-4">
	  <div class="form-group">
	    <label for="examName" class="col-sm-4 control-label">考试名称</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="examName" placeholder="考试名称">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="beginDate" class="col-sm-4 control-label">开始时间</label>
	    <div class="col-sm-8">
	      <div class="input-group">
	        <input type="text" class="form-control date-picker" id="beginDate" data-rr-type="beginDate" placeholder="开始时间" readOnly>
	        <span class="input-group-addon"><i class="icon-calendar"></i></span>
	      </div>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="endDate" class="col-sm-4 control-label">结束时间</label>
	    <div class="col-sm-8">
	      <div class="input-group">
	        <input type="text" class="form-control date-picker" id="endDate" data-rr-type="endDate" placeholder="结束时间" readOnly>
	        <span class="input-group-addon"><i class="icon-calendar"></i></span>
	      </div>
	    </div>
	  </div>
	 
	  <div class="form-group">
	    <div class="col-sm-offset-3 col-sm-9">
	      <button type="submit" class="btn btn-default">保存</button>
	    </div>
	  </div>
	</form>
	<div class="col-md-4"></div>
  </div>	
</div>