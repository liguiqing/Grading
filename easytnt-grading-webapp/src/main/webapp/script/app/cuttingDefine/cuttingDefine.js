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
					clearCanvas();
					
					//将当前试卷保存
					window.examObj.examPapers.push(window.selection);
					
					initSelection();
				});
				
				
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
				console.info(window.examObj);
				
			}
			
			//初始化试卷可以创建选区
			function initSelection() {
				var selection = Selection.newInstance('.image-content');
				window.selection = selection;
				selection.init();
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