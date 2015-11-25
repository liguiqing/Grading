<div class="subject-container">
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>科目</th><th>试卷量</th><th>答题卡原图</th><th>切割块</th><th>分配任务</th><th>满分</th><th>客观题满分</th><th>主观题满分</th><th>导入情况</th><#--<th>删除</th>-->
  	  </tr>
  	</thead>
  	<tbody>
  	<#if resultList??>
	  	<#list resultList as result>
	  	  <tr>
	  	    <td><a href="javascript:void(0)" data-rr-name="subjectName" data-rr-value="${result.subject.subjectCode}" data-rr-testId="${result.testId}">${result.subject.name}</a></td>
	  	    <td>0</td>
	  	    <#if result.usedPaper??>
		  	  <#list result.usedPaper as usedPaper>
		  	    <td>
		  	    <section class="demos">
		  	    <#if usedPaper.paperCards ??>
		  	      <#list usedPaper.paperCards as paperCard>
				  	<a href="javascript:void(0)" data-rr-name="image"  class="demo-image"  data-image="examPaper/${usedPaper.paperId}/${paperCard.cardId}" data-rr-cardId="${paperCard.cardId}" data-rr-paperId="${usedPaper.paperId}">${paperCard_index+1}<i class="icon icon-remove" style="display:none;margin:-5px 0 0 5px;color:#d9534f"></i></a>
				  </#list>
		  	    </#if>
		  	      	<a href="javascript:void(0)" data-rr-name="addImage" ><i class="icon-plus"></i></a></td>
				  </section>
				</td>
		  	    <#if usedPaper.sections ?size = 0>
		  	      <td><a class="cuttingDefine" href="javascript:void(0)" data-paperid="${usedPaper.paperId}">定义切割方案</a></td>
		  	    <#else>
		  	      <td><a href="javascript:void(0)">${usedPaper.sections ?size}</a></td>
		  	    </#if>
		  	    <td ><a href="${request.contextPath}/task/assignto/${result.subject.id}/${usedPaper.paperId}">分配任务</a></td>
		  	    <td data-rr-paperId="${usedPaper.paperId}" >${usedPaper.fullScore}</td>
		  	    <td>${usedPaper.objectivityScore}</td>
		  	    <td>${usedPaper.subjectivityScore}</td>
		  	    </#list>
	  	    </#if>
	  	    <td class="doing"><i class=" icon-remove" style="cursor: pointer;"></i></td>
	  	    <#--
	  	    <td>
	  	    <a href="javascript:void(0)" id="removeSubject"><i class="icon-minus"></i></a></td>-->
	  	  </tr>
	  	</#list>
  	</#if>	    	
  	  <tr class="bg-warning">
  	    <td><a href="javascript:void(0)" id="newSubject"><i class="icon-plus"></i></a></td><td>0</td><td></td><td><a href="javascript:void(0)">设计切割方案</a></td><td></td><td>0</td><td>0</td><td>0</td><td class="doing"></td>
  	  </tr>
  	</tbody>
  </table>
  <div class="subject-editor">
	<div class="col-md-4"></div>
	<form class="form-horizontal col-md-4">
	  <div class="form-group">
	    <label for="subjectName" class="col-sm-4 control-label">科目名称</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="name" placeholder="科目名称">
	      <input type="hidden" class="form-control" id="subjectCode">
	      <input type="hidden" class="form-control" id="testId">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="fullScore" class="col-sm-4 control-label">满分</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="fullScore" data-rr-type="number" placeholder="科目满分">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="objectivityScore" class="col-sm-4 control-label">客观题满分</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="objectivityScore" data-rr-type="number" placeholder="客观题满分">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="`subjectivityScore`" class="col-sm-4 control-label">主观题满分</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="subjectivityScore" data-rr-type="number" placeholder="主观题满分">
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