(function(){
	"use strict";
	define( ['jquery','ajax'],function($,point){
		var o = function(){
			$('#username').focus();
		};
		return{
			render:function(){
				return new o();
			}
		};
		
	});
})();