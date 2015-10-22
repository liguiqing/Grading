(function() {
	"use strict";
	var dps = [ 'jquery', 'ajaxwrapper' ];
	define(dps, function($, ajaxwrapper) {

		var Progress = function() {
			var $template = $("<div></div>").addClass("progress");
			var $bar = $("<div></div>", {
				"role" : "progressbar",
				"aria-valuenow" : "60",
				"aria-valuemin" : "0",
				"aria-valuemax" : "100",
				css : {
					width : "0%"
				}
			}).addClass("progress-bar");
			$template.append($bar);
			var opts = {
				container : null,
				entry : null,
				data : {}
			};
			
			this.init = function(_opts) {
				opts = $.extend({}, _opts);
				opts.container.append($template);
				this.createProcess();
			}

			this.createProcess = function() {
				var url = "/progress/" + opts.entry;
				ajaxwrapper.postJson(url, opts.data || {}, {}, function(data) {
					var text = data.progress.text;
					var percent = data.progress.percent + "%";
					var finished = data.progress.finished;
					$bar.css({
						width : percent
					}).text(text);
					if (finished) {

					} else {
						// setTimeout(function() {
						// _progress.createProcess();
						// }, 3000);
					}
				});
			}

		}

		var _progress = new Progress();
		return _progress;
	});
})();