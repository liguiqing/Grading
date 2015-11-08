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
			};
			
			function entrance() {
				preventImageDrag();
				initSelection();
				
			}
			
			//初始化试卷可以创建选区
			function initSelection() {
				var selection = Selection.newInstance('.image-content');
				window.selection = selection;
				selection.init();
			}
			
			//防止试卷图片被拖动
			function preventImageDrag() {
				$(document.images).each(function() {
					$(this)[0].ondragstart = function() {return false;}
				});
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