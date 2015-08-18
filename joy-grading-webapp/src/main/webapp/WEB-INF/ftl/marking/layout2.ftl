<#compress>
<#import "/taglib/html.ftl" as doc> 
<@doc.html entryjs="grading" title="Grading">
    <#import "pointInputGroup.ftl" as pig> 
	<nav id="navigation" class="navbar navbar-default navbar-fixed-top  navbar-inverse tool-bar" role="navigation" >
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
            <div class="col-xs-9 col-sm-9 col-md-9 col-lg-9 img-panel">
                 <div id="imgContainer" style="height:100%;"></div>
                 <div class="panel panel-success  img-panel-toolbox">
                       <div class="panel-heading">
					       <div class="pull-right">
                                 <i class=" glyphicon  icon-double-angle-left"></i>
                           </div>                                            
                       </div>
                       <div class="panel-body ">
                            <ul>
                                <li class="img-tool"><i class="icon-refresh"></i></li>
                                <li role="separator" class="divider"></li>
                                <li class="img-tool"><i class=" icon-trash"></i></li>
                                <li role="separator" class="divider"></li>
                                <li class="img-tool"><i class="icon-fullscreen"></i></li>                          
					       </ul>
                       </div>
                 </div>            
            </div>
            <div class="col-xs-3 col-sm-3 col-md-3 col-lg-3 point-panel">
                <form class="form-horizontal">
                    <div class="panel panel-success point-panel-marking">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <span>第五题</span>
                                <div class="pull-right point-panel-toolbar">
                                    <i class="icon glyphicon icon-cog"></i>
                                </div>
                            </h3>
                        </div>

                        <div class="panel-body point-panel-detail">
                            <#assign point ={"label":"给分点3","name":"point1","from":"0","to":"2","interval":"0.5"} >
                            <@pig.pointInputGroup dataToggle="inputSuccess1" recommend=false point=point />
                           <#assign point ={"label":"给分点3","name":"point2","from":"0","to":"4","interval":"0.5"} >
                            <@pig.pointInputGroup dataToggle="inputSuccess2" recommend=false point=point />  
                           <#assign point ={"label":"给分点3","name":"point3","from":"0","to":"4","interval":"0.5"} >
                            <@pig.pointInputGroup dataToggle="inputSuccess3" recommend=false point=point />                                                                                                                                       
                        </div>
                        
                        <div class="panel-footer point-panel-total">
                            <#assign point ={"label":"给分点3","name":"point","from":"0","to":"10","interval":"0.5"} >
                            <@pig.pointInputGroup dataToggle="inputSuccess" recommend=true point=point />
                   
                            <div class="pull-right">
                                <button type="button" class=" btn  btn-success  point-record" >记分</button> 
                                <button type="button" class=" btn  btn-danger " disabled="disabled">重改</button>
                            </div>
                            <div class="clearfix"></div>
                        </div>
                    </div>
                </form>
				<div class="panel panel-success point-panel-completed">
                        <div class="panel-heading">
                            <h3 class="panel-title">
                                <span>已阅试卷(最近10张)</span>                          
                            </h3>
                        </div>

                        <div class="panel-body ">
							<ul>
								<li><i class="icon-star"></i><a href="javascript:void(0);">024575546</a></li>
								<li><i class="icon-star-half"></i><a href="javascript:void(0);">024575547</a></li>
								<li><i class="icon-star"></i><a href="javascript:void(0);">024575548</a></li>
								<li><i class="icon-star-empty"></i><a href="javascript:void(0);">024575549</a></li>
								<li><i class="icon-star"></i><a href="javascript:void(0);">024575646</a></li>
								<li><i class="icon-star-half"></i><a href="javascript:void(0);">024576547</a></li>
								<li><i class="icon-star"></i><a href="javascript:void(0);">024573548</a></li>
								<li><i class="icon-star-empty"></i><a href="javascript:void(0);">054575549</a></li>
								<li><i class="icon-star"></i><a href="javascript:void(0);">034573548</a></li>
								<li><i class="icon-star-empty"></i><a href="javascript:void(0);">154575549</a></li>										
							</ul>
                        </div>
                    </div>                
            </div>
            </div>
        </div>
    </div>
    
    <footer class="navbar-fixed-bottom navbar-inverse status-bar">
        <div class="pull-left status-bar">
			<ul><li>科目</li><li>语文</li><li>试卷总数</li><li>112</li></ul>
		</div>
		<div class="clearfix"></div>
    </footer>
</@doc.html>
</#compress>