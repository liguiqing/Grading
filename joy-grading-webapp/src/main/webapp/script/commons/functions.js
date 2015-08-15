/**
 * 公共函数
 * 
 */

(function() {
	"use strict";
	define([], function() {
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
			elment.value = elment.value.replace(".", "$#$").replace(/\./g, "")
					.replace("$#$", ".");
			elment.value = elment.value.replace(/^(\-)*(\d+)\.(\d).*$/,
					'$1$2.$3'); // 只能输入一个小数
		};

		window['divisive'] = function(exp1, exp2) {
			var n1 = Math.round(exp1); // 四舍五入
			var n2 = Math.round(exp2); // 四舍五入
			var rslt = n1 / n2; // 除
			if (rslt >= 0) {
				rslt = Math.floor(rslt); // 返回值为小于等于其数值参数的最大整数值。
			} else {
				rslt = Math.ceil(rslt); // 返回值为大于等于其数字参数的最小整数。
			}
			return rslt;
		}
	});
})();