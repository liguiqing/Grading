<#import "taglib/html.ftl" as doc> 
<@doc.html entryjs="index" title="Grading">
	<nav id="navigation" class="navbar navbar-default navbar-fixed-top  navbar-inverse " role="navigation">
	    <div class="container">
	        <div class="navbar-header">
	            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-navbar-collapse-1">
	                <span class="sr-only">Toggle navigation</span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	                <span class="icon-bar"></span>
	            </button>
	            <a class="navbar-brand" href="${request.contextPath}/index" >
	                <img src="###" >
	            </a>
	        </div>
	        <div class="navbar-collapse collapse" id="bs-navbar-collapse-1" style="height: 1px;">
	            <ul class="nav navbar-nav">
	                <li class="active hidden"></li>
	                <li class=""><a class="smoothScroll" href="#features">Features</a></li>
	                <li class=""><a class="smoothScroll" href="#pricing">Pricing</a></li>
	                <li class=""><a class="smoothScroll" href="#quotes">Testimonials</a></li>
	                <li class=""><a class="smoothScroll" href="#faq">FAQ</a></li>
	                <li class=""><a class="smoothScroll" href="#contact">Contact</a></li>
	            </ul>
	
	            <ul class="nav navbar-nav navbar-right">
	                <li><a href="/login">Logot</a></li>
	            </ul>
	        </div>
	    </div>
	</nav>

     <div class="workspace ">
	   <div class="container-fluid">
           <div class="col-md-9 img-panel">
		       <img src="${request.contextPath}/static/css/img/sj1.jpg"  >
           </div>
           <div class="col-md-3 point-panel">
               
               <form class="form-horizontal">

			        <div class="panel panel-success">
			            <div class="panel-heading">
			                <h3 class="panel-title">第五题<div class="pull-right point-panel-toolbar"><i class="icon icon-cog"></i><i class="icon icon-caret-down"></i></div></h3>			                
			            </div>
			            
			            <div class="panel-body">
						  <div class="form-group  has-success has-feedback">						    
						    <label class="control-label col-sm-4" for="inputSuccess3">1</label>
						    <div class="col-sm-8">
							    <div class="input-group">
							      <input type="text" class="form-control point-input" aria-label="...">
							      <div class="input-group-btn">
							        <span type="button" class="btn btn-default point-mark" ><i class="icon-ok icon-large"></i></span>
							        <button type="button" class="btn btn-default dropdown-toggle point-mark-select-right" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false"><span class="caret"></span></button>
							        <ul class="dropdown-menu dropdown-menu-right">
							          <li><a href="#">满分</a></li>
							          <li><a href="#">零分</a></li>
							          <li role="separator" class="divider"></li>
							          <li><a href="#">a</a></li>
							        </ul>
							        
							      </div><!-- /btn-group -->
							    </div>		      
						    </div>
						  </div>			            
			            
						  <div class="form-group  has-success has-feedback">						    
						    <label class="control-label col-sm-4" for="inputSuccess3">1</label>
						    <div class="col-sm-6">
						      <input type="text" class="form-control"  aria-describedby="inputSuccess3Status">
						      <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>						      				      
						    </div>
						  </div>
						  <div class="form-group  has-success has-feedback">						    
						    <label class="control-label col-sm-4" for="inputSuccess3">1</label>
						    <div class="col-sm-6">
						      <input type="text" class="form-control"  aria-describedby="inputSuccess3Status">
						      <span class="form-control-feedback" aria-hidden="true">
				      
						      </span>						      				      
						    </div>
						  </div>						  		                        		                    
			
			            </div>
			            <div class="panel-footer ">
							<div class="form-group has-success has-feedback">
							    <label class="control-label col-sm-4" for="inputSuccess3">1</label>
							    <div class="col-sm-6">
							      <input type="text" class="form-control" id="inputSuccess3" aria-describedby="inputSuccess3Status">
							      <span class="glyphicon glyphicon-ok form-control-feedback" aria-hidden="true"></span>
							      <span id="inputSuccess3Status" class="sr-only">(success)</span>
							    </div>
							  </div>
				              <div class="pull-right">
				                <span type="button" class="btn btn-xs btn-success" role-for="delete">记分 </span> 
				                <span type="button" class="btn btn-xs btn-danger" role-for="over">重改</span>
				              </div>
				              <div class="clearfix"></div>           
			            </div>			            
			        </div>
			    </div>
			   
			    			                       
               </form>
               
			   <form  style="display:none;">
			       <fieldset >
				      <div class="point-panel-detail">   			        
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn">				                    
				                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left ">
				                        <li class=""><a href="#" data-value="5">5分</a><span></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">设置</a></li>
				                    </ul>
				                </div>
				                <input class="form-control" data-from="0" data-to="5" data-interval="0.5" type="text" placeholder="0-5分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn">				                    
				                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left">
				                        <li><a href="#">Action</a></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">Separated link</a></li>
				                    </ul>
				                </div>
				                <input class="form-control"  data-from="0" data-to="5" data-interval="0.5" type="text" placeholder="0-5分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn">				                    
				                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left">
				                        <li><a href="#">Action</a></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">Separated link</a></li>
				                    </ul>
				                </div>
				                <input class="form-control" data-from="0" data-to="5" data-interval="0.5" type="text" placeholder="0-5分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn">				                    
				                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left ">
				                        <li class=""><a href="#" data-value="5">5分</a><span></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">设置</a></li>
				                    </ul>
				                </div>
				                <input class="form-control" data-from="0" data-to="5" data-interval="0.5" type="text" placeholder="0-5分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn">				                    
				                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left">
				                        <li><a href="#">Action</a></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">Separated link</a></li>
				                    </ul>
				                </div>
				                <input class="form-control" data-from="0" data-to="5" data-interval="0.5" type="text" placeholder="0-5分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn">				                    
				                    <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left">
				                        <li><a href="#">Action</a></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">Separated link</a></li>
				                    </ul>
				                </div>
				                <input class="form-control" data-from="0" data-to="5" data-interval="0.5" type="text" placeholder="0-5分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>				        
				      </div>				        				        				        				        				        
				    </fieldset>
			       <fieldset >
				      <div class="point-panel-total">   			        
				        <div class="form-group ">
				            <label class="col-sm-4 control-label" for="ds_host">主机名</label>
				            <div class="col-sm-6 input-group ">
				                <div class="input-group-btn dropup">				                    
				                    <button type="button" class="btn btn-danger dropdown-toggle" data-toggle="dropdown"
				                        aria-haspopup="true" aria-expanded="false">
				                        Action <span class="caret"></span>
				                    </button>
				                    <ul class="dropdown-menu dropdown-menu-left ">
				                        <li class=""><a href="#" data-value="5">5分</a><span></li>
				                        <li><a href="#">Another action</a></li>
				                        <li><a href="#">Something else here</a></li>
				                        <li role="separator" class="divider"></li>
				                        <li><a href="#">设置</a></li>
				                    </ul>
				                </div>
				                <input class="form-control" data-from="0" data-to="20" data-interval="0.5" type="text" placeholder="0-20分" />
				                <span class="glyphicon glyphicon-question-sign form-control-feedback" style="pointer-events:auto " data-toggle="tooltip" data-placement="top" title="请用数字填写，如3班，请填写数字"3"。"></span>				                
				            </div>
				        </div>
				        <div class="pull-right"><butto type="submit" class="btn btn-success">完成</button></div>			        				        				        				        				        
				    </fieldset>
			   <form>
		       <div class="img-zoom .hidden-xs"><img src="${request.contextPath}/static/css/img/sj1.jpg" ></div>
		   			
		</div>
    </div>
    <footer class="navbar-fixed-bottom navbar-inverse status-bar">
        <div class="pull-left status-bar">
			<ul ><li>科目</li><li>语文</li><li>试卷总数</li><li>112</li></ul>
		</div>
		<div class="clearfix"></div>
    </footer>
</@doc.html>