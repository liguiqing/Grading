<div class="subject-container">
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>科目</th><th>试卷量</th><th>切割块</th><th>满分</th><th>客观题满分</th><th>主观题满分</th><th>导入情况</th>
  	  </tr>
  	</thead>
  	<tbody>
  	  <tr >
  	    <td>语文</td><td>0</td><td><a href="#">8</a></td><td>150</td><td>30</td><td>120</td><td><i class=" icon-ok"></i></td>
  	  </tr>
  	  <tr >
  	    <td>数学</td><td>0</td><td><a href="#">定义切割方案</a></td><td>150</td><td>50</td><td>100</td><td><i class=" icon-remove"></i></td>
  	  </tr>  	    	
  	  <tr class="bg-warning">
  	    <td><i class="icon-plus"></i></td><td>0</td><td><a href="#">定义切割方案</a></td><td>0</td><td>0</td><td>0</td><td></td>
  	  </tr>
  	</tbody>
  </table>
  <div class="subject-editor">
	<div class="col-md-4"></div>
	<form class="form-horizontal col-md-4">
	  <div class="form-group">
	    <label for="subjectName" class="col-sm-4 control-label">科目名称</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="subjectName" placeholder="科目名称">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="fullScore" class="col-sm-4 control-label">满分</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="fullScore" placeholder="科目满分">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="objectivityScore" class="col-sm-4 control-label">客观题满分</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="objectivityScore" placeholder="客观题满分">
	    </div>
	  </div>
	  <div class="form-group">
	    <label for="`subjectivityScore`" class="col-sm-4 control-label">主观题满分</label>
	    <div class="col-sm-8">
	      <input type="text" class="form-control" id="subjectivityScore" placeholder="主观题满分">
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