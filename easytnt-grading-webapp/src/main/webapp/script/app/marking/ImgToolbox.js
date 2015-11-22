(function() {
	"use strict";
	define([ 'jquery', 'logger'], function($, logger) {

		var imgToolbox = function() {
			var $toolbox;
			var _imgViewer;
			function _init() {
				$toolbox = $('div.img-panel-toolbox');
				$toolbox.on('click','div.pull-left i',function(){
					var $this = $(this);
					if($this.hasClass('icon-check-empty')){
						$this.addClass('icon-remove').removeClass('icon-check-empty');
						$toolbox.find('div.panel-body ').show();
					}else{
						$this.addClass('icon-check-empty').removeClass('icon-remove');
						$toolbox.find('div.panel-body ').hide();
					}
					
				});
				return;
				
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
					//_imgViewer.next(_imgSrc);
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
			this.init = function(imgViewer) {
				_imgViewer = imgViewer;
				_init();
			};
			
			this.switchTo = function(imgSrc){
				_imgViewer.next(imgSrc);
			};
			
		};
		return new imgToolbox();
	});
})();