(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichartUtil'],function($,ajax,ui,iChart){
		function o(){
			function drawChart(){				
				ajax.getJson("/monitor/progress/data",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var data = r.data;
						 iChart.showZhuZhuangTu(data.data,data.min,data.max,data.space,data.unit,"progressChart"); 						 
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