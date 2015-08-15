(function() {
	"use strict";
	var deps = [ 'jquery' ];
	var PointModle = {};
	define(deps, function($) {
		var point = function() {
			this.count = function() {
				var total = 0;
				$('div.point-panel-detail input.point-input').each(function() {
					total += this.value * 1;
				});
				$('div.point-panel-total input.point-input').val(total);
			};
			this.reset = function(){
				$('input.point-input').val(0);
			};
			this.init = function(model) {
				var self = this;
				$('div.point-panel .point-input').css({'border-top-left-radius':'4px','border-bottom-left-radius':'4px'});
				$('div.point-panel').on('click', '.point-btn', function() {
					var $btn = $(this);
					var dataToggle = $btn.attr('data-toggle');
					var $input = $('#' + dataToggle);

					if ($btn.attr('data-value')) {
						$input.val($input.attr($btn.attr('data-value')) || 0);
					} else {
						$input.val($input.attr("data-to") || 0);
					}
					if($btn.parents('div.point-panel-total').size() == 0){
						self.count();
					}else{
						if($btn.attr('data-value')=='data-from'){
							self.reset();
						}
					}
				}).on('keyup','.point-input',function(e){
					onlyNumber(this);
					if(this.value * 1 > $(this).attr('data-to') * 1){
						this.value = "";
						return true;
					}
					var interval = $(this).attr('data-interval') * 1;
					this.value = Math.floor(this.value * 1) +  interval;
				}).on('keydown','.point-input',function(e){

				});
			};
		};
		return new point();
	});
})();
