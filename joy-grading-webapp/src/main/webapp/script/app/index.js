(function(){
	"use strict";
	var deps = ['jquery'];
	define(deps,function(){
		var o = function(){
			this.render = function(){
				console.log('---------------------');
			};
		};
		return new o();
	});
})();