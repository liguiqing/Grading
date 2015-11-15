(function() {
	"use strict";
	define([ 'jquery', 'dialog','select','icheck','pager' ], function($, dialog) {
		var DialogSize = {SM:'sm',MD:'md',LG:'lg'};
		var button = {
				type : 'button',
				text :  "确定",
				clazz : 'btn-default'
		};
		var self ;
		var ui = self = {
			modal:function(title, content, size,buttons){
				var opts = {
						size : size||DialogSize.SM,
						header : {
							show : true,
							text : title||""
						},
						body :content||""
					};
				if($.isArray(buttons)){
					opts['footer'] = {
							show : true,
							buttons:[]
					};
					$.each(buttons,function(i){
						var btn = {};
						$.extend(true,btn,button,this);
						opts.footer.buttons[i] =btn;
					});
				}
				return dialog.modal(opts);
			},
			show:function(title,content, size){
				var opts = {
						size : size||DialogSize.SM,
						header : {
							show : true,
							text : title||""
						},
						footer:{show:false,buttons:[]},
						body :content||""
					};
				return dialog.modal(opts);
			},
			fileUpload:function(html,opts,callback){
				var fileInputCss = {
					position:"absolute",
					top:"0",
					right:"0",
					margin:"0",
					padding:"0",
					cursor:"pointer",
					opacity:"0",
					width:"110px",
					height:"34px",
					display:"inline"
				};
				var fileHtml = $(html);

				fileHtml.find('input[type=file]').css(fileInputCss);
				fileHtml.find('.file-preview-filename').css({"height":"36px"});
				fileHtml.find('.file-preview-clear').click(function(e){
					fileHtml.find('.file-preview-filename').val('');
					fileHtml.find('.file-preview-clear').hide();
					fileHtml.find('.file-preview-input input:file').val('');
					fileHtml.find('.file-preview-input-title').text('请选择文件');
					fileHtml.find('button.btn-import').removeAttr('disabled');
					fileHtml.find('#SheetNames')[0].style.display = 'none';
				});
				
				fileHtml.find('.file-preview-input input[type=file]').change(function(e){
					var file = this.files[0];
					
					var reader = new FileReader();
					reader.onload = function(e){
						fileHtml.find('.file-preview-input-title').text('更换文件');
						fileHtml.find('.file-preview-clear').show();
						fileHtml.find('.file-preview-filename').val(file.name);
						fileHtml.find('button.btn-import').removeAttr('disabled');
					};
					reader.readAsDataURL(file);
				});
			},
			pretty:function(html){
				var $html = $(html);
				$html.find('select.selectpicker').each(function(){
					var $select = $(this);
					if($select.attr('disabled')){
						$select.selectpicker().css({'background-color':'#eee'});
						$select.next().css({'background-color':'#eee'});
					}else{
						$select.selectpicker();
						if($select.find('>option').size() > 10){
							$select.next().find('div.dropdown-menu ul').css({'overflow-y':'auto','height':'200px'});
						}
						$select.next().css({'width':'100%'});
					}
				});
				
				$html.find(':checkbox,:radio').iCheck({
					checkboxClass:'icheckbox_minimal-blue',
					radioClass:'iradio_minimal-blue',
					increaseArea:'20%'
				});
				
				if(browser.isMobile()){
					$html.find('select.selectpicker').selectpicker('mobile');
				}
			},
			pager: function(){
				var p =  function (){
					var pagerEl = undefined;
					var callback = function(){};
					var pagenation = {
							pageSize:10,
							pageCount:1,
							pageNum:1,
							concreator:this
					};
					
					this.init = function (){
						pagenation.pageNum = 1;
						pagenation.pageCount = 1;
						return pagenation;
					};
					
					this.create = function(){//arg1 pagerId,arg2 callback
						if(arguments.length > 0){
							for(var i = 0;i<arguments.length;i++){
								if(typeof arguments[i] === 'string'){									
									pagerEl = $('#'+arguments[i]);
									continue;
								}
								
								if(typeof arguments[i] === 'function'){
									callback = arguments[i];
									continue;
								}
							}
						}
						
						if(!pagerEl.size())
							return undefined;
						
						if(pagerEl.find("#pageSize").size() > 0 ){
							pagenation.pageSize = parseInt(pagerEl.find("#pageSize").val());
						}
						
						if(pagerEl.find("#pageNum").size() > 0 ){
							pagenation.pageNum = parseInt(pagerEl.find("#pageNum").val());
						}
						
						if(pagerEl.find("#pageCount").size() > 0 ){
							pagenation.pageCount = parseInt(pagerEl.find("#pageCount").val());
						}
						
						pagerEl.pager({
							pageNum:pagenation.pageNum,
							pageCount:pagenation.pageCount,
							click:function(pageNum,pageCount){
								pagenation.pageNum = pageNum;
								callback.call(this,{pageSize:pagenation.pageSize,pageNum:pagenation.pageNum});
							}
						});
						
						return pagenation;
					};
					
					this.redraw = function(pagerHtml){
						var pagerOuter = pagerEl.parent().parent();
						pagerOuter.find('>div.pager-container').remove();
						pagerOuter.append(pagerHtml.find('div.pager-container'));
						return this.create(pagerOuter.find('div.pager').attr('id'));
					};
				};
				
				return new p();
			}

		};
		return ui;
	});
})();