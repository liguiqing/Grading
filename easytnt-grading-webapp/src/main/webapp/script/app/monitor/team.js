(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui','ichart'],function($,ajax,ui){
		var aera_color=["#a5c2d5","#cbab4f","#76a871","#9f7961","#a56f8f","#6f83a5","#6cc4f4"];
		var labels = [];
		var chartDef = {
				render : 'teamChart',
				data: [],
				align:'center',
				title:{
					text:'11',
					font:'微软雅黑',
					color:'#d4e0ec',
					height:40,
					border:{
						enable:true,
						width:[0,0,2,0],
						color:'#404b5a'
					}
				},
				footnote:{
					text:'',
					font:'微软雅黑',
					color:'#d4e0ec',
					height:30,
					border:{
						enable:true,
						width:[2,0,0,0],
						color:'#1e242c'
					}
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
				tip:{
					enable:true,
					shadow:true,
					listeners:{
						parseText:function(tip,name,value,text,i){
							return "<span style='color:#005268;font-size:16px;font-weight:600;'>"+name+","+labels[i]+"分："+value+"张</span>";
						}
					}
				},
				label:{color:'#dcd6cb',font:'微软雅黑',fontsize:12,fontweight:600},
				legend : {
					enable : true,
					row:1,//设置在一行上显示，与column配合使用
					column : 'max',
					valign:'top',
					background_color:null,//设置透明背景
					offsetx:-30,//设置x轴偏移，满足位置需要
					border : false 
				},
				coordinate:{
					width:'86%',
					height:'76%',
					axis:{
						color:'#9f9f9f',
						width:[0,0,2,2]
					},
					grid_color : '#9f9f9f',
					scale:[{
						 position:'left',
						 label:{color:'#d4e0ec',font:'微软雅黑',fontweight:600},
						 start_scale:0,
						 scale_color:'#9f9f9f'
					},{
						 position:'bottom',	
						 label:{color:'#d4e0ec',font:'微软雅黑',fontweight:600},
						 labels:[]
					}]
				}	
		};
		function o(){
			
			$('div.chart-container >div').css({'height':'100%'});
			function drawChart(){				
				ajax.getJson("/monitor/team/data",{},{beforeMsg:{tipText:"",show:false},
					 successMsg:{tipText:"",show:false}},function(r){
						 var datas = [];
						 
						 $.each(r.datas.rows,function(i,row){
							 if(!labels.contains(row[1])){
								 labels[labels.length] = row[1]*1;
							 }
						 });
						 labels.sort(function(a,b){return a-b;});
						
						 
						 function newValue (){
							 var _value = [];
							 for(var i=0;i<labels.length;i++){
								 _value[i] = 0;
							 }
							 return _value;
						 };
						 
						 $.each(r.datas.rows,function(i,row){
							 var data = {name:row[0],value:newValue(),linewidth:2};
							 var hasData = false;
							 for(var a=0;a<datas.length;a++){
								 if(datas[a]['name'] == data['name']){
									 data = datas[a];
									 for(var b=0;b<labels.length;b++){
										 if(labels[b] == row[1]){
											 data.value[b] = row[2];
											 break;
										 }
									 }
									 hasData = true;
									 break;
								 }
							 }
							 
							 if(!hasData){
								 for(var b=0;b<labels.length;b++){
									 if(labels[b] == row[1]){
										 data.value[b] = row[2];										
										 break;
									 }
								 }
								 data['color']=aera_color[datas.length];
								 datas[datas.length] = data;			 
							 }
						 });
						 logger.log(datas);
						 chartDef.height=$('#teamChart').height();
						 chartDef.width = $('#teamChart').width();
						 chartDef.data = datas;
						 chartDef.coordinate.scale[1].labels=labels;
						 var chart = new iChart.Area2D(chartDef);
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