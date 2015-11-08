<div class="subject-container">
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>科目</th><th>试卷量</th><th>切割块</th><th>满分</th><th>客观题满分</th><th>主观题满分</th><th>导入情况</th>
  	  </tr>
  	</thead>
  	<tbody>
  	  <tr >
  	    <td><a href="#" data-rr-name="subjectName" data-rr-value="100">语文</a></td><td>0</td><td><a href="#">8</a></td><td>150</td><td>30</td><td>120</td><td class="completed"><i class=" icon-ok"></i></td>
  	  </tr>
  	  <tr >
  	    <td><a href="#" data-rr-name="subjectName" data-rr-value="101">数学</a></td><td>0</td><td><a href="#">设计切割方案</a></td><td>150</td><td>50</td><td>100</td><td class="doing"><i class=" icon-remove"></i></td>
  	  </tr>  	    	
  	  <tr class="bg-warning">
  	    <td><a href="#" id="newSubject"><i class="icon-plus"></i></a></td><td>0</td><td><a href="#">设计切割方案</a></td><td>0</td><td>0</td><td>0</td><td class="doing"></td>
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