(function() {
	"use strict";
	var __browser = getBrowser();
	define([ 'jquery','logger','ui','ajaxwrapper','chart' ],
			function($,logger,ui,ajaxWrapper) {
		var _monitor ;
		var Monitor = function(){
			var navigationPanel = $('#navigation .container');
			var statusPanel = $('footer.status-bar');
			var monitorPanel = $('#monitorPanel');
			var menuPanel = monitorPanel.find('aside.sidebar-menu-container');
			var chartW = 450;
			var chartH = 200;
			function drawChart1(){
				$('#chart1').children().remove();
				$('#chart1').highcharts({
			        chart: {
			            plotBackgroundColor: null,
			            plotBorderWidth: null,
			            plotShadow: false,
			        	height:chartH,
			        	width:chartW
			        },
			        title: {
			            text: ''
			        },
			        tooltip: {
			    	    pointFormat: '{series.name}: <b>{point.percentage:.1f}%</b>'
			        },
			        plotOptions: {
			            pie: {
			                allowPointSelect: true,
			                cursor: 'pointer',
			                dataLabels: {
			                    enabled: true,
			                    color: '#000000',
			                    connectorColor: '#000000',
			                    format: '<b>{point.name}</b>: {point.percentage:.1f} %'
			                }
			            }
			        },
			        series: [{
			            type: 'pie',
			            name: '评卷情况',
			            data: [
			                ['出成绩率',   45.0],
			                ['多评未完成率',       26.8],
			                {
			                    name: '问题卷率',
			                    y: 6.2,
			                    sliced: true,
			                    selected: true
			                },
			                {
			                    name: '仲裁卷率',
			                    y: 8.5,
			                    sliced: true,
			                    selected: true
			                }
			            ]
			        }]
			    });
			};
			
			function drawChart2(){
				$('#chart2').children().remove();
				$('#chart2').highcharts({
			        chart:{
			        	height:chartH,
			        	width:chartW
			        },
					title: {
			            text: '',
			            x: -20 //center
			        },
			        xAxis: {
			            categories: ['9:00', '10:00', '11:00', '12:00', '13:000', '14:00','15:00', '16:00', '17:00']
			        },
			        yAxis: {
			            title: {
			                text: '张'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '张'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'center',
			            verticalAlign: 'bottom',
			            borderWidth: 0
			        },
			        series: [{
			            name: '每小时平均评卷速度',
			            data: [11000, 11480, 12089, 0, 11890, 12098, 12311, 12511, 12301]
			        }]
			    });
			};
			
			function drawChart3(){
				$('#chart3').children().remove();
				$('#chart3').highcharts({
					 chart:{
				        	height:chartH,
				        	width:chartW,
				        	 type: 'area'
				    },
					title: {
			            text: '',
			            x: -20 //center
			        },				    
				    xAxis: {
			            labels: {
			                formatter: function() {
			                    return this.value; // clean, unformatted number for year
			                }
			            }
			        },
			        yAxis: {
			            title: {
			                text: '小题得分率'
			            },
			            labels: {
			                formatter: function() {
			                    return this.value  +'%';
			                }
			            }
			        },
			        tooltip: {
			            pointFormat: '{series.name} produced <b>{point.y:,.0f}</b><br>warheads in {point.x}'
			        },
			        plotOptions: {
			            area: {
			                pointStart: 1,
			                marker: {
			                    enabled: false,
			                    symbol: 'circle',
			                    radius: 2,
			                    states: {
			                        hover: {
			                            enabled: true
			                        }
			                    }
			                }
			            }
			        },
			        series: [{
			            name: '小题得分分布',
			            data: [
			            		80, 82,79, 83, 81, 82,
			            		87, 76, 80, 81, 84,79, 82, 85, 84, 88, 80,
			            		87, 77, 72,75, 80, 88, 83, 80, 78, 79, 81,
			            		82,84, 80
			            ]
			        }]
			    });
			};
			
			function drawChart4(){
				$('#chart4').children().remove();
				$('#chart4').highcharts({
					 chart:{
				        	height:chartH,
				        	width:chartW
				    },
					title: {
			            text: '',
			            x: -20 //center
			        },
			        subtitle: {
			            text: '',
			            x: -20
			        },
			        xAxis: {
			        	categories: ['9:00', '10:00', '11:00', '12:00', '13:000', '14:00','15:00', '16:00', '17:00']
			        },
			        yAxis: {
			            title: {
			                text: '得分'
			            },
			            plotLines: [{
			                value: 0,
			                width: 1,
			                color: '#808080'
			            }]
			        },
			        tooltip: {
			            valueSuffix: '°C'
			        },
			        legend: {
			            layout: 'vertical',
			            align: 'center',
			            verticalAlign: 'bottom',
			            borderWidth: 0
			        },
			        series: [{
			            name: '每小时平均评卷平均得分',
			            data: [1.78, 1.75, 1.80, 0, 1.80, 1.83, 1.80, 1.81, 1.79]
			        }]
			    });
			};
			
			function setWorkspaceWH(){
				var windowH = $(window).height();
	            var h = getClientHeight()-navigationPanel.height()-statusPanel.height()-30;
	            monitorPanel.height(h);
	            var w = monitorPanel.width();
	            chartW = monitorPanel.find('div.team-monitor-chart').width();
	            chartH = (h-60)/2 * 0.98;
			};
			
			function addMenuEvent(){
				menuPanel.on('click','ul.sidebar-menu a',function(){
					$(this).parent().parent().children().removeClass('actived');
					$(this).parent().addClass('actived');
				});
			};
			
			this.render = function(){
				setWorkspaceWH();
				addMenuEvent();
				drawChart1();
				drawChart2();
				drawChart3();
				drawChart4();
				monitorPanel.find('div.team-monitor-panel').css({background: '#c5c5c5'});
				setInterval(function(){
					drawChart1();
					drawChart2();
					drawChart3();
					drawChart4();
				},1000 * 10);
			};
		};
		
		_monitor = new Monitor();
		return _monitor; 
	});
	
})();