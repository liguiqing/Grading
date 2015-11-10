<#import "/taglib/html.ftl" as doc> 
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
        <div class="row">
            <div class="col-md-9 img-panel">
                <img src="${request.contextPath}/static/css/img/sj.jpg">
                <div class="img-panel-toolbox"  title=""></div>
            </div>
            <div class="col-md-3 point-panel">
                <form class="form-horizontal">
                    <div class="panel panel-success point-panel-marking">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <span>第五题</span>
                                <div class="pull-right point-panel-toolbar">
                                    <i class="icon glyphicon icon-cog"></i><i class="icon glyphicon glyphicon-question-sign"></i>
                                </div>
                            </h3>
                        </div>

                        <div class="panel-body point-panel-detail">
                            <div class="form-group  has-success has-feedback">
                                <label class="control-label col-sm-4" for="inputSuccess1"> 得分点1</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <input type="text" id="inputSuccess1" placeholder="0-5分" data-from="0" data-to="5" data-interval="0.5" class="form-control point-input" aria-label="...">
                                        <div class="input-group-btn">
                                            <span type="button" class="btn btn-default point-mark point-btn" data-toggle="inputSuccess1" data-value="data-to">
                                                <i class="icon-ok icon-large"></i>
                                            </span>
											<span type="button" class="btn btn-default point-mark point-btn" data-toggle="inputSuccess1" data-value="data-from">
                                                <i class="icon-remove icon-large"></i>
                                            </span>
                                            <button type="button"
                                                class="btn btn-default dropdown-toggle point-mark-select-right"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="#" class="point-btn" data-toggle="inputSuccess1" data-value="data-to"><i class="icon-star icon-large"></i><span>满分</span><i class="icon-key icon-large"></i><b>A</b></a></li>
                                                <li><a href="#" class="point-btn" data-toggle="inputSuccess1" data-value="data-from"><i class="icon-star-empty icon-large"></i><span>零分</span><i class="icon-key icon-large"></i><b>S</b></li>
                                                <li role="separator" class="divider"></li>
                                                <li><a href="#"><i class="icon-edit icon-large"></i>给分说明</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>                                                                                                             
                        </div>
                        
                        <div class="panel-footer ">
                           <div class="form-group  has-success has-feedback point-panel-total">
                                <label class="control-label col-sm-4" for="inputSuccess"> 得分</label>
                                <div class="col-sm-8">
                                    <div class="input-group">
                                        <input type="text" id="inputSuccess" placeholder="0-20分" data-from="0" data-to="20" data-interval="0.5" class="form-control point-input" aria-label="...">
                                        <div class="input-group-btn">
                                            <span type="button" class="btn btn-default point-mark point-btn" data-toggle="inputSuccess" data-value="data-to">
                                                <i class="icon-ok icon-large"></i>
                                            </span>
											<span type="button" class="btn btn-default point-mark point-btn" data-toggle="inputSuccess" data-value="data-from">
                                                <i class="icon-remove icon-large"></i>
                                            </span>
                                            <button type="button"
                                                class="btn btn-default dropdown-toggle point-mark-select-right"
                                                data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                                <span class="caret"></span>
                                            </button>
                                            <ul class="dropdown-menu dropdown-menu-right">
                                                <li><a href="#" class="point-btn" data-toggle="inputSuccess" data-value="data-to"><i class="icon-star icon-large"></i><span>满分</span><i class="icon-key icon-large"></i><b>A</b></a></li>
                                                <li><a href="#" class="point-btn" data-toggle="inputSuccess" data-value="data-from"><i class="icon-star-empty icon-large"></i><span>零分</span><i class="icon-key icon-large"></i><b>S</b></li>
                                                <li role="separator" class="divider"></li>
                                                <li><a href="#"><i class="icon-thumbs-up icon-large"></i>推荐</a></li>
                                                <li><a href="#"><i class="icon-edit icon-large"></i>给分说明</a></li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="pull-right">
                                <span type="button" class="btn btn-xs btn-success" role-for="delete">记分 </span> 
                                <span type="button" class="btn btn-xs btn-danger" role-for="over">重改</span>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </form>
				<div class="panel panel-success point-panel-desc">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <span>第五题给分说明</span>                          
                            </h3>
                        </div>

                        <div class="panel-body ">
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p>              
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p> 
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p> 
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p>  
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p>              
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p> 
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p> 
                            <p class="text-left">       
                            adsfdf
                            adsfasdf
                            </p>                                                                                                                                                                                                       
                        </div>
                    </div>                
            </div>
            </div>
        </div>
    </div>
    
    <footer class="navbar-fixed-bottom navbar-inverse status-bar">
        <div class="pull-left status-bar">
			<ul ><li>科目</li><li>语文</li><li>试卷总数</li><li>112</li></ul>
		</div>
		<div class="clearfix"></div>
    </footer>
</@doc.html>