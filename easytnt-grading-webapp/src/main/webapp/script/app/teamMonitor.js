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
			            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
			        },
			        yAxis: {
			            title: {
			                text: 'Temperature (°C)'
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
			            name: '每小时平均评卷速度',
			            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
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
			                    return this.value / 1000 +'%';
			                }
			            }
			        },
			        tooltip: {
			            pointFormat: '{series.name} produced <b>{point.y:,.0f}</b><br>warheads in {point.x}'
			        },
			        plotOptions: {
			            area: {
			                pointStart: 1940,
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
			            		20434, 24126,27387, 29459, 31056, 31982,
			            		32040, 31233, 29224, 27342, 26662,26956, 27912, 28999, 28965, 27826, 25579,
			            		25722, 24826, 24605,24304, 23464, 23708, 24099, 24357, 24237, 24401, 24344,
			            		23586,22380, 21004
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
			            text: 'Monthly Average Temperature',
			            x: -20 //center
			        },
			        subtitle: {
			            text: 'Source: WorldClimate.com',
			            x: -20
			        },
			        xAxis: {
			            categories: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun','Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec']
			        },
			        yAxis: {
			            title: {
			                text: 'Temperature (°C)'
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
			            data: [7.0, 6.9, 9.5, 14.5, 18.2, 21.5, 25.2, 26.5, 23.3, 18.3, 13.9, 9.6]
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