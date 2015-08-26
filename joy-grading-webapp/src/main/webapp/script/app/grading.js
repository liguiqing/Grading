(function() {
	"use strict";
	var deps = [ 'jquery','logger','app/marking/ImgView','app/marking/point','ui','ajaxwrapper'];
	var __browser = getBrowser();
	define(deps, function($,logger,ImgView,point,ui,ajaxWrapper) {
		var _grading;
		var _pointModel = {shortKeys:[
			                  	       {"name":"full","type":"point","keys":["A"]},{"name":"zero","type":"point","keys":["D"]},
			                  	       {"name":"up","type":"move","keys":["W"]},{"name":"down","type":"move","keys":["S"]}],
			                  	       onKey:function(eventCode){
			                  	        	switch(eventCode){
			                  	        	case 27://退出
			                  	        	case 46://删除
			                  	        		point.reset();
			                  	        		break;
			                  	        	case 13://回车
			                  	        		_grading.record();
			                  	        		break;
			                  	        	case 37://左箭头
			                  	        		shortKeyPoint("A");//暂时的实现
			                  	        		break;
			                  	        	case 38://上箭头
			                  	        		point.prev();
			                  	        		break;
			                  	        	case 39://右箭头
			                  	        		shortKeyPoint("D");//暂时的实现
			                  	        		break;
			                  	        	case 40://下箭头
			                  	        		point.next();
			                  	        		break;
			                  	        	default:
			                  	        	    //转换按键值为字母
			                					var char  = String.fromCharCode(eventCode);
			                  	                //循环所有快捷键定义
				                				$.each(this.shortKeys,function(i,key){
				                					//如果按键值在快捷键定义中
				                					if($.inArray(char,key.keys)){
				                						if(key.type == 'move'){
				                							if(this.name == 'up'){
				                								point.prev();
				                							}else{
				                								point.next();
				                							}
				                						}else{
				                							
				                						}
				                						shortKeyPoint(char);
				                						return false;
				                					}					
				                				});			                  	        	  
			                  	        	}
			                  	        }          
			                  	};
		
		function shortKeyPoint(key){
			var $curInput = $('aside.point-panel input:focus');//得到当得聚焦状态的打分输入框
			var keyDefs = $curInput.parent().find('b');//得到打分输入框的定义的快捷键
			keyDefs.each(function(){
				var $b = $(this);
				if($b.text().toUpperCase() == key){
					$b.parent().click();
					return false;
				}
			});
		};
		
		function onShortKeys(model){
			$(document).on('keydown',function(e){
				model.onKey(e.which||e.keyCode);
			});
		};
		
		var Grading = function() {
			var imgPanel = $('aside.img-panel');
			var markingPanel = $('div.point-panel-marking');
			var markingBody = markingPanel.find('div.panel-body');
			var pointCompletedPanel = $('div.point-panel-completed');
			var pointCompletedBody = pointCompletedPanel.find('div.panel-body');
			var statusPanel = $('.navbar-fixed-bottom');
			var navigationPanel = $('#navigation');
			var imgContainer = $('#imgContainer');
			var imgToolbox = $('div.img-panel-toolbox');
			
			this.nextPaper = function(){
				point.reset();
				this.clear();
				//ImgView.init({containerId:"imgContainer",imgSrc:app.contextPath + "/static/css/img/sj.jpg"});
				ImgView.next(app.contextPath + "/static/css/img/sj.jpg");
			};
			
			this.clear = function(){
				
			};
			
			this.record = function(){
				point.validate(function(data){
					if(data.success){
						ajaxWrapper.postJson('marking',data.item,{beforeMsg:{tipText:"系统正在计分...."},successMsg:{tipText:"第五题计分成功"}},
								function(m){
							if(m.status.success){
								_grading.nextPaper();
							}
						});
					}else{
						ui.modal( '计分错误',data.message,'sm', [ {
									text :  data.confirmText,
									clazz : 'btn-primary',
									callback : function() {
										var $this = $(this);
										ajaxWrapper.postJson('marking',data.item,{beforeMsg:{tipText:"系统正在计分...."},successMsg:{tipText:"第五题计分成功"}},
												function(m){
											if(m.status.success){
												_grading.nextPaper();
												$this.trigger('close');
											}
											
										});
									}
								}, {
									text : "重改",
									callback : function() {
										$(this).trigger('close');
									}
								} ]);
					}
				});
			};
			
			this.render = function(model) {
				setImgPanelHeight();
				
				var thisModel = {};
				$.extend(true,thisModel,model,_pointModel);
				onShortKeys(thisModel);

				point.init(this);
				$(window).resize(setImgPanelHeight);
				markingPanel.on('click','div.point-record button',function(){
					_grading.record();
				});
				imgToolbox.find('div.panel-body ').on('click','ul .icon-refresh',function(){
					imgContainer.children().remove();
					ImgView.init({containerId:"imgContainer",imgSrc:app.contextPath + "/static/css/img/sj.jpg"});
				});
				imgToolbox.find('i.glyphicon').click(function(){
					var $this = $(this);
					$this.toggleClass('icon-double-angle-right');
					imgToolbox.toggleClass('transparent-25');
					if(imgToolbox.hasClass('transparent-25')){
						$this.parent().parent().css({'padding-left':'2px'});
						imgToolbox.css({width:'10px'}).find('div.panel-body').hide();
					}else{
						imgToolbox.css({width:'60px'}).find('div.panel-body').show();
						$this.parent().parent().css({'padding-left':'15px'});
					}
					
				});
				ImgView.init({containerId:"imgContainer",imgSrc:app.contextPath + "/static/css/img/sj.jpg"});
			};
			
			function setImgPanelHeight()
			    var windowH = $(window).height();
	            var h1 = getClientHeight()-navigationPanel.height()-statusPanel.height();
	            imgPanel.height(h1);
	            var h2 =  h1-markingPanel.height();
	            pointCompletedPanel.height(h2-2);
	            
				if(!__browser.ie){
					pointCompletedBody.height(getClientHeight()-markingPanel.height()-pointCompletedBody.prev().height());
					logger.log("-----------------------------");
				}else if(__browser.ie * 1 < 9){
					pointCompletedBody.height(pointCompletedPanel.height()-pointCompletedBody.prev().height()-100);
				}else {
					pointCompletedBody.height(getClientHeight()-markingPanel.height()-pointCompletedBody.prev().height()*2);
					logger.log("-----------++------------------");
				}
	       
	            logger.log(pointCompletedBody.height());
			};
		};
		_grading = new Grading();
		return _grading;
	});
})();