<div class="subject-container" >
  <div class="col-md-12" style="padding-right:0px;padding-left:0px;">  
    <#if query.results??>
    <div class="col-md-7" style="padding-left:0px;">
    <#import "/taglib/commons/pager.ftl" as page > 
    <@page.pager pager=query css="margin:0;text-align:left;"/>
    </div>
    <#else>
    <div class="col-md-6" style="padding-left:0px;"></div>
    <div class="col-md-6" style="padding-right:0px;">
	  <form id="uploadForm"  method="POST" action="" enctype="multipar/form-data" class="form-inline">
	    <div class="col-md-offset-2 col-md-10 input-group file-preview">
	      <input type="text" class="form-control file-preview-filename" disabled="disabled">
	      <div class="input-group-btn"> 
		    <button type="button" class="btn btn-default file-preview-clear" style="display:none;">
		      <span class="glyphicon glyphicon-remove"></span>清除
		    </button>
		    <div class="btn btn-default file-preview-input" >
		      <span class="glyphicon glyphicon-folder-open"></span>
		      <span class="file-preview-input-title">选择报名库文件</span>
		      <input id="fileName" type="file" name="fileName" style="display:none;">
		    </div>
		    <button type="type" class="btn btn-default " id="upload">
		      <span class="glyphicon glyphicon-upload"></span>上传
		    </button>
		    <button type="type" class="btn btn-default " id="imports">
		      <span class="icon icon-random"></span>导入
		    </button>		    
		  </div>
	    </div>
	  </form>
    </div>
    </#if>
  </div>
  <br>
  <hr >
  
  <form id="mapperForm"  method="POST" action="" class="form-horizontal form-edit">
  <#assign rows = 0>
  <#list mappeds as mp>
    <#if mp_index%2=0>	
	<div class="form-group" style="margin-left:15;margin-right:0;">
	</#if>
      <label class="col-sm-3 col-md-3 control-label" for="${mp.fieldName}" data-fieldmapped="${mp.fieldName}">${mp.fieldAlias}</label>
	  <div class="col-sm-3 col-md-3">
		<select  class="selectpicker" data-width="90%" name="${mp.fieldName}" id="${mp.fieldName}">
		  <option value="-1">无</option>
		  <#list dsFields as field>
		  <option value="${field}" >${field}</option>
		  </#list>
		</select>
	  </div>
	  <#assign rows = rows-1>
    <#if rows%2=0>		
	</div>
	</#if>
  </#list>	
  <#if (rows%2) gt 0>
    </div>
  </#if>
  </form>
</div>