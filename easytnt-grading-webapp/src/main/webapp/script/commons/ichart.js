(function() {
	"use strict";
	define([ 'jquery','ui','ichart'], function($,ui) {
		var self ;
		var draw = function (data,labels,min,max,space,unit,renderTo){
			var chartW = $('#'+renderTo).width();
			var chartH = $('#'+renderTo).height();
			var draw = {
					render : renderTo||'canvasDiv',
					data: data,
					padding:'15 15 30 -70',//设置padding,以便title能占满x轴
					sub_option:{
						label:false,
						hollow_inside:false,//设置一个点的亮色在外环的效果
						point_size:10
					},
					tip:{
						enable:true,
						listeners:{
							 //tip:提示框对象、name:数据名称、value:数据值、text:当前文本、i:数据点的索引
							parseText:function(tip,name,value,text,i){
								return labels[i]+":<span style='color:red'>"+value+"</span>"+unit;
							}
						}
					},
					width : chartW,
					height : chartH,
					offsetx: 35,					
					offsety:0,
					position:'center',
					background_color:'#0c222f',
					gradient:true,
					shadow:true,
					shadow_blur:2,
					gradient_mode:'LinearGradientDownUp',//设置一个从下到上的渐变背景
					border:{
						radius:5
					},
					legend:{
						enable:true,
						align:'right',
						valign:'center',
						background_color : null,
						line_height:25,
						color:'#d3d4f0',
						fontsize:12,
						offsetx:20,
						fontweight:600,
						border : {
							enable : false
						}
					},
					coordinate:{

						grid_color:'#506e7d',
						background_color:null,//设置坐标系为透明背景
						scale:[{
							 position:'left',	
							 label:{
								 color:'#eff4f8',
								 fontsize:14,
								 fontweight:600
							 },
							 start_scale:min,
							 end_scale:max,
							 scale_space:space
						},{
							 position:'bottom',	
							 label:{
								 color:'#506673',
								 fontweight:600
							 },
							 labels:labels
						}]
					}
				};
			return draw;
		};
		
		var ichartDraw = self = {
				showMiamJiTu:function(data,labels,min,max,space,unit,renderTo){
					var chart = new iChart.Area2D(draw(data,labels,min,max,space,unit,renderTo));
					this.finaly(chart,unit);
				},
				showZheXianTu:function(data,labels,min,max,space,unit,renderTo){
					var chart = new iChart.LineBasic2D(draw(data,labels,min,max,space,unit,renderTo));
					this.finaly(chart,unit);
				},
				showZhuZhuangTu:function(data,min,max,space,unit,renderTo){
					var chart = new iChart.Column2D(draw(data,[],min,max,space,unit,renderTo,false));
					this.finaly(chart,unit,false);
				},
				finaly:function(chart,unit){
					//利用自定义组件构造左侧说明文本
					chart.plugin(new iChart.Custom({
							drawFn:function(){
								//计算位置
								var coo = chart.getCoordinate(),
									x = coo.get('originx'),
									y = coo.get('originy'),
									w = coo.width,
									h = coo.height;
								//在左上侧的位置，渲染一个单位的文字
								chart.target.textAlign('start')
								.textBaseline('bottom')
								.textFont('600 11px 微软雅黑')
								.fillText(unit,x-40,y-12,false,'#ffffff')
								.textBaseline('bottom')
								.fillText('(时间)',x+w+12,y+h+10,false,'#ffffff');
								
							}
					}));
					//调用绘图方法开始绘图
					chart.draw();
				}
		};
		return ichartDraw;
	});

})();