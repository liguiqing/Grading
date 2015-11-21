(function () {
	var moudles= ["jquery"];
	define(moudles, function ($) {
		var self = {
			log : function (msg) {
				if (window.console) {
					console.log(msg);
				}
			}
		};
		return self;

	});
})();
