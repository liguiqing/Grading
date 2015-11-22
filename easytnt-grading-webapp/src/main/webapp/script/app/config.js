(function(){
	"use strict";
	define(['jquery'],function($){
		var o = function(){
			var url = window.location.href;
			var m = url.substring(url.lastIndexOf('/')+1,url.indexOf('#') > 0 ?url.indexOf('#'):url.length);
			var p = ['app/config/'+m];
			require(p,function(module){
				module.render();
			});
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