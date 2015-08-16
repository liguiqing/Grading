function getUrlFileName(){
	var pathname = window.location.pathname;
	if(pathname == '' || pathname == '/') return 'index';
	var pathArr  = pathname.split("/");
	var fileName = (pathArr[pathArr.length-1]=='') ? 'index':pathArr[pathArr.length-1].split(".")[0];
	return fileName;
}

window['Joy'] = Joy = {
	version : '1.0'
};

if(!window.app){
	//如果没有设置 window.app
	var includeEntry = document.body.getAttribute('entry');
	if(includeEntry != null){
		//如果body内有设置entry属性
		var includeRootPath = document.body.getAttribute('rootPath');
		if(includeRootPath != null) {
			window.app = {rootPath:includeRootPath,entry:includeEntry};
		} else {
			window.app = {rootPath:"",entry:includeEntry};
		}
	} else {
		//如果两个都没有设置则自动加载与网址后缀名相同的js
		window.app = {rootPath:"",entry:getUrlFileName()};
	}
}

requirejs.config({
	contextPath : window.app.rootPath,
	baseUrl : window.app.rootPath + "static/script/", 
	optimize : "none",

	paths : {
		"jquery" : "lib/jquery/jquery.min",
		"bootstrap" : "lib/bootstrap/bootstrap.min",
		"jqueryM":"lib/jquery/jquery.mobile-1.4.5.min",
		"dialog":"commons/dialog",
		"ui":"commons/uiwrapper", 
		"ajax":"commons/ajax",
		"ajaxwrapper":"commons/ajaxwrapper",
		"logger":"util/logger",
		"funcs":"commons/functions",
		"StringBuffer":"ext/StringBuffer",
		"Map":"ext/Map"
	},
	shim : {
		'bootstrap' : {deps:['jquery']}
	}
});

//两次require,确保公共方法加载完成后才加入模块
require(['jquery','bootstrap','funcs'], function() {
	var p = [ 'app/' + window.app.entry ];
	if(!window.JSON){//如果浏览器不支持JSON，使用JSON2
		p[1]="util/json2";
	}
	require(p, function(module) {
		if(module){
			module.render();
		}
	});
});
