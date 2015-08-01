(function(){
	"use strict";
	var deps = ['jquery'];
	define(deps,function($){
		var o = function(){
			var imgPanel = $('div.img-panel');
			var pointPanel = $('div.point-panel');
			var statusPanel = $('.navbar-fixed-bottom');
			var navigationPanel = $('#navigation');
			var imgZoomPanel = $('div.img-zoom ');
			this.render = function(){
				var footerH = statusPanel.height();
				var docH = $('body').height();
				var navigationH = navigationPanel.height();
				var h = docH-navigationH-footerH;
				imgPanel.height(h).find('img').show();
				pointPanel.find('ul').height(h/3*2);
				imgZoomPanel.find('img').width(pointPanel.width()).height(h/3).show();
			};
		};
		return new o();
	});
})();