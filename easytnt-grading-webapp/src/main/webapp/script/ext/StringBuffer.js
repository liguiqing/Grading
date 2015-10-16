(function() {
	"use strict";
	define([], function() {
		function StringBuffer() {
			this._strings = "";
			this.append = function(s) {
			};
		}
		StringBuffer.prototype.append = function(str) {
			this._strings += str;
		};
		
		StringBuffer.prototype.length = function() {
			this._strings.length;
		};

		StringBuffer.prototype.toString = function() {
			return this._strings;
		};
		return StringBuffer;
	});
})();