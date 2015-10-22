(function() {
	"use strict";
	var deps = [ 'jquery', 'smartWizard', '../../commons/progress' ];
	define(deps, function($, smartWizard, progress) {
		var obj = function() {
			this.render = function() {
				$("#importPaperWizard").smartWizard({
					transitionEffect : "slide",
					labelNext : '上一步',
					labelPrevious : '下一步',
					labelFinish : '完成',
					enableAllSteps : false,
					noForwardJumping : true,
					onLeaveStep : function() {
						// console.log("onLeaveStep");
						// console.log(arguments);
						// console.log(this);
						return true;
					},
					onShowStep : function(container, obj) {
						console.log(arguments);
						console.log(obj);
						if (obj.fromStep === 1 && obj.toStep === 2) {
							var $step2_container = $("#step2_container");

							var param = {
								container : $step2_container,
								entry : "ddd",
								data : {
									uuid : "123"
								}
							};
							
							progress.init(param);
						}

						return false;
					},
					onFinish : function() {
						console.log("onFinish");
					}
				});

			}
		}

		return new obj();
	});
})();