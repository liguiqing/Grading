(function() {
	'use strict';
	var deps = [ 'jquery', "easyui" ];
	define(deps, function($,easyui) {
		function Resize(target) {
			var resize = this;
			resize.target = target;
			//初始化各个点的拖动事件
			//@param ui : 被拖动的点
			resize.left = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var w = $(resize.target.view).width();
							var distanceX = currentX - x;//水平移动距离
							var left = currentX - selection.getOffsetLeft(selection.target);
							
							var width = w - distanceX;
							var position = resize.resize_constrain(left, $(resize.target.view)[0].offsetTop, width, $(resize.target.view).height());
							if(position.left == 0) {
								return;
							}
							$(resize.target.view).css({
								left:position.left,
								width : position.width  + 'px'
							});
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.right = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var w = $(resize.target.view).width();
							var distanceX = currentX - x;//水平移动距离
							
							var width = w + distanceX;
							
							var position = resize.resize_constrain($(resize.target.view)[0].offsetLeft, $(resize.target.view)[0].offsetTop, width, $(resize.target.view).height());
							$(resize.target.view).css({
								width : position.width  + 'px'
							});
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.up = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var h = $(resize.target.view).height();
							var distanceY = currentY - y;//垂直移动距离
							var top = currentY - selection.getOffsetTop(selection.target);
							var height = h - distanceY;
							var position = resize.resize_constrain($(resize.target.view)[0].offsetLeft, top, $(resize.target.view).width(), height);
							
							if(position.top == 0) {
								return;
							}
							
							$(resize.target.view).css({
								top : position.top,
								height : position.height  + 'px'
							});
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.down = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var h = $(resize.target.view).height();
							var distanceY = currentY - y;//垂直移动距离
							var height = h + distanceY;
							var position = resize.resize_constrain($(resize.target.view)[0].offsetLeft, $(resize.target.view)[0].offsetTop, $(resize.target.view).width(), height);
							
							$(resize.target.view).css({
								height : position.height  + 'px'
							});
							
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.leftUp = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var h = $(resize.target.view).height();
							var w = $(resize.target.view).width();
							var distanceY = currentY - y;//垂直移动距离
							var distanceX = currentX - x;//水平移动距离
							var left = currentX - selection.getOffsetLeft(selection.target);
							var top = currentY - selection.getOffsetTop(selection.target);
							
							var width = w - distanceX;
							var height = h - distanceY;
							var position = resize.resize_constrain(left, top, width, height);
							
							if(position.left == 0 && position.top != 0) {
								$(resize.target.view).css({
									height : position.height  + 'px',
									top : position.top,
									left : position.left
								});
							}else if(position.top == 0 && position.left != 0) {
								$(resize.target.view).css({
									width : position.width  + 'px',
									top : position.top,
									left : position.left
								});
							}else if(position.left != 0 && position.top != 0){
								$(resize.target.view).css({
									height : position.height  + 'px',
									width : position.width  + 'px',
									top : position.top,
									left : position.left
								});
							}else {
								return;
							}
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.rightUp = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var h = $(resize.target.view).height();
							var w = $(resize.target.view).width();
							var distanceY = currentY - y;//垂直移动距离
							var distanceX = currentX - x;//水平移动距离
							var top = currentY - selection.getOffsetTop(selection.target);
							var width = w + distanceX;
							var height = h - distanceY;
							var position = resize.resize_constrain($(resize.target.view)[0].offsetLeft, top, width, height);
							
							if(position.top == 0) {
								$(resize.target.view).css({
									width : position.width  + 'px',
									top : position.top
								});
							}else {
								$(resize.target.view).css({
									height : position.height  + 'px',
									width : position.width  + 'px',
									top : position.top
								});
							}
							
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.leftDown = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var h = $(resize.target.view).height();
							var w = $(resize.target.view).width();
							var distanceY = currentY - y;//垂直移动距离
							var distanceX = currentX - x;//水平移动距离
							var left = currentX - selection.getOffsetLeft(selection.target);
							
							var width = w - distanceX;
							var height = h + distanceY;
							
							var position = resize.resize_constrain(left, $(resize.target.view)[0].offsetTop, width, height);
							
							if(position.left == 0) {
								$(resize.target.view).css({
									height : position.height  + 'px',
									left : position.left
								});
							}else {
								$(resize.target.view).css({
									height : position.height  + 'px',
									width : position.width  + 'px',
									left : position.left
								});
							}
							
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};

			resize.rightDown = function(ui) {
				var isResize = false;
				$(ui).mousedown(function(e) {
					selection.showSize = true;
					selection.prevent_event(e);
					isResize = true;
					var position = selection.get_current_position(e);
					var x = position.x;
					var y = position.y;
					$(document).mousemove(function(e) {
						selection.prevent_event(e);
						if(isResize) {//开始拖动
							var position = selection.get_current_position(e);
							var currentX = position.x;
							var currentY = position.y;
							var h = $(resize.target.view).height();
							var w = $(resize.target.view).width();
							var distanceY = currentY - y;//垂直移动距离
							var distanceX = currentX - x;//水平移动距离
							
							$(resize.target.view).css({
								height : (h + distanceY)  + 'px',
								width : (w + distanceX)  + 'px'
							});
							
							x = currentX;
							y = currentY;
							
							resize.change_element_status(e);
						}
					});

					$(document).mouseup(function() {
						selection.showSize = false;
						selection.prevent_event(e);
						isResize = false;
						$(document).unbind('mousemove');
						$(document).unbind('mouseup');
					});
				});

				return resize;
			};
			
			//设置大小之后改变元素当前状态
			resize.change_element_status = function(e) {
				//如果用户改变元素大小为单选操作
				selection.record_current_element(resize.target);
				//保存上一个选中元素的数据
				//selection.currentElement.save_preview_element_data();
				//重新显示当前元素的数据
				selection.show_data();
				//选中当前元素
				selection.select_element(selection.currentElement, e);
				//设置中间的4个助托点居中
				resize.position_middle_point();
			};
			
			//重新调整中间四个点的位置居中
			resize.position_middle_point = function() {
				//调整中间4个点的位置居中
				var parentWidth = $(resize.target.view).width();
				var parentHeight = $(resize.target.view).height();
				var up = $(resize.target.view).find('.up');
				var down = $(resize.target.view).find('.down');
				var left = $(resize.target.view).find('.left');
				var right = $(resize.target.view).find('.right');
				
				var size = $(up).outerWidth();
				
				$(up).css({
					left : (parentWidth - size) / 2 + 'px'
				});
				$(down).css({
					left : (parentWidth - size) / 2 + 'px'
				});
				
				$(left).css({
					top : (parentHeight - size) / 2 + 'px'
				});
				$(right).css({
					top : (parentHeight - size) / 2 + 'px'
				});
			};
			
			//对拖动的元素位置进行矫正
			resize.resize_constrain = function(left, top, width, height) {
				var target = resize.target.view;
				
				if(left < 0) {
					left = 0;
				}
				
				if(top < 0) {
					top = 0;
				}
				
				//默认最小宽高为10
				if(width < 1) {
					width = 1;
				}
				
				if(height < 1) {
					height = 1;
				}
				
				if (left + width > $(target).parent()[0].scrollWidth) {// 目的就是为了禁止元素超出编辑区
					width = $(target).parent()[0].scrollWidth - left;
				}
				if (top + height > $(target).parent()[0].scrollHeight) {
					height = $(target).parent()[0].scrollHeight - top;
				}
				
				var position = {
					left : left,
					top : top,
					width:width,
					height:height
				}
				
				return position;
			}
		}
		
		var o={
				newInstance : function(target){
					return new Resize(target);
				}
		};
		return o;

	});
})();