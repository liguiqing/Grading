(function() {
	"use strict";

	define([ 'jquery', 'dialog','lib/jquery/ajaxfileupload' ],function($, dialog) {
				var defaultSettings = {
					target : $(document.body),
					type : 'GET',
					contentType : 'application/json',
					dataType : 'json',
					timeOut : 1000,
					beforeMsg : {
					    backdrop : false,
						show : true,
						header : {
							show : false
						},
						tipText : '系统正在处理，请稍候...',
						iconInfo : 'update-ing',
						footer : {
							show : false
						},
						untransparent : false
					},
					successMsg : {
						show : false,
						delay : 1000,
						tipText : '操作成功！',
						iconInfo : 'success',
						header : {
							show : true,
							text : '操作成功'
						}
					},
					warningMsg : {
						iconInfo : 'warning',
						header : {
							show : true,
							text : '操作失败'
						}
					},
					errorMsg : {
						tipText : '操作失败！',
						iconInfo : 'error',
						moveable : true,
						header : {
							show : true,
							text : '操作失败'
						},
						footer : {
							show : true,
							buttons : [ {
								type : 'submit',
								text : "确定",
								clazz : 'primary',
								callback : function() {
									$(this).trigger('close');
								}
							} ]
						}
					}
				};

				var o = function(opts) {
					var settings = {};
					$.extend(true, settings, defaultSettings, opts);

					var target = settings.target;
					settings.beforeMsg.target = settings.beforeMsg.target ? settings.beforeMsg.target: target;
					settings.successMsg.target = settings.successMsg.target ? settings.successMsg.target: target;
					settings.errorMsg.target = settings.errorMsg.target ? settings.errorMsg.target: target;

					var $overlay = undefined;

					var _url = window.app.rootPath + settings.url;
					if (settings.url.substring(0, 1) === '/') {
						var length = opts.url.length;
						_url = window.app.rootPath + settings.url.substring(1, length);
					}

					return (function() {
						var ajaxOpts = {
							url : encodeURI(_url),
							type : settings.type,
							contentType : settings.contentType,
							dataType : settings.dataType,
							timeOut : settings.timeOut,
							beforeSend : function() {
								if (settings.beforeMsg.show) {
									if (!$overlay) {
										$overlay = dialog.tipmodal(settings.beforeMsg);
									}
								}
							},
							complete : function() {
								/* 隐藏之后移除内容 */
								if ($overlay) {
									$overlay.complete();
								}
							},
							success : function(data, textStatus, jqXHR) {
								if (jqXHR) {
									var sessionstatus = jqXHR.getResponseHeader("sessionstatus");
									if (sessionstatus === 'timeout') {
										login(data);
										return;
									}
								}

								var tmpData = null;
								if (typeof (data) === "string") {
									try {
										tmpData = jQuery.parseJSON(data);
									} catch (e) {
									}
								} else {
									tmpData = data;
								}
								if (data.timeOut) {
									login(tmpData);
									return;
								}
								if (tmpData && tmpData.status && tmpData.status.success == false) {
									dialog.tipmodal({
										footer : {
											show : true,
											buttons : [ {
												type : 'button',
												text : "确定",
												clazz : 'btn-default',
												callback : function() {
													$(this).trigger('close');
													if($.isFunction(settings.callback))
														settings.callback(data);
												}
											} ]
										},
										tipText : '操作失败:' + tmpData.status.msg,
										iconInfo : 'error',
									});
								} else {
									//if (settings.beforeMsg.show)
									//	dialog.fadedialog(settings.beforeMsg);
									if (settings.successMsg.show)
										dialog.fadedialog(settings.successMsg);
									if($.isFunction(settings.callback))
										settings.callback(data);
								}
							},
							error : function(xhr, textStatus) {
								dialog.tipmodal(settings.errorMsg);
							}
						};

						ajaxOpts["data"] = settings.data;
						if (settings.callback)
							ajaxOpts["callback"] = settings.callback;
						if (settings.dataType === 'text') {
							ajaxOpts.fileElementId = settings.fileElementId|| "fileName";
							$.ajaxFileUpload(ajaxOpts);
						} else {
							$.ajax(ajaxOpts);
						}

					})();

				};
				return o;
			});
})();