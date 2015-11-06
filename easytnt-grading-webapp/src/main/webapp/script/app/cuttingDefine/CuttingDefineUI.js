(function() {
	'use strict';
	var deps = [ 'jquery', 'ajaxwrapper', 'dialog' ];
	define(deps, function($, ajaxWrapper, dialog) {
		var obj = function() {
			var me = this;
			this.render = function() {
				console.log("test .......");
			};
		}
		return new obj();
	});
})();