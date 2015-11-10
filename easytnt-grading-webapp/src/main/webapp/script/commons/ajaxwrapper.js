(function() {
	"use strict";
	define([ 'jquery', 'ajax','dialog' ], function($, ajax,dialog) {
		var Method = {GET:'GET',POST:'POST',PUT:'PUT',DELETE:'DELETE'}
		var DataType = {JSON:'json',HTML:'html'};
		function doAjax(opts) {

			var beforeMsg = {
					show : false
			};			
			$.extend(true, beforeMsg, opts.messages.beforeMsg);
			
			var successMsg = {
					show : false,
					header : {
						show : false
					}
			};
			$.extend(true, successMsg, opts.messages.successMsg);
			
			var errorMsg = {
					moveable : true
			};			
			$.extend(true, errorMsg, opts.messages.errorMsg);
            
			var warningMsg = {
					header : {show:true}
			};			
			$.extend(true, warningMsg, opts.messages.warningMsg);
			
			if (!opts.messages.successMsg)
				successMsg.show=false;

			var ajaxOpts = {
				url : opts.url,
				beforeMsg :beforeMsg,
				successMsg :successMsg,
				warningMsg :warningMsg,
				errorMsg :errorMsg,
				type : opts.method,
				callback : function(data) {
					if (opts.callback)
						opts.callback(data);
				}
			};
			if(!$.isEmptyObject(opts["data"])){
				if(opts.method === Method.GET){
					ajaxOpts["data"]=opts.data;
				}else{
					ajaxOpts["data"] = JSON.stringify(opts.data);
				}
				
			}
			
			//ajaxOpts["data"]=opts.data;
			
			if(!$.isEmptyObject(opts["contentType"])){
				ajaxOpts["contentType"] = opts["contentType"];
			}

			if (opts.dataType)
				ajaxOpts["dataType"] = opts.dataType;
			ajax(ajaxOpts);
		};
		
		/**
		 * @param url
		 * @param data a json data
		 * @param dataType a type of DataType 
		 * @param messages a json data 
		 *             通常情况下只要定义 successMsg.tipText；有必要可以定义beforeMsg.tipText
		 *             完整定义请参考
		 *                    <link>ajax.defaultSettings.beforeMsg</link>
		 *                    <link>ajax.defaultSettings.successMsg</link>
		 *                    <link>ajax.defaultSettings.warningMsg</link>
		 *                    <link>ajax.defaultSettings.errorMsg</link>
		 * @param mehtod a type of Method            
		 * @param callback a function
		 */
		function doSend(url,data,dataType,messages,method,callback){
			doAjax({
				url : url,
				data : data||{},
				dataType : dataType,
				messages : messages,
				callback : callback,
				method : method
			});				
		};
		
		var  wrapper = {
			post:function(url,data,dataType,messages,callback){
				doSend(url,data,dataType,messages,Method.POST,callback);
			},
			put:function(url,data,dataType,messages,callback){
				doSend(url,data,dataType,messages,Method.PUT,callback);
			},
			get:function(url,data,dataType,messages,callback){
				doSend(url,data,dataType,messages,Method.GET,callback);
			},
			remove:function(url,data,dataType,messages,callback){
				doSend(url,data,dataType,messages,Method.DELETE,callback);
			},
			postJson : function(url,data,messages,callback){
				doSend(url,data,DataType.JSON,messages,Method.POST,callback);				
			},
			postHtml : function(url,data,messages,callback){
				doSend(url,data,DataType.HTML,messages,Method.POST,callback);				
			},
			putJson:function(url,data,messages,callback){
				doSend(url,data,DataType.JSON,messages,Method.PUT,callback);
			},
			removeJson:function(url,data,messages,callback){
				doSend(url,data,DataType.JSON,messages,Method.PUT,callback);
			},
			getJson:function(url,data,messages,callback){
				doSend(url,data,DataType.JSON,messages,Method.GET,callback);
			},
			getHtml:function(url,data,messages,callback){
				doSend(url,data,DataType.HTML,messages,Method.GET,callback);
			},
			send:function(url,data,dataType,contentType,messages,callback){
				doAjax({
					url : url,
					data : data||{},
					dataType : dataType,
					contentType:contentType,
					messages : messages,
					callback : callback,
					method : method
				});	
			}
		};
		
		return  wrapper;
	});
})();