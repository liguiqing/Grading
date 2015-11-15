(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichartUtil'],function($,ajax,ui,iChart){
		function o(){
			function drawChart(){				
				ajax.getJson("/monitor/personalStabled/data/100001",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var data = r.data;
						 iChart.showMiamJiTu(data.data,data.labels,data.min,data.max,data.space,data.unit,"canvasDiv"); 						 
				});				
			};
			
			drawChart();
			
			setInterval(function(){
				drawChart();
			},60000);
			
		};

		return {
			render:function(){
				return new o();
			}
		};
	});
})();