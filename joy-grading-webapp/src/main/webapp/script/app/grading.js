(function() {
	"use strict";
	var deps = [ 'jquery','logger','app/marking/ImgView','app/marking/point','dialog'];
	define(deps, function($,logger,ImgView,point,dialog) {
		var Grading = function() {
			var imgPanel = $('div.img-panel');
			var markingPanel = $('div.point-panel-marking');
			var markingBody = markingPanel.find('div.panel-body');
			var pointDescPanel = $('div.point-panel-desc');
			var pointDescBody = pointDescPanel.find('div.panel-body');
			var statusPanel = $('.navbar-fixed-bottom');
			var navigationPanel = $('#navigation');
			var imgContainer = $('#imgContainer');
			var imgToolbox = $('div.img-panel-toolbox');
			this.render = function(model) {
				setImgPanelHeight();
				point.init();
				$(window).resize(setImgPanelHeight);
				markingPanel.on('click','button.point-record',function(){
					point.validate(function(){
						
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
				
	            var h1 = getClientHeight()-navigationPanel.height()-statusPanel.height()-11;
	            imgPanel.height(h1);
	            var h2 =  h1-markingPanel.height();
	            pointDescPanel.height(h2-5);
	            pointDescBody.height(pointDescPanel.height()-pointDescBody.prev().height()-50);
	       
	            logger.log(pointDescBody.height());
			};
		};
		return new Grading();
	});
})();