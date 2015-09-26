(function() {
	"use strict";
	define([ 'jquery', 'logger', 'app/marking/ImgView' ], function($, logger,ImgView) {

		var imgToolbox = function() {
			var $toolbox;
			var _imgViewer;
			var _imgSrc;
			function _init() {
				$toolbox = $('div.img-panel-toolbox');
				$toolbox.find('i.glyphicon').click(function() {
					var $this = $(this);
					$this.toggleClass('icon-double-angle-right');
					$toolbox.toggleClass('transparent-25');
					if ($toolbox.hasClass('transparent-25')) {
						$this.parent().parent().css({
							'padding-left' : '2px'
						});
						$toolbox.css({
							width : '10px'
						}).find('div.panel-body').hide();
					} else {
						$toolbox.css({
							width : '60px'
						}).find('div.panel-body').show();
						$this.parent().parent().css({
							'padding-left' : '15px'
						});
					}
				});
				$toolbox.find('div.panel-body ').on('click','ul .icon-refresh',function(){
					_imgViewer.next(_imgSrc);
				}).on('click','ul .icon-fullscreen',function(){
					_imgViewer.autoAdaptationWidthAndHeight();
				}).on('click','ul .icon-zoom-in',function(){
					_imgViewer.zoomOut();
				}).on('click','ul .icon-zoom-out',function(){	
					_imgViewer.zoomIn();
				});	
			};

			/**
			 * 首次使用时，必须调用
			 */
			this.init = function(imgContainerId,imgSrc) {
				_imgSrc = imgSrc;
				_imgViewer = ImgView.init({containerId:imgContainerId,imgSrc:_imgSrc});
				_init();
			};
			
			/**
			 * 切到新图
			 */
			this.switchTo = function(imgSrc){
				_imgSrc = imgSrc;
				_imgViewer.next(_imgSrc);
			};
			
			this.shift = function() {
				$toolbox.find('i.glyphicon').click();
			};
			
			this.refresh = function(){
				$toolbox.find('div.panel-body ul.icon-refresh ').click();
			};

			this.zoomIn = function() {
				$toolbox.find('div.panel-body ul.icon-zoom-in ').click();
			};

			this.zoomOut = function() {
				$toolbox.find('div.panel-body ul.icon-zoom-out ').click();
			};

			this.fullscreen = function() {
				$toolbox.find('div.panel-body ul.icon-fullscreen ').click();
			};
		};
		return new imgToolbox();
	});
})();