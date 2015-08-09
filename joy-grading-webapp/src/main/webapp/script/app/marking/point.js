(function() {
	"use strict";
	var deps = [ 'jquery' ];
	var PointModle = {};
	define(deps, function($) {
		var point = function() {
			this.count = function() {
				var total = 0;
				$('.point-panel-detail input').each(function() {
					total += this.value * 1;
				});
				$('.point-panel-total input').val(total);
			};
			this.init = function(model) {
				var self = this;
				$('.point-panel .point-btn').each(function() {
					var $btn = $(this);
					var dataToggle = $btn.attr('data-toggle');
					var $input = $('#' + dataToggle);
					$btn.click(function() {
						if ($btn.attr('data-value')) {
							$input.val($input.attr($btn.attr('data-value')) || 0);
						} else {
							$input.val($input.attr("data-to") || 0);
						}
						self.count();
					});
				});
			};
		};
		return new point();
	});
})();
