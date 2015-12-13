(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichart'],function($,ajax,ui){
		var chartDef = {
				render : 'stabled',
				data: [],
				align:'center',
				title:{
					text:'小组成员稳定性',
					font:'微软雅黑',
					color:'#d4e0ec',
					height:40,
					border:{
						enable:true,
						width:[0,0,2,0],
						color:'#404b5a'
					}
				},
				area_opacity:0.15,
				legend : {
					enable : true,
					background_color:'#d4e0ec'
				},
				padding:'2 10',
				border:{
					width:[0,20,0,20],
					color:'#222831'
				},
				width : 800,
				height : 400,
				shadow:true,
				shadow_color:'#15353a',
				shadow_blur:8,
				background_color : '#38404b',
				gradient : true,//应用背景渐变
				gradient_mode:'LinearGradientDownUp',//渐变类型
				color_factor : 0.2,//渐变因子
				tip:{
					enable:true,
					shadow:true,
					listeners:{
						parseText:function(tip,name,value,text,i){
							return "<span style='color:#005268;font-size:16px;font-weight:600;'>小时评卷："+value+"张</span>";
						}
					}
				},
				crosshair:{
					enable:true,
					line_width:2,
					line_color:'#6cc4f4'//十字线的颜色
				},
				sub_option : {
					smooth:true,
					hollow_inside:false,
					hollow_color : '#FEFEFE',
					point_size:10,
					label:false
				},
				coordinate:{
					width:'90%',
					height:'90%',
					offsetx:-30,
					axis:{
						color:'#9f9f9f',
						width:[0,0,2,2]
					},
					grid_color : '#9f9f9f',
					scale:[{
						 position:'left',
						 label:{color:'#d4e0ec',font:'微软雅黑',fontweight:600},
						 start_scale:0,
						 end_scale:600,
						 scale_space:100,
						 scale_size:2,
						 scale_color:'#9f9f9f'
					},{
						 position:'bottom',	
						 label:{color:'#d4e0ec',font:'微软雅黑',fontweight:600},
						 labels:[]
					}]
				}
		};
		function o(){
			function drawChart(){				
				ajax.getJson("/monitor/personalStabled/data",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var data = r.data;
						 chartDef.height=$('#stabled').height();
						 chartDef.width = $('#stabled').width();
						 chartDef.data = data.data;
						 chartDef.coordinate.scale[1].labels=data.labels;
						 
						 var chart = new iChart.Area2D(chartDef);
						 chart.draw();
						 //iChart.showMiamJiTu(data.data,data.labels,data.min,data.max,data.space,data.unit,"canvasDiv"); 						 
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