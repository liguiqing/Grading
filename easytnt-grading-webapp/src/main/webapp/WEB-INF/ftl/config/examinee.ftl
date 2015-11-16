<div class="subject-container" >
  <div class="col-md-12" style="padding-right:0px;padding-left:0px;">  
    <div class="col-md-7" style="padding-left:0px;">
    <#import "/taglib/commons/pager.ftl" as page > 
    <@page.pager pager=query css="margin:0;text-align:left;"/>
    </div>
    <div class="col-md-5" style="padding-right:0px;">
	  <form id="uploadForm"  method="POST" action="" enctype="multipar/form-data" class="form-inline">
	    <div class="col-md-offset-2 col-md-10 input-group file-preview">
	      <input type="text" class="form-control file-preview-filename" disabled="disabled">
	      <div class="input-group-btn"> 
		    <button type="button" class="btn btn-default file-preview-clear" style="display:none;">
		      <span class="glyphicon glyphicon-remove"></span>清除
		    </button>
		    <div class="btn btn-default file-preview-input" >
		      <span class="glyphicon glyphicon-folder-open"></span>
		      <span class="file-preview-input-title">选择文件</span>
		      <input id="fileName" type="file" name="fileName" style="display:none;">
		    </div>
		    <button type="type" class="btn btn-default " id="upload">
		      <span class="glyphicon glyphicon-upload"></span>上传
		    </button>
		  </div>
	    </div>
	  </form>
    </div>
  </div>
  <hr style="margin-top:10px;margin-bottom:10px;">
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>系统</th><th>文件</th><th>操作</th>
  	  </tr>
  	</thead>
  	<tbody>
  	  <tr >
  	    <td>
  	      <select name="i1" class="selectpicker" style="width:50%;">
  	        <option value="1">考点</option>
  	        <option value="2">考场</option>
  	        <option value="3">考生姓名</option>
  	        <option value="4">准考证号</option>
  	      </select>
  	    </td>
  	    <td>
  	      <select name="s1" class="selectpicker">
  	        <option value="1">考点</option>
  	        <option value="2">考场</option>
  	        <option value="3">考生姓名</option>
  	        <option value="4">准考证号</option>
  	      </select>  	    
  	    </td>
  	    <td><a href="#"><i class="icon-plus"></i></a></td>
  	  </tr>  	  
  	</tbody>
  </table>
  <div class="col-sm-offset-5 col-md-4" style="margin-bottom:10px;">
    <button type="submit" class="btn btn-default">导入考生</button>
  </div>

  <#import "/taglib/commons/pager.ftl" as page> 
  <@page.pager pager=query/>
</div>