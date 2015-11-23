(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui', 'dialog'],function($,ajax,ui,dialog){
		var o = function(){
			var $outer = $('div.subject-container');
			ui.pretty($outer);
			
			$outer.find('div.row').show();
			
			$outer.find(':checkbox').on('ifChecked',function(){
				logger.log('i checked');
			}).on('ifUnchecked',function(){
				logger.log('i unchecked');
			});
			
			$outer.find('ul.list-task>li').each(function(){
				var task = $(this);
				var cuttoid = task.attr('data-cuttoid');
				var sid = task.attr('data-sid');
				ajax.getJson('task/assignto/paper/'+sid+'/'+cuttoid,{},{},function(data){
					
				});
				
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