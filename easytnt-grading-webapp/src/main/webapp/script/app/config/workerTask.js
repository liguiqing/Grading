(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui', 'dialog'],function($,ajaxWrapper,ui,dialog){
		var o = function(){
			var $outer = $('div.subject-container');
			ui.pretty($outer);
			
			$outer.find('div.row').show();
			
			$outer.find(':checkbox').on('ifChecked',function(){
				logger.log('i checked');
			}).on('ifUnchecked',function(){
				logger.log('i unchecked');
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