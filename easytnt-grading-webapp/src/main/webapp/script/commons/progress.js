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
				finishedCallBack : null,
				data : {}
			};

			this.init = function(_opts) {
				opts = $.extend({}, _opts);
				opts.container.append($template);
				opts.data.completed = 1;
				this.createProcess();
			}

			this.createProcess = function() {
				var url = "/progress/" + opts.entry;
				ajaxwrapper.getJson(url, opts.data, {}, function(data) {
					var text = data.progress.text;
					var percent = data.progress.percent + "%";
					var finished = data.progress.finished;
					var completed = data.progress.completed;
					opts.data.completed = completed;
					$bar.css({
						width : percent
					}).text(text);

					if (finished) {
						if ($.isFunction(opts.finishedCallBack)) {
							opts.finishedCallBack();
						}
					} else {
						opts.data.completed = completed;
						setTimeout(function() {
							_progress.createProcess();
						}, 3000);
					}
				});
			}

		}

		var _progress = new Progress();
		return _progress;
	});
})();