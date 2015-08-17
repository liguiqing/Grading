function getUrlFileName(){
	var pathname = window.location.pathname;
	if(pathname == '' || pathname == '/') return 'index';
	var pathArr  = pathname.split("/");
	var fileName = (pathArr[pathArr.length-1]=='') ? 'index':pathArr[pathArr.length-1].split(".")[0];
	return fileName;
}

function getBrowser(){
    var browser = {};
	var ua = navigator.userAgent.toLowerCase();

    var s;
	//浏览器
    (s = ua.match(/msie ([\d.]+)/)) ? browser.ie = s[1] :
    (s = ua.match(/firefox\/([\d.]+)/)) ? browser.firefox = s[1] :
    (s = ua.match(/chrome\/([\d.]+)/)) ? browser.chrome = s[1] :
    (s = ua.match(/opera.([\d.]+)/)) ? browser.opera = s[1] :
    (s = ua.match(/version\/([\d.]+).*safari/)) ? browser.safari = s[1] : 0;
	
	//移动设备
    (s = ua.match(/ipad/i)) ? browser.ipad = true:
    (s = ua.match(/iphone os/i)) ? browser.iphone = true:
    (s = ua.match(/midp/i)) ? browser.midp = strue:
    (s = ua.match(/android/i)) ? browser.android = true:
    (s = ua.match(/windows ce/i)) ? browser.windowsCE = true:
    (s = ua.match(/windows mobile/i)) ? browser.windowsMobile = true:0;
	
	browser.isMobile = function(){
		return browser.ipad||browser.iphone||browser.midp||browser.android||browser.windowCE||browser.windowsMobile;
	};
	return browser;
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
var browser = getBrowser();
var jqueryPath = "lib/jquery/jquery.min";
if(browser.ie && browser.ie * 1 < 10){
	jqueryPath = "lib/jquery/jquery-1.11.3.min";
}
var config = {
	contextPath : window.app.rootPath,
	baseUrl : window.app.rootPath + "static/script/", 
	optimize : "none",

	paths : {
		"jquery" : jqueryPath,
		"bootstrap" : "lib/bootstrap/bootstrap.min",
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
};
if(browser.isMobile()){
    config.paths["jqueryM"] = "lib/jquery/jquery.mobile-1.4.5.min";
	config.shim["jqueryM"] = {deps:['jquery']};
}
if(console){console.log(config)}
requirejs.config(config);

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
