<div class="subject-container">
  <div class="col-md-12">
    <div class="col-md-9">
    <#import "/taglib/commons/pager.ftl" as page > 
    <@page.pager pager=query css="margin:0;text-align:left;"/>
    </div>
    <div class="col-md-1"></div>
    <div class="col-md-2">	 
	  <select id="subject-query" class="form-controller selectpicker">
	    <option value="-1">全部</option>
	  <#list subjects as subject>
	    <option value="${subject.id}">${subject.name!}</option>
	  </#list>
	  </select>
    </div>
  </div>	 
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>科目</th><th>姓名</th><th>账号</th><th>操作</th>
  	  </tr>
  	</thead>
  	<tbody>
  	<#if query.results?size gt 0>
  	 <#assign teachers=query.results>
  	 <#include "/teacher/teacherRow.ftl">
  	</#if>
	<tr class="bg-warning">
   	    <td><a href="javascirpt:void(0);" id="newTeacher"><i class="icon-plus"></i></a></td>
   	    <td></td>
   	    <td><a href="javascirpt:void(0);"></a></td>
   	    <td><a href="javascirpt:void(0);"></td>
   	  </tr>  	  
  	</tbody>
  </table>
  
  <div class="subject-editor">
	<div class="col-md-4"></div>
	<form class="form-horizontal col-md-4" style="display:none;">
	  <div class="form-group">
	    <label for="teacher" class="col-sm-4 control-label">科目</label>
	    <div class="col-sm-8">
	     <select id="subject" class="form-controller selectpicker">
	     <#list subjects as subject>
	    	<option value="${subject.subjectCode}" data-rr-value="${subject.id}">${subject.name!}</option>
	     </#list>
	     </select>
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="teacherName" class="col-sm-4 control-label">姓名</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="teacherName" id="teacherName" placeholder="姓名">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="teacherAccount" class="col-sm-4 control-label">账号</label>
	    <div class="col-sm-8">
	      <input type="text" readonly="readonly" class="form-control" id="teacherAccount"  placeholder="账号">
	    </div>
	  </div> 
	  <div class="form-group">
	    <label for="accounts" class="col-sm-4 control-label">账号数量</label>
	    <div class="col-sm-3">
	      <input type="text"  class="form-control" id="amount"  value="1" placeholder="账号数量">
	    </div>
	    <div class="col-sm-3 col-radio"><label class="control-label "><input class="form-control" type="checkbox"  id="isLeader" name="isLeader" value="1">组长</label></div>	
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
