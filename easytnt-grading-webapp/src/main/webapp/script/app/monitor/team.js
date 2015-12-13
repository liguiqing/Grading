(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichart'],function($,ajax,ui){
		var chartDef = {
				render : 'teamChart',
				data: [],
				align:'center',
				width : 800,
				height : 500,
				shadow:true,
				shadow_color : '#20262f',
				shadow_blur : 4,
				shadow_offsetx : 0,
				shadow_offsety : 2,
				background_color:'#383e46',
				legend : {
					enable : true,
					background_color:'#d4e0ec'
				},
				tip:{
					enable:true,
					shadow:true
				},
				crosshair:{
					enable:true,
					line_color:'#62bce9'
				},
				sub_option : {
					label:false,
					hollow_inside:false,
					point_size:8
				},
				coordinate:{
					grid_color:'#cccccc',
					axis:{
						color:'#cccccc',
						width:[0,0,2,2]
					},
					grids:{
						vertical:{
							way:'share_alike',
					 		value:5
						}
					},
					scale:[{
						 position:'left',	
						 start_scale:0,
						 end_scale:100,
						 scale_space:10,
						 scale_size:2,
						 label : {color:'#ffffff',fontsize:11},
						 scale_color:'#9f9f9f'
					},{
						 position:'bottom',	
						 label : {color:'#ffffff',fontsize:11},
						 labels:[]
					}]
				}	
		};
		function o(){
			
			$('div.chart-container >div').css({'height':'100%'});
			function drawChart(){				
				ajax.getJson("/monitor/team/data",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var data = r.data;
						 chartDef.height=$('#teamChart').height();
						 chartDef.width = $('#teamChart').width();
						 chartDef.data = data.data;
						 chartDef.coordinate.scale[1].labels=data.labels;
						 var chart = new iChart.LineBasic2D(chartDef);
						 chart.draw();
						 //iChart.showZheXianTu(data.data,data.labels,data.min,data.max,data.space,data.unit,"teamChart"); 					 
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