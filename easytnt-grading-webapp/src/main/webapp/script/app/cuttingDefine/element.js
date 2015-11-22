(function() {
	'use strict';
	var deps = [ 'jquery', "./resize", "./sub_question_panel" ];
	define(deps, function($, Resize, SubQuestionPanel) {
		function Element() {
			var element = this;

			element.view = create_element_view();
			element.data = create_question_data();
			
			// 为元素添加事件
			element.make_element_editable = function() {
				element.make_draggable();
				element.make_resizable();
			};
			
			//根据缩放倍数更改当前元素的位置
			element.updatePosition = function() {
				var position = element.data.areaInPaper;
				var scaleRate = selection.scaleRate;
				//修改页面上元素的显示位置
				element.updateViewPosition(position, scaleRate);
			}
			
			//更改当前元素数据区坐标值
			element.updateDataPosition = function(position, scaleRate) {
				element.data.areaInPaper = {
						left: position.left * scaleRate,
						top: position.top * scaleRate,
						width: position.width * scaleRate,
						height: position.height * scaleRate
				}
			}
			
			//更改当前元素页面上显示的坐标位置
			element.updateViewPosition = function(position, scaleRate) {
				$(element.view).css({
					left: position.left * scaleRate,
					top: position.top * scaleRate,
					width: position.width * scaleRate,
					height: position.height * scaleRate
				});
			}
			
			//根据x轴坐标和宽度进行对齐操作
			element.align = function(left, width) {
				//设置数据域中的值
				element.data.areaInPaper.left = left;
				element.data.areaInPaper.width = width;
				
				//修改页面上的位置
				$(element.view).css({
					left: left + 'px',
					width: width + 'px'
				});
			}
			
			//选区删除
			//1.删除选区列表中对应的当前元素
			//2.从试卷面板中移除已经画出的选区
			//3.删除选区左上角显示的宽高提示面板
			element.del = function() {
				//删除elements列表中的当前元素
				selection.elements.remove(element);
				//删除页面上的无效dom元素
				$(element.view).remove();
				//隐藏无效dom的宽高tip
				$(selection.target).find('.size').css({display : 'none'});
				//如果当前数据信息提示框显示，就隐藏,元素被删除了，显示数据就没有意义了
				$(selection.target).find('.control-content').css({
					display: 'none'
				});
				//恢复鼠标形状
				$(selection.target).css({
					cursor : 'default'
				});
				
				selection.currentElement = null;
			}

			//让元素可以拖动
			element.make_draggable = function() {
				var target = element.view;
				//设置拖拽样式
				element.set_drag_style(target);
				
				$(target).mousedown(function(e) {
					selection.prevent_event(e);
					if($(element.view).width() >= 1) {
						// 改变当前处理元素值
						selection.record_current_element(element);
						//保存上一个元素的数据
						element.save_preview_element_data();
						// 选中当前元素，其余元素都取消选中,隐藏助托点
						selection.select_element(element, e);
						selection.showSize = true;
					}
					
					$(this).css({
						cursor : 'move'
					});
					var draggable = true;
					var startX = e.pageX;
					var startY = e.pageY;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(draggable) {//可以进行拖动操作
							var currentX = e.pageX;
							var currentY = e.pageY;
							var distanceX = currentX - startX;
							var distanceY = currentY - startY;
							element.position_drag_element(target, distanceX, distanceY);

							//限制数据栏显示位置，不超出整个试卷范围
							element.position_question_panel();
							// 显示选区当前宽高tip
							selection.show_size();
							// 改变宽高tip中的值
							selection.change_size_tip();
							// 显示位置信息
							selection.show_msg();
							
							startX = currentX;
							startY = currentY;
						}
					});

					$(document).mouseup(function() {
						selection.prevent_event(e);
						selection.showSize = false;
						draggable = false;
						$(this).css({
							cursor : 'move'
						});
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});

				});
			};
			
			//修改拖动样式
			element.set_drag_style = function(target) {
				$(target).mouseover(function() {
					$(this).css({
						cursor : 'move'
					});
				});
				$(target).mouseout(function() {
					$(this).css({
						cursor : 'default'
					});
				});
			};
			
			//调整拖动过程中元素位置
			element.position_drag_element = function(target, distanceX, distanceY) {
				var x = $(target)[0].offsetLeft;
				var y = $(target)[0].offsetTop;

				var left = x + distanceX;
				var top = y + distanceY;
				var width = $(target).outerWidth();
				var height = $(target).outerHeight();
				
				//容错处理
				var position = selection.restrain_question_panel(left, top, width, height);
				
				$(target).css({
					left : position.left + 'px',
					top : position.top + 'px'
				});
			};
			
			// 让元素可以拖动
			element.make_draggable1 = function() {
				// 可拖拽
				$(element.view).draggable(true);

				element.change_drag_style();
				
				$(element.view).draggable({
					onStartDrag : function(e) {
						if($(element.view).width() >= 1) {
							// 改变当前处理元素值
							selection.record_current_element(element);
							//保存上一个元素的数据
							element.save_preview_element_data();
							// 选中当前元素，其余元素都取消选中,隐藏助托点
							selection.select_element(element, e);
							selection.showSize = true;
						}
					},
					onDrag : function(e) {
						//限制元素位置和大小，不超出整个试卷范围
						element.element_drag_restrain(e);
						//限制数据栏显示位置，不超出整个试卷范围
						element.position_question_panel();
						// 显示选区当前宽高tip
						selection.show_size();
						// 改变宽高tip中的值
						selection.change_size_tip();
						// 显示位置信息
						selection.show_msg();
					},
					onStopDrag: function() {
						selection.showSize = false;
					}
				});
			};
			
			// 设置拖动时鼠标样式
			element.change_drag_style = function() {
				// 设置当前指针为拖拽样式
				$(element.view).mouseover(function(e) {
					$(element.view).css({
						cursor : 'move'
					});
				});
				
				$(element.view).mouseout(function(e) {
					$(element.view).css({
						cursor : 'default'
					});
				});
				
				$(element.view).mousedown(function(e) {
					$(element.view).css({
						cursor : 'move'
					});
					
					$(document).mousemove(function(e) {
						$(element.view).css({
							cursor : 'move'
						});
					});
					
					$(document).mouseup(function(e) {
						$(element.view).css({
							cursor : 'move'
						});
						
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});
			};
			
			// 拖动过程中限制元素的位置
			element.element_drag_restrain = function(e) {
				var d = e.data;
				if (d.left < 0) {
					d.left = 0;
				}
				if (d.top < 0) {
					d.top = 0;
				}
				var img = $(selection.target).find('img');
				if (d.left + $(d.target).outerWidth() > $(img)[0].scrollWidth) {
					d.left = $(img)[0].scrollWidth - $(d.target).outerWidth();
				}
				if (d.top + $(d.target).outerHeight() > $(img)[0].scrollHeight) {
					d.top = $(img)[0].scrollHeight - $(d.target).outerHeight();
				}
			};

			// 让元素可以拖动
			element.make_resizable = function() {
				// 可改变大小
				var points = $(element.view).find('.point');
				if(points.length != 0) {
					// 显示这八个助托点
					$(points).each(function() {
						$(this).css({
							display : 'inline-block'
						});
					});
					
					return;
				}
				
				// 创建八个点并加入到当前要操作的元素中
				var leftUp = document.createElement('div');
				$(leftUp).addClass('point leftUp');
				
				var up = document.createElement('div');
				$(up).addClass('point up');
				
				var rightUp = document.createElement('div');
				$(rightUp).addClass('point rightUp');
				
				var left = document.createElement('div');
				$(left).addClass('point left');
				
				var right = document.createElement('div');
				$(right).addClass('point right');
				
				var leftDown = document.createElement('div');
				$(leftDown).addClass('point leftDown');
				
				var down = document.createElement('div');
				$(down).addClass('point down');
				
				var rightDown = document.createElement('div');
				$(rightDown).addClass('point rightDown');
				
				$(element.view).append($(up))
					.append($(left))
					.append($(right))
					.append($(down))
					.append($(rightUp))
					.append($(leftDown))
					.append($(leftUp))
					.append($(rightDown));
				
				
				var resize = Resize.newInstance(element)
					.left(left)
					.right(right)
					.up(up)
					.down(down)
					.leftUp(leftUp)
					.rightUp(rightUp)
					.leftDown(leftDown)
					.rightDown(rightDown);
				
				// 设置中间的4个助托点居中
				resize.position_middle_point();
				//缩放需要重新设定中间点的位置,所以需要用到resize.position_middle_point
				element.resize = resize;
			};
			
			// 显示当前选中元素的数据值
			element.show_data = function(createSubData) {
				//这里经过select_element方法处理后，会存在取消选中元素的情况，需要判断
				if(!selection.currentElement) {
					return;
				}
				if(createSubData) {
					var subData = create_sub_question_data();
					element.data.itemAreas.push(subData);
				}
				//调整题目信息框位置
				element.position_question_panel();
				//显示对象中的数据到信息框上
				element.show_with_element_data(createSubData);
			};
			
			// 保存设置的数据到元素数据域中
			// 只有点击添加按钮或者点击下一页、上一页按钮时还显示信息提示框时是将数据保存到当前选中元素下
			// 其他情况都是将数据保存到上一个选中元素中
			element.save_preview_element_data = function(saveSelf) {
				var el = null;
				if(saveSelf) {
					el = selection.currentElement;
				}else {
					if(selection.previousElement == null) {
						return;
					}
					el = selection.previousElement;
				}
				
				// 获取设置的数据信息，填入到element数据域中
				var questionPanel = $('.control-content');
				
				var name = $(questionPanel).find('#name').val();
				var fullScore = $(questionPanel).find('#fullScore').val();
				
				var requiredPinci = $(questionPanel).find('#requiredPinci').val();
				var maxerror = $(questionPanel).find('#maxerror').val();
				
				var left = $(questionPanel).find('#left').text();
				var top = $(questionPanel).find('#top').text();
				var width = $(questionPanel).find('#width').text();
				var height = $(questionPanel).find('#height').text();
				
				//元素areaInPaper存实际坐标值
				var scaleRate = selection.scaleRate;
				
				el.data.name = name;
				el.data.fullScore = fullScore;
				el.data.requiredPinci = requiredPinci;
				el.data.maxerror = maxerror;
				el.data.areaInPaper.left = parseInt(left / scaleRate);
				el.data.areaInPaper.top = parseInt(top / scaleRate);
				el.data.areaInPaper.width = parseInt(width / scaleRate);
				el.data.areaInPaper.height = parseInt(height / scaleRate);
				// 获取小题信息
				var subQuestionPanels = $(questionPanel).find('.subQuestionPanel');
				
				el.data.itemAreas.length = 0;
				// 重新填入小题信息
				var subQuestionPanel = null;
				for(var i = 0; i < subQuestionPanels.length; i++) {
					subQuestionPanel = subQuestionPanels[i];
					var title = $(subQuestionPanel).find('input[name=title]').val();
					var subFullScore = $(subQuestionPanel).find('input[name=subFullScore]').val();
					var seriesScore = Number($(subQuestionPanel).find('select#seriesScore').val());
					var interval = $(subQuestionPanel).find('input[name=interval]').val();
					var validValues = $(subQuestionPanel).find('input[name=validValues]').val();
					
					var subData = create_sub_question_data(title, subFullScore, 
							seriesScore, interval, validValues);
					el.data.itemAreas.push(subData);
				}
			};
			
			//重新调整题目信息框位置
			element.position_question_panel = function(flag) {
				if(!selection.currentElement) {
					return;
				}
				var target = $('.control-content');
				var display = $(target).css('display');
				// 获取当前元素的相对位置
				var x = element.view.offsetLeft;
				var w = $(element.view).width();
				var top = element.view.offsetTop;
				var left = x + w + 20;
				
				var width = $(target).outerWidth();
				var height = $(target).outerHeight();
				
				var position = selection.restrain_question_panel(left, top, width, height);
				
				if(display == 'none') {
					$('.control-content').css({
						display:'block'
					});
				}
				
				//如果是点击添加按钮触发就不用重新调整信息框位置
				if(!flag) {
					$('.control-content').css({
						left : position.left + 'px',
						top : position.top + 'px'
					});
				}
			}
			
			// 根据当前选择的元素在右侧显示其数据信息
			element.show_with_element_data = function(flag) {
				// 1显示右侧数据表单
				element.position_question_panel(flag);
				// 2清空小题信息，重新加载
				$('.sub-question-container').empty();
				// 3根据当前元素值初始化数据控件
				$('#name').val(element.data.name);
				$('#fullScore').val(element.data.fullScore);
				$('#requiredPinci').val(element.data.requiredPinci);
				$('#maxerror').val(element.data.maxerror);
				
				var panel = null;
				var subDatas = element.data.itemAreas;
				
				//删除其中空元素
				for(var i = 0; i < subDatas.length; i++) {
					if(subDatas[i] == null) {
						subDatas.remove(subDatas[i]);
						i--;
					}
				}
				
				var subData = null;
				for(var i = 0; i <subDatas.length; i++) {
					panel = element.add_sub_question(i);
					
					subData = subDatas[i];
					// 赋值
					$(panel.view).find('input[name=title]').val(subData.title);
					$(panel.view).find('input[name=subFullScore]').val(subData.fullScore);
					$(panel.view).find('select#seriesScore').val(subData.seriesScore);
					$(panel.view).find('input[name=interval]').val(subData.interval);
					$(panel.view).find('input[name=validValues]').val(subData.validValues);
					
					//根据用户给分率初始化控件状态
					if(!Number(subData.seriesScore)) {
						$(panel.view).find('input[name=interval]').css({
							display: 'none'
						});
						
						$(panel.view).find('input[name=validValues]').removeAttr('readonly');
					}
					
				}
			};
			
			element.add_sub_question = function(index) {
				var panel = SubQuestionPanel.newInstance(index);
				$('.sub-question-container').append($(panel.view));
				return panel;
			};
		}
		
		// 创建元素内容布局
		function create_element_view() {
			var div = document.createElement('div');
			$(div).addClass('element');

			$(div)[0].ondragstart = function() {return false;};

			return div;
		}

		// 创建选中元素的题目数据
		function create_question_data() {
			var data = {};
			data.id = 0;
			data.name = '1';// 题号
			data.answerCardImageIdx = window.selection.answerCardImageIdx;//答题卡位置
			data.requiredPinci = 1;//评次
			data.maxerror = 1;//误差
			data.fullScore = 10;// 满分值
			data.areaInPaper = {
					left: 0,// 相对图片的x轴坐标
					top: 0, // 相对图片的y轴坐标
					width: 0, // 所选区域的宽度
					height: 0// 所选区域的高度
			};
			
			data.itemAreas = [];// 小题定义
			return data;
		}

		// 创建选中元素的小题数据
		// @param title 小题号
		// @param fullScore 分值
		// @param seriesScore 给分率 1:连续 0:不连续
		// @param interval 分值间隔
		// @param validValues 给分率所计算的值
		function create_sub_question_data(title, fullScore, seriesScore, interval, validValues) {
			var data = {};
			data.id = 0;
			data.title = title == undefined ? '' : title;// 小题号
			data.fullScore = fullScore == undefined ? 10 : fullScore;// 小题分值
			data.seriesScore = seriesScore == undefined ? 1 : seriesScore;// 默认为连续
			data.interval = interval == undefined ? 1 : interval;// 默认从1开始
			data.validValues = validValues == undefined ? '0,1,2,3,4,5,6,7,8,9,10' : validValues;// 给分率

			return data;
		}
		
		var o={
				newInstance:function(){
					return new Element();
				}
		};
		return o;
		

	});
})();