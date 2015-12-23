(function(){
	"use strict";
	define('jquery','headroom',function($){
		var o = function(){
			this.render=function(){
				$("div.jumbotron").headroom({
					  "tolerance": 5,
					  "offset": 205,
					  "classes": {
					    "initial": "animated",
					    "pinned": "swingInX",
					    "unpinned": "swingOutX"
					  }
				});
			};
		};

		return new o();
	});
})();