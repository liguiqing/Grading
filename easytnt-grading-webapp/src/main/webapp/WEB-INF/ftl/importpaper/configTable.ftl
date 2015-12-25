<div class="row">
	<div class=".col-sm-12">
		<h3 class="text-success" style="padding-left:50px;">总共扫描：${totalFielSize}张图片</h3>
		<div style="display:none;">
			<input type="hidden" name="fileSize" id="fileSize" value="${totalFielSize}">
		</div>
	</div>
</div>
<table id="viewImageTable" class="table">
	<thead>
		<tr>
			<th>地址</th>
			<th><select class="form-control mappingFiled">
					<option selected value="KM">科目</option>
					<option value="DQ">地区</option>
					<option value="KC">考场</option>
					<option value="TH">题号</option>
			</select></th>
			<th><select class="form-control mappingFiled">
					<option  value="KM">科目</option>
					<option selected value="DQ">地区</option>
					<option value="KC">考场</option>
					<option value="TH">题号</option>
			</select></th>
			<th><select  class="form-control mappingFiled">
					<option  value="KM">科目</option>
					<option value="DQ">地区</option>
					<option selected value="KC">考场</option>
					<option value="TH">题号</option>
			</select></th>
			<th><select class="form-control mappingFiled">
					<option  value="KM">科目</option>
					<option value="DQ">地区</option>
					<option value="KC">考场</option>
					<option  selected value="TH">题号</option>
			</select></th>
		</tr>
	</thead>
	<tbody>
	<#list cutImageInfos as ci>
		<tr>
			<td><a href="#" class="viewImage" title="点击预览" imagePath="${url}${ci.imagePath}">${url}${ci.imagePath}</a></td>
			<td>${ci.getName(0)}</td>
			<td>${ci.getName(1)}</td>
			<td>${ci.getName(2)}</td>
			<td>${ci.getName(3)}</td>
		</tr>
	</#list>
	</tbody>
</table>
