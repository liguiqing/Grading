<div class="workspace" >
<aside class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
<div id="cuttingDefineContainer" css="image-container" style="width:100%;background-color:white;overflow:auto;position:relative;">

<#-- 且过操作区域-->
<div class="image-content">

</div>
<#-- 且过操作区域结束-->
</div>
</aside>
</div>
<div style="display: none;">
	<div id="subQuestionPanel" class="panel panel-success subQuestionPanel">
		<div class="panel-heading">
			<span>小题信息</span> <span class="span-btn close-btn"></span>
		</div>
		<div class="panel-body">
			<div class="sub-question">
				<table class="table no-border">
					<tr>
						<td class="table-label">小题号</td>
						<td><input type="text" name="subQuestionNum"
							class="form-control"></td>
					</tr>
					<tr>
						<td class="table-label">分值</td>
						<td><input type="text" name="subQuestionScore" class="form-control"></td>
					</tr>
					<tr>
						<td class="table-label">给分率</td>
						<td>
							<select name="subQuestionScoreRate" class="subQuestionScoreRate form-control">
								<option value="1">连续</option>
								<option value="0">不连续</option>
							</select> <br/>
							<!-- 间隔 -->
							<input name="subQuestionScoreRateInterval" type="text" class="form-control" value="" placeholder="输入分值间隔">
							<input name="subQuestionScoreRateVal" readonly="readonly" type="text" class="form-control" value="">
						</td>
					</tr>
				</table>
			</div>
		</div>
	</div>
</div>