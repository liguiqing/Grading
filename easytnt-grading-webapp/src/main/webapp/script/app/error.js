(function(){
	"use strict";
	define( ['jquery','ajaxwrapper'],function($,ajax){
		var o = function(){
			ajax.getJson('/config',{page:'adsf'},{beforeMsg:{tipText:"",show:false},successMsg:{tipText:"空白卷保存成功",show:false}});
		};
		return{
			render:function(){
				return new o();
			}
		};
		
	});
})();