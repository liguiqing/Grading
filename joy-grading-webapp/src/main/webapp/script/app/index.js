(function(){
	"use strict";
	var deps = ['jquery','app/marking/point'];
	define(deps,function($,point){
		var o = function(){
			var imgPanel = $('div.img-panel');
			var markingPanel = $('div.point-panel-marking');
			var markingBody = markingPanel.find('div.panel-body');
			var pointDescPanel = $('div.point-panel-desc');
			var pointDescBody = pointDescPanel.find('div.panel-body');
			var statusPanel = $('.navbar-fixed-bottom');
			var navigationPanel = $('#navigation');
			var imgZoomPanel = $('div.img-zoom ');
			var imgToolbox = $('div.img-panel-toolbox');
			this.render = function(){
				setImgPanelHeight();
				point.init();
				$(window).resize(setImgPanelHeight);
				imgPanel.scroll(function(){
					var top = $(this).scrollTop();
					imgToolbox.css({top:top});
				});
				imgToolbox.find('i.glyphicon').click(function(){
					$(this).toggleClass('icon-double-angle-right');
					if(imgToolbox.width()==78){
						imgToolbox.css({width:'10px'});
					}else{
						imgToolbox.css({width:'80px'});
					}
					
				});
			};
			
			function setImgPanelHeight(){
	            var h1 = $('body').height()-navigationPanel.height()-statusPanel.height();
	            imgPanel.height(h1);
	            var h2 =  h1-markingPanel.height()-pointDescBody.prev().height();
	            if(h2 > 75){
	            	pointDescBody.height(h2-statusPanel.height()-10);
	            } else{
	            	pointDescBody.height(75);
	            }
	            console.log(pointDescBody.height());
			}
		};

		return new o();
	});
})();