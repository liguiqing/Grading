<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="taskList" title=title>
  <div class="workspace ">
      <div class="container">
        <div class="row"　style="margin-top:100px;">         
			<div class="col-md-4"></div>
			<div class="col-md-4">
			    <ul class="list-group list-task">
			    <#list tasks as task>
			        <li class=" list-group-item" data-taskid="${task.taskId}">
			            <a href="${request.contextPath}/task/${task.taskId}">${task.genBy.name}</a>
			        </li>
			    </#list>
			    </ul>			
			</div>
			<div class="col-md-4"></div>
        </div>
      </div>
  </div>  
</@doc.html>