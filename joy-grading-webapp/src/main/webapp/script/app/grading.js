(function() {
	"use strict";
	var __browser = getBrowser();
	define([ 'jquery','logger','app/marking/ImgToolbox','app/marking/point','ui','ajaxwrapper'],
			function($,logger,imgToolbox,point,ui,ajaxWrapper) {
		var _grading;
		var _imgViewer;
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
						    //shortKeyPoint("A");//暂时的实现
							point.count();
          	        		point.next();
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
			console.log(model);
			$(document).off('keydown').on('keydown',function(e){
				model.onKey(e.which||e.keyCode);
			});
		};
		
		function save(data){
			ajaxWrapper.postJson('marking',data,{beforeMsg:{tipText:"系统正在计分...."},successMsg:{tipText:"计分成功"}},
					function(m){
				if(m.status.success){
					_grading.nextPaper();
				}
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

			this.nextPaper = function(){
				point.reset();
				this.clear();
				imgToolbox.switchTo(app.contextPath + "/static/css/img/sj.jpg");
			};
			
			this.clear = function(){
				var thisModel = {};
				$.extend(true,thisModel,{},_pointModel);
				onShortKeys(thisModel);
				point.init(this);
			};
			
			this.record = function(score){
				var btns = [{text:'确认',clazz : 'btn-primary',callback:function(){
					save(score.toJson());
					$(this).trigger('close');
				}},{text:'重改',callback:function(){
					point.next();
					$(this).trigger('close');
				}}];
				var message =  "总分：<b style='color:#c83025'>"+ score.total +"</b>";
				if(!score.eq()){
					btns = [{text:'重改',callback:function(){
						point.next();
						$(this).trigger('close');
					}}];
					message = "<p>各得分点合计：<b style='color:#c83025'>" + score.pointsTotal() 
					                   + "</b>分</p><p>总分：<b style='color:#c83025'>" + score.total+"</b>分</p>";
				}
				var modal = ui.modal( score.title+'得分情况',message,'sm',btns);
				$(document).off('keydown').on('keydown',function(e){
					var eventCode = e.which||e.keyCode;
					if(eventCode == 27){
						modal.close();
						//point.next();
					}else if(eventCode == 13){
						if(score.eq()){
							save(score.toJson());
							modal.close();
						}
					}							
	            });	
			};
			
			this.render = function(model) {
				setImgPanelHeight();
				//imgPanel.hide();
				var thisModel = {};
				$.extend(true,thisModel,model,_pointModel);
				onShortKeys(thisModel);

				point.init(this);
				$(window).resize(setImgPanelHeight);
				markingPanel.on('click','div.point-record button',function(){
					var score = point.validate();
					_grading.record(score);
				});
				imgToolbox.init("imgContainer",app.contextPath + "/static/css/img/sj.jpg");
			};
			
			function setImgPanelHeight(){
			    var windowH = $(window).height();
	            var h1 = getClientHeight()-navigationPanel.height()-statusPanel.height();
	            imgPanel.height(h1);
	            
	            var h2 =  h1-markingPanel.height();
	            pointCompletedPanel.height(h2-2);

				pointCompletedBody.height(getClientHeight()-navigationPanel.height()-statusPanel.height()-markingPanel.height()-pointCompletedBody.prev().height()-45-2);
				
			};
		};
		_grading = new Grading();
		return _grading;
	});
})();