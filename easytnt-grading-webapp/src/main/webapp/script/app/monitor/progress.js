(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichart'],function($,ajax,ui){
		function o(){
			var chartDef = {
					render : 'progressChart',
					data: [],
					labels:[],
					title : {
						text:'评卷进度',
						color:'#dcd6cb',
						textAlign:'left',
						padding:'0 40',
						font:'微软雅黑',
						border:{
							enable:true,
							width:[0,0,4,0],
							color:'#698389'
						},
						height:40
					},
					
					padding:'8 0',
					width : $('#progressChart').width(),
					height : $('#progressChart').height(),
					column_width:70,
					gradient : true,//应用背景渐变
					gradient_mode:'LinearGradientDownUp',//渐变类型
					color_factor : 0.1,//渐变因子
					background_color : '#425154',
					sub_option:{
						label:{color:'#f9f9f9',fontsize:12,fontweight:600},
						border : false
					},
					label:{color:'#dcd6cb',font:'微软雅黑',fontsize:12,fontweight:600},
					legend:{
						enable:true,
						background_color : null,
						line_height:25,
						color:'#dcd6cb',
						fontsize:12,
						font:'微软雅黑',
						fontweight:600,
						border : {
							enable : false
						}
					},
					coordinate:{
						background_color : 0,
						grid_color:'#888888',
						axis : {
							color : '#c0d0e0',
							width : 0
						}, 
						scale:[{
							 position:'left',	
							 scale_enable : false,
							 start_scale:0,
							 scale_space:10,
							 end_scale:100,
							 label:{color:'#dcd6cb',fontsize:11,fontweight:600}
						}],
						width:'80%',
						height:'76%'
					}	
			};
			function drawChart(){				
				ajax.getJson("/monitor/progress/data",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var data = [{"name":"未完成数",value:[],color:'#32bdbc'},{"name":"已完成数",value:[],color:'#d75a5e'}];
						 var max = 0;
						 var lables = [];
						 $.each(r.data.rows,function(i){
							 var d = this;
							 data[0].value[i]=d[1]-d[2];
							 data[1].value[i]=d[2];
							 lables[i]=d[0];
							 if(d[1]>max)
								 max = d[1];
						 });
						 chartDef.data = data;
						 chartDef.labels = lables;
						 chartDef.coordinate.scale[0].end_scale=max;
						 var chart = new iChart.ColumnStacked2D(chartDef);
						 chart.draw();
						 //iChart.showZhuZhuangTu(data.data,data.min,data.max,data.space,data.unit,"progressChart"); 						 
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