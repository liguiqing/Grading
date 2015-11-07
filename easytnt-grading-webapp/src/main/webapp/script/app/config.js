(function(){
	"use strict";
	var deps = ['jquery'];
	define(deps,function($,point){
		var o = function(){
			
		};
		function setWorkspaceWH(){
			var navigationPanel = $('#navigation .container');
			var statusPanel = $('footer.status-bar');
			
            var h = getClientHeight()-navigationPanel.height()-statusPanel.height()-45;
            $("div.config-panel").height(h);
            $("div.config-wrapper>div").height(h-60);
		};
		return {
			render:function(){
				setWorkspaceWH();
				return new o();
			}
		};
	});
})();