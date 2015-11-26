(function(){
	"use strict";
	define( ['jquery','ajax','dialog'],function($,dialog){
		var o = function(){
			var tasks = $('list-task >li');
			if(tasks.size()==1){
				var taskId = tasks.attr('data-taskid');
				dialog.fadedialog({
					delay:5000,
					tipText:"5秒后开始进行评卷",
					callback:function(){
						window.location.href = app.rootPath+"/task/"+taskId;
					}
				});
			}
		};
		return{
			render:function(){
				return new o();
			}
		};
		
	});
})();