<div class="worker-container">
  <table class="table table-striped table-bordered ">
  	<thead class="bg-primary">
  	  <tr>
  	    <th>账号</th><th>姓名</th><th>评卷量</th><th>速度（秒/张）</th><th>平均分</th><th>最高分</th><th>最低分</th><th>小组一致性</th><th>自身稳定性</th>
  	  </tr>
  	</thead>
  	<tbody>
  	<#list datas.rows as row>
  	  <tr >
  	    <td>${row[1]}</td><td>${row[0]}</td>
  	    <td><div class="completed">${row[2]!"0"}</div></td>
  	    <td>${row[6]!"0"}</td>
  	    <td>${row[5]!"0"}</td><td>${row[3]!"0"}</td><td>${row[4]!"0"}</td><td>1</td><td>1</td>
  	  </tr>
  	</#list> 	    	
  	</tbody>
  </table>
</div>