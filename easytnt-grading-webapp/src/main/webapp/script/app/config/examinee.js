(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui'],function($,ajaxWrapper,ui){
		function o(){
			var self = this;
			var $outer = $('div.subject-container');
			ui.pretty($outer);
			ui.fileUpload($outer);
			
			$('#uploadForm').submit(function(){
				return false;
			});
			$('#upload').click(function(){
				if($('input.file-preview-filename').val().length == 0){
					return;
				}
				
				ajaxWrapper.upload('examinee/upload','fileName',
						{beforeMsg:{tipText:"",show:true},successMsg:{tipText:"上传成功",show:true}},
						function(){
							window.location.reload();
						});
			});
			
			$('#imports').click(function(e){
				var Mappers = [];
				var i = 0;
				$('#mapperForm select.selectpicker').each(function(){
					var $m = $(this);
					if($m.val() !=='-1'){
						Mappers[i] = {};
						Mappers[i]['fieldName'] = $m.attr('name');
						Mappers[i]['targetName'] = $m.val();
						Mappers[i]['seq'] = $m.find(':selected')[0].index;
						i++;
					}
				});
				ajaxWrapper.postJson('examinee/importExaminee',Mappers,
						{beforeMsg:{tipText:"",show:true},successMsg:{tipText:"考生导入开始......",show:true}},
						function(){
							//window.location.reload();
						});
			});
			
			this.query = function(pager){
				logger.log('examinee.query');
				if(!pager){
					pager = _pager.concreator.init();
	        	}
				//TODO query
			};
			
			//加入页码块
			var _pager = new ui.pager().create('pager',self.query);
			
			$outer.show();
		};

		return {
			render:function(){
				
				return new o();
			}
		};
	});
})();