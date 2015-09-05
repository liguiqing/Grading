(function() {
	"use strict";
	define([ 'jquery', 'logger' ], function($, logger) {

		var ShortKey = function() {
			var _shortKeyEvents = [];
			function _init() {
				
			};
			
			function _onKey(e,targetEventCode,callback){
				var eventCode = e.which||e.keyCode;
				if(eventCode == targetEventCode){
					callback();
				}
			};

			/**
			 * 首次使用时，必须调用
			 */
			this.init = function(point,grading,conf) {
				_point = point;
				_grading = grading;
				_init();
			};
			
			this.registry = function(eventCode,callback){
				_shortKeyEvents.push({eventCode:eventCode,eventCallback:callback});
				return this;
			};
			
			this.bindTo = function(obj,event){
				$(obj).off(event).on(event,function(e){
					var eventCode = e.which||e.keyCode;
					$.each(_shortKeyEvents,function(i,k){
						if(k.eventCode == eventCode){
							k.eventCallback();
							return false;
						}
					});
				});
			};
			
			this.onEnter = function(obj,callback){
				$(obj).off('keydown').on('keydown',function(e){
					_onKey(13,callback);
				});
			};
			
			this.onNumber = function(callback){
				
			};
			
			this.onLetter = function(callback){
				
			};
		};
		return new ShortKey();
	});
})();