(function() {
	"use strict";
	var deps = [ 'jquery','smartWizard' ];
	define(deps, function($,smartWizard) {
		var obj = function() {
			this.render = function() {
				$("#importPaperWizard").smartWizard({
					transitionEffect : "slide",
					labelNext : '上一步',
					labelPrevious : '下一步',
					labelFinish : '完成',
				});
				
				console.log("这是一个测试");
			}
		}

		return new obj();
	});
})();