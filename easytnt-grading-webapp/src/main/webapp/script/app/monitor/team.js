(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichartUtil'],function($,ajax,ui,iChart){
		function o(){
			
			$('div.chart-container >div').css({'height':'100%'});
			function drawChart(){				
				ajax.getJson("/monitor/team/data",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var data = r.data;
						 iChart.showZheXianTu(data.data,data.labels,data.min,data.max,data.space,data.unit,"teamChart"); 					 
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