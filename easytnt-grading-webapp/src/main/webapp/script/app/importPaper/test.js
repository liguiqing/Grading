(function() {
	"use strict";
	var deps = [ 'jquery', 'ajaxwrapper', 'smartWizard',
			'../../commons/progress' ];
	define(deps, function($, ajaxwrapper, smartWizard, progress) {
		var obj = function() {
			this.render = function() {
				$("#test1").click(function() {
					var data = {
						name : "my1242"
					};

					// var data = new Array();
					// data.push({
					// name : "name1",
					// avg : 10
					// });
					// data.push({
					// name : "name2"
					// });

					ajaxwrapper.postJson("/test/2", data, {}, function(data) {

					});
				});
			}
		}

		return new obj();
	});
})();