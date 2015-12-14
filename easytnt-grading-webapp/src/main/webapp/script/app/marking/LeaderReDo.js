(function() {
	"use strict";
	define([ 'jquery', 'logger','ajaxwrapper'], function($, logger,ajax) {
		var _imgServer = $('#imgServer').val();
		var $reDoPanel = $('#repeats');
		var ReDo = function() {
			if($reDoPanel.size() == 0){
				return;
			}
			$('#navigation ul.navbar-nav li:last a').click(function(){
				logger.log(this.baseURI);
				var url = this.baseURI.substring(this.baseURI.indexOf('grading')+8);
				ajax.getJson(url+"/exception",{},{show:false},function(data){					
					logger.log(data);
				});
			});
			
		};
		return new ReDo();
	});
})();