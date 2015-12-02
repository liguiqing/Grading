(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui', 'dialog'],function($,ajax,ui,dialog){
		var o = function(){
			var $outer = $('div.subject-container');
			
			$outer.find(':checkbox').on('ifChecked',function(){
				var teacherId = $(this).attr('data-tid');
				var li = $(this).parents('li[data-cuttoid]');
				var cuttoid = li.attr('data-cuttoid');
				ajax.postJson("task/assignto/"+cuttoid+"/"+teacherId,{},{beforeMsg:{show:false},
				    successMsg:{tipText:"分配成功",show:true}},
					function(m){
						if(m.status.success){
							logger.log('i unchecked');
					    }
			        });
			}).on('ifUnchecked',function(){
				var teacherId = $(this).attr('data-tid');
				var li = $(this).parents('li[data-cuttoid]');
				var cuttoid = li.attr('data-cuttoid');
				ajax.send("task/unassignto/"+cuttoid+"/"+teacherId,{},'json','json','DELETE',{beforeMsg:{show:false},
				    successMsg:{tipText:"取消成功",show:true}},
					function(m){
						if(m.status.success){
							logger.log('i unchecked');
					    }
			        });
			});
			var tasks = $outer.find('ul.list-task>li');
			tasks.each(function(a){
				var task = $(this);
				var cuttoid = task.attr('data-cuttoid');
				ajax.getJson('task/assigned/'+cuttoid,{},{beforeMsg:{show:false},
				    successMsg:{show:false}},function(data){
				    	$.each(data.tasks,function(i,t){
				    		var tc = task.find(':checkbox[data-tid='+t.teacher+']');
				    		if(tc.size()){
				    			tc[0].checked='checked';
				    		}
				    	});
				    	ui.pretty(task);
				    	if(tasks.size()==a+1){
				    		$outer.find('div.row').show();
				    	}
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