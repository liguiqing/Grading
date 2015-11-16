(function() {
	'use strict';
	var deps = [ 'jquery', 'ajaxwrapper', 'dialog',"easyui", "./selection", "./examObj", "./element"];
	define(deps, function($, ajaxWrapper, dialog,easyui, Selection, ExamObj, Element) {
		var obj = function() {
			var me = this;
			this.render = function() {
				//浏览器自适应高度
				this.setContainerHeight();
				$(window).resize(function() {
					me.setContainerHeight();
				});
				kk();
				//切割入口
				entrance();
				//初始化底部工具栏按钮状态
				initBtnStatus();
				initEvent();
			};
			
			function entrance() {
				//准备试卷信息
				recoverPaper();
				if(window.examObj.examPapers.length == 0) {//保存操作
					doCreate();
				}else {//编辑操作
					doEdit();
				}
			}
			
			//进行新建试卷初始化
			function doCreate() {
				initSelection(0);
			}
			
			//进行编辑操作,默认恢复第一张答题卡数据
			function doEdit() {
				var selection = window.examObj.examPapers[0];
				selection.recover();
				window.selection = selection;
			}
			
			//初始化底部工具栏中按钮点击事件
			function initEvent() {
				//保存，将试卷数据转化为固定格式的json，并传递到后台
				$('#saveBtn').click(function() {
					stage_unsaved_element();
					//构建数据
					var data = buildData();
					//提交保存
					saveData(data);
				});
				
				//对齐按钮，针对选中的元素，距离该元素位置范围[-15%, 15%]*width之间的元素自动按照该元素位置和宽度进行对齐操作
				$('#alignBtn').click(function() {
					//得到当前选中的元素
					var element = window.selection.currentElement;
					if(element) {
						selection.alignElements(element);
					}
				});
				
				//答题卡页面点击事件
				$('.clearfix').find('a.examPage').click(function() {
					var val = $(this).text();
					var index = Number(val) - 1;
					jumpTo(this, index);
				});
			}
			
			//保存题目数据
			function saveData(data) {
				var url = "/cuttingDefine/savetest";
				console.info(data);
				ajaxWrapper.postJson(url,data,'',function(data) {
					alert(data);
				});
			}
			
			//构建提交到后台的json数据对象
			function buildData() {
				var CuttingsSolution = {
						designFor: {
							paperId: window.examObj.paperId,
							answerCardCuttingTemplates : window.examObj.answerCardCuttingTemplates,
						},
						cutTo: []
				};
				
				for(var i = 0; i < window.examObj.examPapers.length; i++) {
					var selection = window.examObj.examPapers[i];
					for(var j = 0; j < selection.elements.length; j++) {
						var element = selection.elements[j];
						var data = element.data;
						var cut = {
								id: data.id,
								name: data.name,
								answerCardImageIdx: data.answerCardImageIdx,
								requiredPinci: data.requiredPinci,
								maxerror: data.maxerror,
								fullScore: data.fullScore,
								areaInPaper: {
									left: data.areaInPaper.left,
									top: data.areaInPaper.top,
									width: data.areaInPaper.width,
									height: data.areaInPaper.height
								},
								itemAreas:[]
						};
						CuttingsSolution.cutTo.push(cut);
						
						for(var k = 0; k < data.itemAreas.length; k++) {
							var itemArea = data.itemAreas[k];
							var item = {
									item: {
										id: itemArea.id,
										title: itemArea.title,
										fullScore: itemArea.fullScore,
										seriesScore: itemArea.seriesScore,
										interval: itemArea.interval,
										validValues: itemArea.validValues.split(',')
									}
							};
							cut.itemAreas.push(item);
						}
					}
				}
				
				return CuttingsSolution;
			}
			
			//判断当前有没有没有被保存的元素,存在就保存
			function stage_unsaved_element() {
				//如果题目信息提示框被显示出来，说明可能用户对指定选区做了修改操作，
				//或者新建了一个选区还没保存，所以针对这种情况，需要对该选区执行保存操作
				var display = $(window.selection.target).find('.control-content').css('display');
				if(display == 'block') {//当前可能存在修改操作
					window.selection.currentElement.save_preview_element_data(true);//对自己的数据进行保存
				}
			}
			
			//恢复整个试卷内容
			function recoverPaper() {
				var data = {
				    designFor: {
				        paperId: 1,
				        answerCardImageNum: 0,
				        answerCardCuttingTemplates: [{
				            index: 0,
				            rotate: 0,
				            url: "/grading/static/css/images/shijuan.jpg"
				        },
				        {
				            index: 1,
				            rotate: 0,
				            url: "/grading/static/css/images/shijuan2.jpg"
				        }]
				    },
				    cutTo: [{
				        id: 0,
				        name: 1,
				        areaInPaper: {
				            left: 244,
				            top: 119,
				            width: 191,
				            height: 102
				        },
				        itemAreas: [{
				            item: {
				                title: 2,
				                fullScore: 5.0,
				                validValues: [0.0, 1.0, 2.0, 3.0, 4.0, 5.0],
				                seriesScore: true,
				                interval: 1.0
				            }
				        }],
				        requiredPinci: 1,
				        maxerror: 1.0,
				        answerCardImageIdx: 0,
				        fullScore: 10.0
				    },
				    {
				        id: 0,
				        name: 1,
				        areaInPaper: {
				            left: 257,
				            top: 259,
				            width: 236,
				            height: 145
				        },
				        itemAreas: [{
				            item: {
				                title: 2,
				                fullScore: 6.0,
				                validValues: [0.0, 2.0, 4.0, 6.0],
				                seriesScore: true,
				                interval: 2.0
				            }
				        }],
				        requiredPinci: 1,
				        maxerror: 1.0,
				        answerCardImageIdx: 1,
				        fullScore: 10.0
				    }]
				}
				//试卷数据
				var examObj = ExamObj.newInstance();
				examObj.paperId = data.designFor.paperId;
				examObj.answerCardCuttingTemplates = data.designFor.answerCardCuttingTemplates;
				//答题卡数据
				var selections = [];
				var index = -1; 
				for(var i = 0; i < data.cutTo.length; i++) {
					var cut = data.cutTo[i];
					index = cut.answerCardImageIdx;
					//当前selection如果没有被创建，就新建一个
					var selection = selections[index];
					if(selection == undefined) {
						selection = Selection.newInstance('.image-content');
						window.selection = selection;
						selection.answerCardImageIdx = index;
						selections[index] = selection;
					}
					
					//创建题目信息
					var element = Element.newInstance();
					element.data = {
							id: cut.id,
							name: cut.name,// 题号
							answerCardImageIdx :cut.answerCardImageIdx,//答题卡位置
							requiredPinci: cut.requiredPinci,//评次
							maxerror: cut.maxerror,//误差
							fullScore: cut.fullScore,// 满分值
							areaInPaper: cut.areaInPaper,
							itemAreas:[]// 小题定义
					};
					
					//创建小题信息
					for(var j = 0; j < cut.itemAreas.length; j++) {
						var itemArea = cut.itemAreas[j].item;
						itemArea.seriesScore = itemArea.seriesScore ? 1 : 0;//转换为select的值
						if(itemArea.validValues.length == 0) {
							itemArea.validValues = '';
						}else {
							itemArea.validValues = itemArea.validValues.join(',');
						}
						element.data.itemAreas.push(itemArea);
					}
					
					selection.elements.push(element);
				}
				
				examObj.examPapers = selections;
				window.examObj = examObj;
			}
			
			
			//恢复指定答题卡页面内容
			function recoverSelection(selection) {
				window.selection = selection;//标记当前处理的答题卡
				//初始化试卷状态
				clearCanvas();
				selection.recover();
			}
			
			//得到上一张答题卡
			function getPreviousSelection() {
				var selection = null;
				var index = window.examObj.examPapers.indexOf(window.selection);
				selection = window.examObj.examPapers[--index];
				return selection;
			}
			
			//得到下一张答题卡
			function getNextSelection() {
				var selection = null;
				var index = window.examObj.examPapers.indexOf(window.selection);
				index += 1;
				if(index == window.examObj.examPapers.length) {//已经是最后一页
					return null;
				}else {
					selection = window.examObj.examPapers[index];
				}
				return selection;
			}
			
			//是否还可以往前翻页，如果为第一页就不能再往前翻页
			function isPreviousEnable() {
				var index = window.examObj.examPapers.indexOf(window.selection);
				if(index == -1) {//刚开始初始化完毕
					return false;
				}else {
					index -= 1;
					if(index < 0) {//已经处在第一页
						return false;
					}
				}
				
				return true;
			}
			
			//是否还可以往后翻页,如果已经是最后一张答题卡就不能继续往下翻了
			function isNextEnable() {
				var index = window.examObj.examPapers.indexOf(window.selection);
				index += 1;
				if(index == window.examObj.answerCardCuttingTemplates.length) {
					return false;
				}
				
				return true;
			}
			
			//根据examObj初始化底部上翻下翻按钮状态
			function initBtnStatus() {
				//根据examobj中的试卷答题卡数量创建对应每一张答题卡的超链接
				var arr = window.examObj.answerCardCuttingTemplates;
				if(arr.length == 0) {
					throw new Error('异常，试卷无答题卡!');
				}else {
					var html = '';
					for(var i = 0; i < arr.length; i++) {
						var currentCls = '';
						if(i == window.examObj.current) {
							currentCls = ' currentPage';
						}
						html += '<a class="examPage'+currentCls+'" href="javascript:void(0)">' 
							+ (Number(arr[i].index)+1) + '</a>';
					}
					$('.clearfix').append($(html));
				}
			}
			
			//跳转到相应答题卡页面
			function jumpTo(target, index) {
				//如果当前点击的是当前显示页面就不作渲染
				var val = $('.clearfix').find('a.currentPage').text();
				var curIndex = Number(val) - 1;
				if(index == curIndex) {
					return;
				}else {//跳转到其他答题卡页面
					stage_unsaved_element();
					var selection = window.examObj.examPapers[index];
					//如果是不存在的就直接创建一个
					if(selection == undefined) {
						initSelection(index);
					}else {
						recoverSelection(selection);
						
					}
					
					resetCurrentPageStyle(target);
				}
			}
			
			//重新设置当前页面索引样式
			function resetCurrentPageStyle(target) {
				$(target).siblings('a.currentPage').removeClass('currentPage');
				$(target).addClass('currentPage');
			}
			
			//清除画布中内容
			function clearCanvas() {
				var target = $('.image-content');
				$(target).empty();
				//取消对画布容器设置的mousedown事件监听
				$(target).unbind('mousedown');
			}
			
			//初始化试卷可以创建选区
			function initSelection(index) {
				//初始化之前先清空画布
				clearCanvas();
				var selection = Selection.newInstance('.image-content');
				//初始化该答题卡对应的图片地址
				selection.answerCardImageIdx = index;
				window.selection = selection;
				selection.init();
				//将当前答题卡保存到指定索引
				window.examObj.examPapers[index] = window.selection;
			}

			function kk(){
				$('.testpaperImage').attr("unselectable", "on");
				$('.testpaperImage').on("selectstart", function () {
					return false;
				}); //设置不能选择内容
				$('.testpaperImage').on("contextmenu", function () {
					return false;
				}); //设置鼠标右键出菜单失效
				
				$('.image-content').attr("unselectable", "on");
				$('.image-content').on("selectstart", function () {
					return false;
				}); //设置不能选择内容
				$('.image-content').on("contextmenu", function () {
					return false;
				}); //设置鼠标右键出菜单失效
				
				$('#cuttingDefineContainer').attr("unselectable", "on");
				$('#cuttingDefineContainer').on("selectstart", function () {
					return false;
				}); //设置不能选择内容
				$('#cuttingDefineContainer').on("contextmenu", function () {
					return false;
				}); //设置鼠标右键出菜单失效
			}
			this.setContainerHeight = function() {
				var height = $('body').height();
				$('#cuttingDefineContainer').css('height',
						height - 85);

			}
		}
		return new obj();
	});
})();