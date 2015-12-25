(function() {
	'use strict';
	var deps = [ 'jquery', "./element" ];
	define(deps, function($, Element) {
		//选区组件
		function Selection(target) {
			var selection = this;
			selection.startX = 0;//记录当前鼠标点击的位置，便于计算所选区域的宽高
			selection.startY = 0;//记录当前鼠标点击的位置，便于计算所选区域的宽高
			selection.showSize = false;//是否显示提示框
			selection.currentElement = null;//当前操作的元素
			selection.previousElement = null///前一个被选中的元素， 用于保存数据
			selection.answerCardImageIdx = 0; //答题卡图片索引
			selection.elements = [];//已经添加到内容中的元素
			selection.selectedList = [];//已经被选中的元素列表
			selection.scaleRate = 1;//缩放倍数默认不缩放
			selection.orignalWidth = 0;//试卷原始宽度
			selection.orignalHeight = 0;//试卷原始高度
			//目标控件
			selection.target = target;
			
			//选区一系列初始化操作
			selection.init = function() {
				selection.initUI();//初始化界面元素
				selection.initPopover(); //初始化提示框
				selection.preventImageDrag();
				selection.enable_key_del();
				selection.enable_selection();
				selection.btn_style();
				selection.init_event();
				selection.make_element_data_panel_draggable();
			};
			
			selection.initPopover = function() {
				$('span.add-btn').mouseover(function() {
					$(this).popover('show');
				});
				$('span.add-btn').mouseout(function() {
					$(this).popover('hide');
				});

			};
			
			//根据缩放倍数改变布局和元素位置
			selection.updateLayoutByScale = function() {
				//改变元素位置
				selection.updateElementsPosition();
			};
			
			//更改元素的位置(坐标宽高)
			selection.updateElementsPosition = function(){
				for(var i = 0; i < selection.elements.length; i++) {
					selection.elements[i].updatePosition();
				}
			};
			
			//更改整个试卷的宽高
			selection.changeExamPagerSize = function() {
				var width = selection.orignalWidth * selection.scaleRate;
				var height = selection.orignalHeight * selection.scaleRate;
				$(selection.target).find('img').attr('width', width);
				$(selection.target).find('img').attr('height', height);
			};
			
			//恢复某一页答题卡的内容
			selection.recover = function() {
				//重置seleciton属性值
				selection.init_properties();
				//初始化整个画布
				selection.init();
				//填充已经存在的选框
				selection.display_elements();
			};
			
			selection.init_properties = function() {
				selection.startX = 0;
				selection.startY = 0;
				selection.showSize = false;//是否显示提示框
				selection.currentElement = null;//当前操作的元素
				selection.previousElement = null///前一个被选中的元素， 用于保存数据
			};
			
			//回显所有已经存在的元素
			selection.display_elements = function() {
				for(var i = 0; i < selection.elements.length; i++) {
					var element = selection.elements[i];
					selection.add_exist_element(element);
				}
			}
			
			//针对选中的元素，距离该元素位置范围[-15%, 15%]*width之间的元素自动按照该元素位置和宽度进行对齐操作
			selection.alignElements = function(element) {
				var position = selection.getPosition(element);
				var range = selection.getRange(position);
				
				var siblingElement = null;
				var elementPosition = {};
				for(var i = 0; i < selection.elements.length; i++) {
					siblingElement = selection.elements[i];
					if(element != siblingElement) {
						elementPosition = selection.getPosition(siblingElement);
						if(elementPosition.left >= range.min 
								&& elementPosition.left <= range.max) {
							siblingElement.align(position.left, position.width, selection.scaleRate);
						}
					}
					
				}
			};
			
			//根据位置信息算出需要对齐的范围 width*[-15%, 15%]
			selection.getRange = function(position) {
				var minRange = parseInt(position.left - (position.width * 0.15));
				minRange = minRange < 0 ? 0 : minRange;
				
				var parentWidth = $(selection.target)[0].scrollWidth;
				var maxRange = parseInt(position.left + (position.width * 0.15));
				maxRange = maxRange > parentWidth ? parentWidth : maxRange;
				
				return {
					min: minRange,
					max: maxRange
				}
			}
			
			//获取指定元素针对当前试卷的相对位置和宽高
			selection.getPosition = function(element) {
				var left = element.view.offsetLeft;
				var top = element.view.offsetTop;
				var width = $(element.view).width();
				var height = $(element.view).height();
				
				return {
					left: left,
					top: top,
					width: width,
					height: height
				};
			};
			
			//针对选中的元素，距离该元素位置范围[-15%, 15%]*width之间的元素自动按照该元素位置和宽度进行对齐操作
			selection.alignElements = function(element) {
				var position = selection.getPosition(element);
				var range = selection.getRange(position);
				
				var siblingElement = null;
				var elementPosition = {};
				for(var i = 0; i < selection.elements.length; i++) {
					siblingElement = selection.elements[i];
					if(element != siblingElement) {
						elementPosition = selection.getPosition(siblingElement);
						if(elementPosition.left >= range.min 
								&& elementPosition.left <= range.max) {
							siblingElement.align(position.left, position.width, selection.scaleRate);
						}
					}
					
				}
			};
			
			//根据位置信息算出需要对齐的范围 width*[-15%, 15%]
			selection.getRange = function(position) {
				var minRange = parseInt(position.left - (position.width * 0.15));
				minRange = minRange < 0 ? 0 : minRange;
				
				var parentWidth = $(selection.target)[0].scrollWidth;
				var maxRange = parseInt(position.left + (position.width * 0.15));
				maxRange = maxRange > parentWidth ? parentWidth : maxRange;
				
				return {
					min: minRange,
					max: maxRange
				}
			}
			
			//获取指定元素针对当前试卷的相对位置和宽高
			selection.getPosition = function(element) {
				var left = element.view.offsetLeft;
				var top = element.view.offsetTop;
				var width = $(element.view).width();
				var height = $(element.view).height();
				
				return {
					left: left,
					top: top,
					width: width,
					height: height
				};
			};
			
			selection.add_exist_element = function(element){
				//清空原来元素中设定的八个助托点,重新设置元素可编辑
				$(element.view).empty();
				
				$(selection.target).append($(element.view));
				
				//设置当前元素的位置
				$(element.view).css({
					left: element.data.area.left + 'px',
					top: element.data.area.top + 'px',
					width: element.data.area.width + 'px',
					height: element.data.area.height + 'px'
				});
				
				//让当前元素可以编辑
				element.make_element_editable();
				
				//隐藏元素的八个助托点，表示选取框默认为不选中状态
				$(element.view).find('.point').css({
					display: 'none'
				});;
				
			};
			
			//初始化用户创建选区操作
			selection.enable_selection = function() {
				//为图片内容div添加事件
				$(selection.target).mousedown(function(e) {
					selection.showSize = true;
					var position = selection.get_current_position(e);
					selection.startX = position.x;///相对于屏幕的位置
					selection.startY = position.y;
					
					//鼠标拖动元素时触发，这时就不再是新建元素，而是拖动元素
					if(selection.intersect(selection.startX, selection.startY)) {
						return;
					}
					
					//添加一个新元素到内容中
					selection.add_element();
					
					//在进行创建元素时，需要对前一个元素数据进行保存
					if(selection.previousElement) {
						selection.currentElement.save_preview_element_data();
					}
					
					//改变鼠标指针形状
					$(this).css({
						cursor : 'crosshair'
					});
					
					$(document).mousemove(function(e) {
						//通过showSize控制鼠标事件是否对页面影响，鼠标按下时开启鼠标事件、鼠标弹起时取消鼠标事件
						if(!selection.showSize) {
							$(document).unbind('mousemove');
							return;
						}
						
						//设置要显示的当前选区宽高
						var position = selection.get_current_position(e);
						var currentX = position.x;///相对于屏幕的位置
						var currentY = position.y;
						
						//这里需要判断是否是拖动时点击触发
						var cursor = $(selection.target).css('cursor');
						//元素在创建过程中，完成元素创建之后才可以再设置元素大小
						if(selection.intersect(currentX, currentY) && cursor != 'crosshair') {
							$(document).unbind('mousemove');
							return;
						}
						
						//初始化宽高提示框大小
						selection.init_size(currentX, currentY);
						
						//初始化刚创建的元素大小
						selection.init_element_size(currentX, currentY);
					});
					
					//完成选区
					$(document).mouseup(function(e) {
						selection.showSize = false;
						//设置要显示的当前选区宽高
						var position = selection.get_current_position(e);
						var currentX = position.x;///相对于屏幕的位置，包括滚动条
						var currentY = position.y;
						
						// 在鼠标移动过程中才对该元素宽高进行控制，所以，需要将该无效元素去掉
						if($(selection.currentElement.view).width() < 50) {
							//隐藏题目信息框
							$('.control-content').css({
								display: 'none'
							});
							
							//隐藏上一个被选中的元素的助托点
							if(selection.previousElement) {
								$(selection.previousElement.view).find('.point').css({
									display: 'none'
								});
							}
							selection.currentElement.del();
						}else {
							//这里需要判断是否恢复鼠标形状,排除拖拽
							var cursor = $(target).css('cursor');
							if(!selection.intersect(currentX, currentY) || cursor == 'crosshair') {
								//这里需要判断是否是拖动时点击触发
								if(cursor != 'move') {//新建元素
									//恢复鼠标形状
									$(target).css({
										cursor : 'default'
									});
									//为当前元素添加事件
									selection.currentElement.make_element_editable();
								}
							}
							//创建成功，选中当前元素
							selection.select_element(selection.currentElement, e);
						}
						
						//清理当前类的mousemove mouseup事件
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});
			};
			
			//显示当前元素的题目信息
			selection.show_data = function() {
				if(selection.currentElement) {
					//显示当前设置的元素的数据
					selection.currentElement.show_data();
				}
			};
			
			// 元素数据面板可拖动
			selection.make_element_data_panel_draggable = function() {
				var dragui = $(selection.target).find('.panel-heading');
				var target = $(selection.target).find('.control-content');
				$(dragui).mouseover(function() {
					$(this).css({
						cursor : 'move'
					});
				});
				$(dragui).mouseout(function() {
					$(this).css({
						cursor : 'default'
					});
				});
				
				$(dragui).mousedown(function(e) {
					selection.prevent_event(e);
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

							startX = currentX;
							startY = currentY;
						}
					});

					$(document).mouseup(function() {
						selection.prevent_event(e);

						draggable = false;

						$(this).css({
							cursor : 'move'
						});
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});

				});
			}
			
			//重新定位信息框位置，防止超出试卷范围
			selection.restrain_question_panel = function(left, top, width, height) {
				if (left < 0) {
					left = 0;
				}
				if (top < 0) {
					top = 0;
				}
				if (left + width > $(target).width()) {
					left = $(target).width() - width;
				}
				if (top + height > $(target).height()) {
					top = $(target).height() - height;
				}
				
				var position = {
						left: left,
						top: top
				};
				
				return position;
			};
			
			// 阻止事件冒泡
			selection.prevent_event = function(event) {
				if (event.stopPropagation) {
					event.stopPropagation();
				} else {
					event.cancelBubble = true;
				}
			};
			
			//初始化一些题目信息中按钮点击事件
			selection.init_event = function() {
				// 点击添加小题按钮触发事件
				$(selection.target).find('.add-btn').click(function() {
					var element = selection.currentElement;
					element.show_data(true);
				});
				
				//面板关闭按钮
				$(selection.target).find('.panel-heading .close-btn').click(function() {
					$('.control-content').css({
						display : 'none'
					});
				});
				
				//小题框被点击时事件也会传递到图片上，需要阻止
				$(selection.target).find('.control-content').mousedown(function(e) {
					selection.prevent_event(e);
				});
				
			};
			
			//加载按钮样式
			selection.btn_style = function() {
				selection.add_btn_style();
			};
			
			//添加题目信息框添加小题定义按钮样式
			selection.add_btn_style = function() {
				// 添加右侧小题定义样式
				var addbtn_defaulticon = window.app.rootPath + 'static/css/images/add_default.png';
				var addbtn_focusicon =window.app.rootPath + 'static/css/images/add_focus.png';
				selection.btn_mouse_style($('.add-btn'), addbtn_defaulticon, addbtn_focusicon);
			}
			
			// 添加按钮获得和失去焦点样式
			selection.btn_mouse_style = function(btn, defaulticon, focusicon) {
				$(btn).mouseover(function() {
					$(this).css({
						backgroundImage : 'url(' + focusicon + ')',
						cursor : 'pointer'
					});
				});
				$(btn).mouseout(function() {
					$(this).css({
						backgroundImage : 'url(' + defaulticon + ')',
						cursor : 'default'
					});
				});
			}
			
			//判断当前操作是否是拖动
			selection.intersect = function(x, y) {
				var element = null;
				var left = 0;
				var top = 0;
				var width = 0;
				var height = 0;
				var intersect = false;//是否相交
				for(var i = 0; i < selection.elements.length; i++) {
					element = selection.elements[i];
					left = selection.getOffsetLeft(element.view);
					top = selection.getOffsetTop(element.view);
					width = $(element.view).width();
					height = $(element.view).height();
					if((x > left) 
							&& (x < left + width)
							&& (y > top)
							&& (y < top + height)) {
						intersect = true;
						break;
					}
				}
				
				return intersect;
			};
			
			//获取当前鼠标位置
			selection.get_current_position = function(e) {
				var x = e.clientX + selection.getScrollLeft($(selection.target));
				var y = e.clientY + selection.getScrollTop($(selection.target));

				var position = {
					x : x,
					y : y
				};
				return position;
			};
			
			//选中某一个元素，取消其他元素的选中效果(取消助托点)
			selection.select_element = function(element, e) {
				var isCtrl = e.ctrlKey;
				//是单选
				if(!isCtrl) {
					selection.singleSelectElement(element);
				} else {
					selection.multiSelectElement(element);
				}
				
				//鼠标停止改变元素位置的时候显示出最后的位置坐标
				selection.show_msg();
				selection.change_size_tip();
				selection.show_size();
				selection.show_data();
				if(element.resize) {
					element.resize.position_middle_point();
				}
			};
			
			//实现对元素的多选
			selection.multiSelectElement = function(element) {
				selection.currentElement = null;
				selection.hideQuestionInfo();
				selection.hideSizePanel();
				//多选有可能是取消选择的情况
				if(selection.isElementSelected(element)) {//选中则取消选中
					selection.unSelectElement(element);
				}else {
					//将当前选中元素添加到选中列表中
					selection.selectedList.push(element);
					//显示当前元素的8个助托点
					selection.show_resize_points(element);
				}
			};
			
			//隐藏宽高提示框
			selection.hideSizePanel = function() {
				//隐藏无效dom的宽高tip
				$(selection.target).find('.size').css({display : 'none'});
			}
			
			//取消对所有元素的选中
			selection.unSelectAll = function() {
				selection.currentElement = null;
				selection.previousElement = null;
				selection.selectedList.length = 0;
				selection.hideQuestionInfo();
				selection.hideSizePanel();
				selection.hideAllPoints();
			};
			
			//隐藏所有助托点，取消选中任何一个元素
			selection.hideAllPoints = function() {
				$(selection.target).find('.point').css({
					display: 'none'
				});
			};
			
			//实现对元素的单选
			selection.singleSelectElement = function(element) {
				//清空选中列表
				selection.selectedList.length = 0;
				//隐藏其他被选中元素的八个助托点，表示取消其他元素的选中状态
				selection.hideSiblingsResizePoints(element.view);
				//将当前选中元素添加到选中列表中
				selection.selectedList.push(element);
				//显示当前元素的8个助托点
				selection.show_resize_points(element);
			};
			
			//取消某元素的选中
			selection.unSelectElement = function(element) {
				//1.隐藏当前元素的八个助托点
				selection.hideResizePoints(element.view);
				//2.从选中列表中移除该元素
				selection.selectedList.remove(element);
			};
			
			//取消显示题目信息框
			selection.hideQuestionInfo = function() {
				//隐藏题目信息框
				$(selection.target).find('.control-content').css({
					display: 'none'
				});
			};
			
			//判断元素当前是否是选中状态，根据元素的8个助托点来判断，如果显示，则为选中
			selection.isElementSelected = function(element) {
				var isSelected = false;
				var display = $(element.view).find('.point').css('display');
				if(display == 'block') {
					isSelected = true;
				}
				
				return isSelected;
			};
			
			//取消其他被选中的元素所显示的8个助托点
			selection.hideSiblingsResizePoints = function(view) {
				var els = $(view).siblings('.element');
				$(els).each(function() {
					selection.hideResizePoints(this);
				});
			};
			
			//隐藏当前元素的8个助托点，取消对当前元素的选中
			selection.hideResizePoints = function(view) {
				var ps = $(view).find('.point');
				$(ps).each(function() {
					$(this).css({
						display : 'none'
					});
				});
			};
			
			//显示八个助托点，方便改变元素大小
			selection.show_resize_points = function(element) {
				//显示当前元素的助托点
				var points = $(element.view).find('.point');
				$(points).each(function() {
					$(this).css({
						display : 'inline-block'
					});
				});
			}
			
			selection.show_msg = function() {
				var element = selection.currentElement;
				if(!element) {
					return;
				}
				var view = element.view;
				var x = view.offsetLeft;
				var y = view.offsetTop;
				var width = $(view).width();
				var height = $(view).height();
				
				element.data.x = parseInt(x);
				element.data.y = parseInt(y);
				element.data.width = parseInt(width);
				element.data.height = parseInt(height);
				
				$('#left').text(element.data.x);
				$('#top').text(element.data.y);
				$('#width').text(element.data.width);
				$('#height').text(element.data.height);
				
			};
			
			//添加一个元素到内容区中
			selection.add_element = function() {
				var element = Element.newInstance();
				$(selection.target).append($(element.view));
				selection.record_current_element(element);
				selection.elements.push(selection.currentElement);
			}
			
			//记录当前正在处理的元素
			//同时保存上一个处理的元素，便于后面保存数据
			selection.record_current_element = function(element) {
				//如果选中的是同一个元素就不做处理，避免元素数据多余的增删操作
				if(selection.currentElement == element) {
					return;
				}
				selection.previousElement = selection.currentElement;
				selection.currentElement = element;
			};
			
			//显示提示框位置
			selection.show_size = function() {
				if(!selection.showSize || !selection.currentElement) {
					return;
				}
				var x = selection.getOffsetLeft(selection.currentElement.view) - selection.getOffsetLeft(selection.target);
				var y = selection.getOffsetTop(selection.currentElement.view) - selection.getOffsetTop(selection.target)-20;//20为提示框高度
				var display = $('.size').css('display');
				if(display != 'block') {
					$('.size').css({
						display : 'block'
					});
				}
				
				$('.size').css({
					left : x,
					top : y
				});
			}
			
			//改变加入元素的大小
			selection.init_element_size = function(currentX, currentY) {
				//先计算得到width,height
				var width = Math.abs(currentX - selection.startX);
				var height = Math.abs(currentY - selection.startY);
				
				//最小宽高为1x1
				width = width == 0 ? 1 : width;
				height = height == 0 ? 1 : height;
				
				var left = 0;
				var top = 0;
				//如果当前鼠标移动是往左和上两个方向移动，这时需要改变提示框的位置
				if(currentX < selection.startX) {
					left = currentX;
				}else {
					left = selection.startX;
				}
				
				if(currentY < selection.startY) {
					top = currentY;
				}else {
					top = selection.startY;
				}
				
				selection.position_element(left, top, width, height);
				
			}
			
			//设置元素的位置和宽高
			selection.position_element = function(left, top, width, height) {
				//设置位置还需要考虑到父元素的位置
				if(!selection.showSize) {
					return;
				}
				
				var img = $(selection.target).find('img');
				
				var parentLeft = selection.getOffsetLeft(img);
				var parentTop = selection.getOffsetTop(img);
				var parentWidth = $(img)[0].scrollWidth;
				var parentHeight = $(img)[0].scrollHeight;
				
				//这里需要做一个容错处理，放置绘制剪切区域时超出图片区域
				//x轴容错
				if(left + width > parentLeft + parentWidth) {
					width = parentLeft + parentWidth - left;
				}
				if(left < parentLeft) {
					left = parentLeft;
					width = selection.startX - parentLeft;
				}
				
				//y轴容错
				if(top + height > parentTop + parentHeight) {
					height = parentTop + parentHeight - top;
				}
				if(top < parentTop) {
					top = parentTop;
					height = selection.startY - parentTop;
				}
				
				var x = left - parentLeft;
				var y = top - parentTop;
				
				$(selection.currentElement.view).css({
					left : x,
					top : y,
					width : width,
					height : height
				});
				
				//显示选区当前宽高
				selection.show_size();
				//显示宽高提示框
				selection.change_size_tip();
				
			}
			
			//改变宽高提示框的值
			selection.change_size_tip = function() {
				if(!selection.showSize || !selection.currentElement) {
					return;
				}
				
				var width =Math.ceil($(selection.currentElement.view).width());
				var height = Math.ceil($(selection.currentElement.view).height());
				
				//调整提示框宽度而创建的一个尺子，用于测量文本宽度
				var ruler = null;
				ruler = $('.ruler');
				if(ruler.length == 0) {
					ruler = document.createElement('span');
					$(ruler).addClass('ruler');
					$('body').append(ruler);
					$(ruler).css({
						'font-size' : '8pt',
						display : 'none'
					});
				}else {
					ruler = $(ruler)[0];
				}
				
				ruler.innerHTML = width + ' x ' + height;
				var w = $(ruler).width() + 50;
				
				$('.size').css({
					width : w + 'px'
				});
				
				$('#width-tip').text(width);
				$('#height-tip').text(height);
			}
			
			//改变宽高提示框的大小
			selection.init_size = function(currentX, currentY) {
				//如果当前鼠标移动是往左和上两个方向移动，这时需要改变提示框的位置
				if(currentX < selection.startX && currentY >= selection.startY) {
					selection.show_size(currentX, selection.startY);
				}else if(currentY < selection.startY && currentX >= selection.startX) {
					selection.show_size(selection.startX, currentY);
				}else if(currentX < selection.startX && currentY < selection.startY) {
					selection.show_size(currentX, currentY);
				}
			};
			
			//获取元素离屏幕的scrollleft
			//这里已经对html元素scrollLeft进行了计算
			selection.getScrollLeft = function(target) {
				target = $(target)[0];
				var left = target.scrollLeft;
				var parent = $(target).parent()[0];
				while (parent != null && parent != undefined) {
					if (parent.scrollLeft == undefined) {
						break;
					}
					left += parent.scrollLeft;
					parent = $(parent).parent()[0];
				}
				
				return left;
			};
			
			//获取元素离屏幕的垂直位置
			//这里已经对html元素scrollTop进行了计算
			selection.getScrollTop = function(target) {
				target = $(target)[0];
				var top = target.scrollTop;
				var parent = $(target).parent()[0];
				while (parent != null && parent != undefined) {
					if (parent.scrollTop == undefined) {
						break;
					}
					top += parent.scrollTop;
					parent = $(parent).parent()[0];
				}
				return top;
			};
			
			//获取元素离屏幕的水平位置
			selection.getOffsetLeft = function(target) {
				target = $(target)[0];
				var left = target.offsetLeft;
				var parent = $(target).parent()[0];
				while (parent != null && parent != undefined) {
					if (parent.offsetLeft == undefined) {
						break;
					}
					left += parent.offsetLeft;
					parent = $(parent).parent()[0];
				}
				
				return left;
			};
			
			//获取元素离屏幕的垂直位置
			selection.getOffsetTop = function(target) {
				target = $(target)[0];
				var top = target.offsetTop;
				var parent = $(target).parent()[0];
				while (parent != null && parent != undefined) {
					if (parent.offsetTop == undefined) {
						break;
					}
					top += parent.offsetTop;
					parent = $(parent).parent()[0];
				}
				
				return top;
			};
			
			//获取当前答题卡图片url
			selection.getAnswerCardImageUrl = function(index) {
				var url = null;
				var template = null;
				for(var i = 0; i < window.examObj.answerCardCuttingTemplates.length; i++) {
					template = window.examObj.answerCardCuttingTemplates[i];
					
					if(template.index == index) {
						url = template.url;
						break;
					}
				}
				
				return url;
			};

			selection.saveOrignalExamSize = function(img) {
				$(img).on('load', function() {
					selection.orignalWidth = $(img).width();
					selection.orignalHeight = $(img).height();
					
					//更改各个元素的位置
					selection.updateLayoutByScale();
					//更改整个试卷的宽高
					selection.changeExamPagerSize();
				});
			}
			
			//初始化试卷内容，添加选框宽高提示框，添加题目信息框
			selection.initUI = function() {
				//1.初始化试卷内容
				var img = document.createElement('img');
				$(img).addClass('testpaperImage');
				var imageUrl = selection.getAnswerCardImageUrl(selection.answerCardImageIdx);
				if(!imageUrl) {
					throw new Error('获取答题卡图片地址失败!');
				}
				
				$(selection.target).append(img);
				$(img).attr('src', imageUrl);
				//将试卷原始大小保存起来，后面缩放需要使用到
				selection.saveOrignalExamSize($(img));
				
				//2.添加宽高提示框
				var html = '<div class="size" style="display:none;">'
								+ '<span id="width-tip" class="tip">30</span>'
								+ '<span class="tip">&nbsp;x&nbsp;</span>'
								+ '<span id="height-tip" class="tip">20</span>'
						 + '</div>';
				
				$(selection.target).append($(html));
				
				//3.添加题目信息框
				var qhtml = 
					'<div class="control-content" style="display:none;">'
					+ '<div class="panel panel-info">'
						+ '<div class="panel-heading" style="position:relative;">'
							+ '<span>题目信息</span>'
							+ '<span class="span-btn add-btn" data-toggle="popover" data-content="新建小题信息" data-container="body" data-placement="bottom"></span>'
							+ '<span class="span-btn close-btn"></span>'
						+ '</div>'
						+ '<div class="panel-body" style="width:100%;height:360px;overflow:auto;">'
							+ '<table class="table no-border">'
								+ '<tr>'
									+ '<td>题号</td>'
									+ '<td><input type="text" id="name" name="name" class="form-control"></td>'
								+ '</tr>'
//								+ '<tr>'
//									+ '<td>满分值</td>'
//									+ '<td><input id="fullScore" type="text" name="fullScore" class="form-control"></td>'
//								+ '</tr>'
//								+ '<tr>'
//								+ '<td>评次</td>'
//								+ '<td><input type="text" id="requiredPinci" name="requiredPinci" class="form-control"></td>'
//								+ '</tr>'
//								+ '<tr>'
//								+ '<td>误差</td>'
//								+ '<td><input id="maxerror" type="text" name="maxerror" class="form-control"></td>'
//								+ '</tr>'
								+ '<tr>'
									+ '<td colspan="2">'
										+ '<span>满分值</span>&nbsp;<input id="fullScore" type="text" name="fullScore" style="width:45px;display:inline-block;" class="form-control">&nbsp;&nbsp;'
										+ '<span>评次</span>&nbsp;<input type="text" id="requiredPinci" name="requiredPinci" style="width:45px;display:inline-block;" class="form-control">&nbsp;&nbsp;'
										+ '<span>误差</span>&nbsp;<input id="maxerror" type="text" name="maxerror" style="width:45px;display:inline-block;" class="form-control">'
									+ '</td>'
								+ '</tr>'
//								+ '<tr>'
//									+ '<td>X坐标(px)</td>'
//									+ '<td><span id="left" class="label label-success"></span></td>'
//								+ '</tr>'
//								+ '<tr>'
//									+ '<td>Y坐标(px)</td>'
//									+ '<td><span id="top" class="label label-success"></span></td>'
//								+ '</tr>'
//								+ '<tr>'
//									+ '<td>宽度(px)</td>'
//									+ '<td><span id="width" class="label label-info"></span></td>'
//								+ '</tr>'
//								+ '<tr>'
//									+ '<td>高度(px)</td>'
//									+ '<td><span id="height" class="label label-info"></span></td>'
//								+ '</tr>'
								+ '<tr>'
									+ '<td colspan="2">'
										+ '<span>X</span>&nbsp;<span id="left" class="label label-success"></span>&emsp;'
										+ '<span>Y</span>&nbsp;<span id="top" class="label label-success"></span>&emsp;'
										+ '<span>宽度</span>&nbsp;<span id="width" class="label label-info"></span>&emsp;'
										+ '<span>高度</span>&nbsp;<span id="height" class="label label-info"></span>'
									+ '</td>'
								+ '</tr>'
//								+ '<tr>'
//									+ '<td>小题定义</td>'
//									+ '<td><span class="span-btn add-btn"></span></td>'
//								+ '</tr>'
								+ '<tr>'
									+ '<td colspan="2" class="sub-question-container"></td>'
								+ '</tr>'
							+ '</table>'
						+ '</div>'
					+ '</div>'
				+ '</div>';
				$(selection.target).append($(qhtml));
			};
			
			// 监听按键，点击删除按钮删除用户选择的element
			selection.enable_key_del = function() {
				$('body').keyup(function(e) {
					//获取客户点击的按键
					var e=e||event;
					var keyCode = e.keyCode || e.which || e.charCode;
					if(keyCode == 46) {
						if(selection.selectedList.length != 0) {
							for(var i = 0; i < selection.selectedList.length; i++) {
								selection.selectedList[i].del();
							}
						}
						
						//将所选元素删除之后直接清空选中列表
						selection.selectedList.length = 0;
					}
				});
			};
			
			//防止试卷图片被拖动
			selection.preventImageDrag = function() {
				$(document.images).each(function() {
					$(this)[0].ondragstart = function() {return false;}
				});
			};
			
		}
		
		var o ={
				newInstance : function(target){
					return new Selection(target);
				}
		};
		return o;
	});
})();
