<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="login" title="用户登录">
  <div class="workspace ">
      <div class="container">
        <div class="row">         
			<div class="panel panel-default">
		        <div class="panel-heading">用户登录</div>
		        <div class="panel-body">
		            <div class="">
						<form class="form-horizontal" action="${request.contextPath}/login">
						  <div class="form-group">
						    <label for="account" class="col-sm-2 control-label">账号</label>
						    <div class="col-sm-10">
						      <input type="text" class="form-control" id="account" placeholder="Email">
						    </div>
						  </div>
						  <div class="form-group">
						    <label for="password" class="col-sm-2 control-label">密码</label>
						    <div class="col-sm-10">
						      <input type="password" class="form-control" id="password" placeholder="Password">
						    </div>
						  </div>
						  <div class="form-group">
						    <div class="col-sm-offset-2 col-sm-10">
						      <button type="submit" class="btn btn-default">登录</button>
						    </div>
						  </div>
						</form>		            
		            </div>
		        </div>
		    </div>
        </div>
      </div>
  </div>  
</@doc.html>