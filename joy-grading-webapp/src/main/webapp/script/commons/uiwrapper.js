(function() {
	"use strict";
	define([ 'jquery', 'dialog' ], function($, dialog) {
		var DialogSize = {SM:'sm',MD:'md',LG:'lg'}
		var button = {
				type : 'button',
				text :  "确定",
				clazz : 'btn-default'
		}
		var self ;
		var ui = self = {
				modal:function(title, content, size,buttons){
					var opts = {
							size : size||DialogSize.SM,
							header : {
								show : true,
								text : title||""
							},
							body :content||""
						}
					if($.isArray(buttons)){
						opts['footer'] = {
								show : true,
								buttons:[]
						}
						$.each(buttons,function(i){
							var btn = {}
							$.extend(true,btn,button,this);
							opts.footer.buttons[i] =btn;
						});
					}
					return dialog.modal(opts);
				}
		};
		return ui;
	});
})();