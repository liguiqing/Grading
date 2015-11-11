(function() {
	'use strict';
	var deps = [ 'jquery', 'ajaxwrapper', 'dialog',"easyui", "./selection"];
	define(deps, function($, ajaxWrapper, dialog,easyui, Selection) {
		var obj = function() {
			var me = this;
			this.render = function() {
				console.log("ddddd")
				//浏览器自适应高度
				this.setContainerHeight();
				$(window).resize(function() {
					me.setContainerHeight();
				});
				kk();
				
				entrance();
				
				initEvent();
			};
			
			//用于记录试卷对象
			function ExamObj() {
				this.paperId = 1;//当前试卷id
				this.answerCardCuttingTemplates = [{
					index: 0,
					url: window.app.rootPath + 'static/css/images/shijuan.jpg'
				},{
					index: 1,
					url: window.app.rootPath + 'static/css/images/shijuan.jpg'
				}];
				this.examPapers = [];//每一张试卷都对应一个selection对象
			}
			
			//初始化底部工具栏中按钮点击事件
			function initEvent() {
				$('#nextBtn').click(function() {
					stage_unsaved_element();
					//判断当前元素是否有下一页答题卡，并且之前已经被处理过
					var selection = getNextSelection();
					if(selection != null) {
						//存在下一页，并之前已经被处理过，就恢复到下一页继续编辑
						//恢复到下一页
						recover_selection(selection);
						
					}else {//这里后面需要根据传递过来的数据进行判断，是否还可以继续往下翻页
						clearCanvas();
						initSelection();
					}
					
					$('#previousBtn')[0].disabled = false;//启用上翻功能
				});
				
				$('#previousBtn').click(function() {
					stage_unsaved_element();
					//只要在第二页及其以上，就可以往上翻页
					var selection = getPreviousSelection();
					//恢复到上一页
					recover_selection(selection);
					//判断下一次是否还可以继续往上翻
					var enable = previousEnable();
					if(!enable) {//不可以翻页
						$('#previousBtn')[0].disabled = true;
					}
					
				});
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
			
			//恢复指定答题卡页面内容
			function recover_selection(selection) {
				//初始化试卷状态
				clearCanvas();
				selection.recover();
				window.selection = selection;//标记当前处理的答题卡
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
			function previousEnable() {
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
			
			function entrance() {
				window.examObj = new ExamObj();
				initSelection();
			}
			
			//清除画布中内容
			function clearCanvas() {
				var target = $('.image-content');
				$(target).empty();
				//取消对画布容器设置的mousedown事件监听
				$(target).unbind('mousedown');
			}
			
			//初始化试卷可以创建选区
			function initSelection() {
				var selection = Selection.newInstance('.image-content');
				window.selection = selection;
				selection.init();
				//将当前试卷保存
				window.examObj.examPapers.push(window.selection);
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