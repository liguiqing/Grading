(function(){
	"use strict";
	define(['jquery','ichartUtil','ajaxwrapper'],function($,ichartDraw,ajaxWrapper){
		var o = function(){
			var url = window.location.href;
			var m = url.substring(url.lastIndexOf('/')+1,url.indexOf('#') > 0 ?url.indexOf('#'):url.length);
			var p = ['app/monitor/'+m];
			require(p,function(module){
				module.render();
			});
			
		};
		function draw(m){
			ajaxWrapper.postJson("/monitor/"+m+"Show/"+123,123,{beforeMsg:{tipText:"",show:false},
				 successMsg:{tipText:"",show:false}},
					function(data){
					 if(m=="team"){
						 ichartDraw.showMiamJiTu(data.data,data.labels,data.min,data.max,data.space,data.unit); 
					 }else if(m=="personalStabled"){
						 ichartDraw.showZheXianTu(data.data,data.labels,data.min,data.max,data.space,data.unit);
					 }
			});
		}
		function setWorkspaceWH(){
			var navigationPanel = $('#navigation .container');
			var statusPanel = $('footer.status-bar');
			
            var h = getClientHeight()-navigationPanel.height()-statusPanel.height()-45;
            $("div.config-panel").height(h);
            $("div.config-wrapper>div").height(h-60);
		};
		return {
			render:function(){
				setWorkspaceWH();
				return new o();
			}
		};
	});
})();