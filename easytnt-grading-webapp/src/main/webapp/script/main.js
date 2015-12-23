Array.prototype.remove = function(obj) {
	var index = -1;
	for(var i = 0; i < this.length; i++) {
		if(this[i] == obj) {
			index = i;
			break;
		}
	}
	
	if(index != -1) {
		this.splice(index, 1);
	}
}

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
//IEMobile10 
if (navigator.userAgent.match(/IEMobile\/10\.0/)) {
	var msViewportStyle = document.createElement('style')
	msViewportStyle.appendChild(document.createTextNode('@-ms-viewport{width:auto!important}'))
	document.querySelector('head').appendChild(msViewportStyle)
}



var config = {
	contextPath : window.app.rootPath,
	baseUrl : window.app.rootPath + "script/", 
	optimize : "none",

	paths : {
		"jquery" : jqueryPath,
		"bootstrap" : "lib/bootstrap/bootstrap.min",
		"bootstrapSlider" : "lib/bootstrap/plugins/bootstrap-slider/bootstrap.slider.v4",
		"headroom":"http://hm.baidu.com/h.js?3d8e7fc0de8a2a75f2ca3bfe128e6391",
		"chart" : "lib/highcharts/highcharts",
		"smartWizard" : "lib/smartWizard/jquery.smartWizard",
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
		'bootstrap' : {deps:['jquery']},
		'chart' : {deps:['jquery']},
		'headroom': {deps:['jquery']}
	}
};
if(browser.isMobile()){
    config.paths["jqueryM"] = "lib/jquery/jquery.mobile-1.4.5.min";
	config.shim["jqueryM"] = {deps:['jquery']};
}
//IE禁止响应式布局
if(browser.ie && !browser.isMobile()){
	var metas = document.getElementsByTagName('meta');
	for(var i=0;i<metas.length;i++){
		var name = metas[i].attributes["name"]
		if(name && name.value === "viewport"){
			metas[i].parentNode.removeChild(metas[i]);
			break;
		}
	}
}
requirejs.config(config);

//两次require,确保公共方法加载完成后才加入模块
require(['jquery','bootstrap','funcs'], function($) {
	var p = [ 'app/' + window.app.entry ];
	if(!window.JSON){//如果浏览器不支持JSON，使用JSON2
		p[1]="util/json2";
	}
	require(p, function(module) {
		//Android4.1 系统默认的浏览器将不会显示侧边栏控件
		if(browser.android){
			$('select.form-control').removeClass('form-control').css('width', '100%')
		}
		
		if(module){
			module.render();
		}
	});
});
