/**
 * 公共函数
 * 
 */

(function() {
	"use strict";
	define(['jquery'], function($) {
		window['getScrollerTop'] = function() {
			return document.body.scrollTop
					|| document.documentElement.scrollTop;
		};

		window['getClientHeight'] = function() {
			return document.documentElement.clientHeight;
		};

		window['getClientWidth'] = function() {
			return document.documentElement.clientWidth;
		};

		window['getTopOf'] = function(elment) {
			var top = 0;
			while (elment) {
				top += elment.offsetTop;
				elment = elment.offsetParent;
			}
			return top;
		};

		window['getLeftOf'] = function(elment) {
			var left = 0;
			while (elment) {
				left += elment.offsetLeft;

				elment = elment.offsetParent;
			}
			return left;
		};

		/**
		 * 输入框只能输入小数
		 */
		window['onlyNumber'] = function(elment, scal) {
			elment.value = elment.value.replace(/[^\d.]/g, ""); // 清除"数字"和"."以外的字符
			elment.value = elment.value.replace(/^\./g, ""); // 验证第一个字符是数字而不是
			elment.value = elment.value.replace(/\.{2,}/g, "."); // 只保留第一个.
																	// 清除多余的
			elment.value = elment.value.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
		};

		/*  元素拖动效果  onmouseover="comDrag(this)"  */
		window['drag'] = function (obj){
			var parent = obj.parentNode;
			var disX=0, disY=0;
			obj.onmousedown=function(ev){
				if(ev && ev.button != 0) return;
				var ev = ev || window.event;
				disX=ev.clientX-parent.offsetLeft;
				disY=ev.clientY-parent.offsetTop;
				bind(document,'mousemove',fnMove);
				obj.style.cursor = "move";
				function fnMove(ev){
					var ev= ev || event;
					var L=ev.clientX-disX;
					var T=ev.clientY-disY;
					parent.style.left=L+'px';
					parent.style.top=T+'px';
				};
				
				bind(document,'mouseup',fnUp);
				function fnUp(){
					obj.style.cursor = "default";
					remove(document,'mousemove',fnMove);
					remove(document,'mouseup',fnUp);
				};
				
				function bind(obj, evName, fn) {
					if (obj.addEventListener) {
						obj.addEventListener(evName, fn, false);
					} else {
						obj.attachEvent('on' + evName, fn);
					}
				};
				
				function remove(obj, evName, fn){
					if( obj.removeEventListener){
						obj.removeEventListener(evName,fn,false);
					}else{
						obj.detachEvent('on'+evName, fn);	
					}		
				}
				
				return false;
			};
		}
		
		window['login'] = function(data) {

		}
		Array.prototype.contains = function(obj) {
		    var i = this.length;
		    while (i--) {
		        if (this[i] === obj) {
		            return true;
		        }
		    }
		    return false;
		}		
	});
})();