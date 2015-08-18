(function() {
	"use strict";
	var deps = [ 'jquery','logger','app/marking/ImgView','app/marking/point','ui','ajaxwrapper'];
	var __browser = getBrowser();
	define(deps, function($,logger,ImgView,point,ui,ajaxWrapper) {
		var Grading = function() {
			var imgPanel = $('div.img-panel');
			var markingPanel = $('div.point-panel-marking');
			var markingBody = markingPanel.find('div.panel-body');
			var pointCompletedcPanel = $('div.point-panel-completed');
			var pointDescBody = pointCompletedcPanel.find('div.panel-body');
			var statusPanel = $('.navbar-fixed-bottom');
			var navigationPanel = $('#navigation');
			var imgContainer = $('#imgContainer');
			var imgToolbox = $('div.img-panel-toolbox');
			this.render = function(model) {
				setImgPanelHeight();
				point.init();
				$(window).resize(setImgPanelHeight);
				markingPanel.on('click','button.point-record',function(){
					point.validate(function(data){
						if(data.success){
							ajaxWrapper.postJson('marking',data.item,{beforeMsg:{tipText:"系统正在计分...."},successMsg:{tipText:"第五题计分成功"}});
						}else{
							ui.modal( '计分错误',data.message,'sm', [ {
										text :  "按总分计",
										clazz : 'btn-primary',
										callback : function() {
											var $this = $(this);
											ajaxWrapper.postJson('marking',data.item,{beforeMsg:{tipText:"系统正在计分...."},successMsg:{tipText:"第五题计分成功"}},
													function(data){
												$this.trigger('close');
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
			
			function setImgPanelHeight(){			
	            var h1 = getClientHeight()-navigationPanel.height()-statusPanel.height();
	            imgPanel.height(h1);
	            var h2 =  h1-markingPanel.height();
	            pointCompletedcPanel.height(h2-2);
	            return;
				if(!__browser.ie){
					pointDescBody.height(pointCompletedcPanel.height()-pointDescBody.prev().height()-60);
				}else if(__browser.ie * 1 < 9){
					pointDescBody.height(pointCompletedcPanel.height()-pointDescBody.prev().height()-100);
				}else {
					pointDescBody.height(pointCompletedcPanel.height()-pointDescBody.prev().height()-60);
				}
	       
	            logger.log(pointDescBody.height());
			};
		};
		return new Grading();
	});
})();