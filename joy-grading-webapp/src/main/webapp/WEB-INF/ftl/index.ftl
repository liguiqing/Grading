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
	   <div class="container">
           <div class="col-md-9 img-panel">
				<img src="${request.contextPath}/static/css/img/sj.jpg"  >
           </div>
           <div class="col-md-3 point-panel">
			   <ul class="">
			      <#import "marking/markingpanel.ftl" as panel>   
				  <@panel.markingpanel stds=["a","b","c"]/>
				</ul>
				<div class="img-zoom"><img src="${request.contextPath}/static/css/img/sj.jpg" ></div>
		   </div>			
		</div>
    </div>
    <footer class="navbar-fixed-bottom navbar-inverse status-bar">
        <div>ads</div>
    </footer>
</@doc.html>