(function() {
	"use strict";
	var deps = [ 'jquery','smartWizard' ];
	define(deps, function($,smartWizard) {
		var obj = function() {
			this.render = function() {
				console.log("这是一个测试");
			}
		}

		return new obj();
	});
})();