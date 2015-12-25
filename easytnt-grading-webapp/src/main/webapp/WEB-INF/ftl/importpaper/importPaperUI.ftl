<div class="workspace" >
<button id="myBotton" value="test">tttttttt</button>

<div class="container-fluid">
<div style="display:none;">
<input type="hidden" name="uuId" id="uuId" value="">
<input type="hidden" name="step2Finished" id="step2Finished" value="false">
<input type="hidden" name="step3Finished" id="step3Finished" value="false">
</div>
<aside class="col-xs-12 col-sm-12 col-md-12 col-lg-12 monitor-panel-container">
<div id="importPaperWizard" class="swMain ">
	<ul>
		<li><a href="#step-1"> <label class="stepNumber">1</label> <span
				class="stepDesc">根地址 <br /> <small>填写图片和网路根地址</small></span></a></li>
		<li><a href="#step-2"> <label class="stepNumber">2</label> <span
				class="stepDesc">配置预览 <br /> <small>配置地址预览图片</small></span></a></li>
		<li><a href="#step-3"> <label class="stepNumber">3</label> <span
				class="stepDesc">完成 <br /></span></a></li>
	</ul>
	<div id="step-1">
		<h2 class="StepTitle">填写图片和网路根地址</h2>
		<br /> <br />
		<div class="container-fluid">
			<form class="form-horizontal">
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">考试：</label>
					<div class="col-sm-9">
						<select id="testId" class="form-control">
								<option selected value="1">语文考试</option>
								<option value="2">理数考试</option>
								<option value="3">理综考试</option>
						</select>
					</div>
				</div>
				<div class="form-group">
					<label for="inputEmail3" class="col-sm-3 control-label">网络根目录地址：</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="rootUrl"
							placeholder="网络根目录地址" value="http://127.0.0.1:8080" />
					</div>
				</div>
				<div class="form-group">
					<label for="inputPassword3" class="col-sm-3 control-label">科目图片根目录地址：</label>
					<div class="col-sm-9">
						<input type="text" class="form-control" id="fileDir"
							placeholder="科目图片根目录地址" value="D:\sj\lishu" />
					</div>
				</div>
			</form>
		</div>
	</div>
	<div id="step-2">
		<h2 class="StepTitle">配置地址预览图片</h2>
		<div class="container-fluid">
			<div class="row">
				<div id="step2_container" class="col-sm-12">
					
				</div>
			</div>
			
		</div>
	</div>
	<div id="step-3">
		<h2 class="StepTitle">完成</h2>
		<div class="container-fluid">
			<div class="row">
				<div id="step3_container" class="col-sm-12">
					
				</div>
			</div>
		</div>
	</div>
</div>

</aside>
</div>
</div>