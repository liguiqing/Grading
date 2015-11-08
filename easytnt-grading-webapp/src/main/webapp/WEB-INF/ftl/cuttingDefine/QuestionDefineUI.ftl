<div class="control-content" style="display:none;">
	<div class="panel panel-info">
		<div class="panel-heading" style="position:relative;">
			<span>题目信息</span>
			<span class="span-btn close-btn"></span>
		</div>
		<div class="panel-body" style="width:100%;height:400px;overflow:auto;">
			<table class="table no-border">
				<tr>
					<td>题号</td>
					<td><input type="text" id="questionName" name="questionName"
						class="form-control"></td>
				</tr>
				<tr>
					<td>满分值</td>
					<td><input id="fullScore" type="text" name="fullScore"
						class="form-control"></td>
				</tr>
				<tr>
					<td>X坐标(px)</td>
					<td><span id="x" class="label label-success"></span></td>
				</tr>
				<tr>
					<td>Y坐标(px)</td>
					<td><span id="y" class="label label-success"></span></td>
				</tr>
				<tr>
					<td>宽度(px)</td>
					<td><span id="width" class="label label-info"></span></td>
				</tr>
				<tr>
					<td>高度(px)</td>
					<td><span id="height" class="label label-info"></span></td>
				</tr>
				<tr>
					<td>小题定义</td>
					<td><span class="span-btn add-btn"></span></td>
				</tr>
				<tr>
					<td colspan="2" class="sub-question-container"></td>
				</tr>
			</table>
		</div>
	</div>
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