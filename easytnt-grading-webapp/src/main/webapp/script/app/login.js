(function(){
	"use strict";
	define( ['jquery','ajax'],function($,ajax){
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