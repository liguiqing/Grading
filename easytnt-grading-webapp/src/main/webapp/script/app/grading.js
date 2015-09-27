(function() {
	"use strict";
	var __browser = getBrowser();
	define([ 'jquery','logger','app/marking/ImgToolbox','app/marking/PointPanel','ui','ajaxwrapper'],
			function($,logger,imgToolbox,point,ui,ajaxWrapper) {
		var _grading;
		var _imgViewer;
		
		
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
				pointPanelKeyShort();
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
					save(score);
					$(this).trigger('close');
				}},{text:'重改',callback:function(){
					point.next();
					$(this).trigger('close');
				}}];
				var message =  "总分：<b style='color:#c83025'>"+ score.value +"</b>";
				
				var modal = ui.modal( score.title+'得分情况',message,'sm',btns);
				$(document).off('keyup').on('keyup',function(e){
					var eventCode = e.which||e.keyCode;
					if(eventCode == 27){
						point.actived();
						pointPanelKeyShort();
						modal.close();
					}else if(eventCode == 13){						
						save(score);
						modal.close();						
					}							
	            });	
			};
			
			function pointPanelKeyShort(){
				$(document).off('keyup').on('keyup',function(e){
					var eventCode = e.which || e.keyCode;
					var char  = String.fromCharCode(eventCode);
					switch(eventCode){
					case 38:
						point.prev();						
						break;
					case 40:
						point.next();
						break;
					case  13:
						//按回车往下一个输入框移动，最后一个输入框完成后，再按回车，进行计分操作
						if(point.hasNext()){
							point.next(true);
						}else{
							_grading.record(point.total());
							//console.log(point.total());
						}
						break;
					default:
						point.checked();
					}
				});				
			};
			
			this.render = function(model) {
				setImgPanelHeight();
				point.newInstance().addFocusListener(function(){
					imgToolbox.hilight(this.position,this.dataTo);
				});
				point.actived();
				pointPanelKeyShort();
				
				
//				$(window).resize(setImgPanelHeight);
//				markingPanel.on('click','div.point-record button',function(){
//					var score = point.validate();
//					_grading.record(score);
//				});
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