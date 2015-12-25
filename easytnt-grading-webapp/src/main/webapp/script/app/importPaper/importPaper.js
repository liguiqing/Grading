(function() {
	'use strict';
	var deps = [ 'jquery', 'ajaxwrapper', 'smartWizard',
			'../../commons/progress', 'dialog' ];
	define(deps, function($, ajaxWrapper, smartWizard, progress, dialog) {
		var obj = function() {
			var me = this;
			function getCurStepContainer(step) {
				return $('#step' + step + '_container');
			}

			this.getConfig = function() {
				var data = {};
				data['testId'] = $('#testId').val();
				data['rootUrl'] = $('#rootUrl').val();
				data['fileDir'] = $('#fileDir').val();

				if ($('#fileSize').val()) {
					data['fileSize'] = $('#fileSize').val();
				}

				var $mappingFields = $('.mappingFiled');

				if ($mappingFields.size() > 0) {
					var mappingFields = new Array();
					$mappingFields.each(function(i) {
						var value = $(this).val();
						var f = {
							place : i,
							mappingName : value
						};
						mappingFields.push(f);
					});
					data['directoryMappings'] = mappingFields;
				}

				return data;
			};
			this.startScan = function(step) {
				var $container = getCurStepContainer(step);
				var data = me.getConfig();
				var saveToDb = step === 2 ? 0 : 1;
				var url = '/importPaper/' + saveToDb + '/scan';
				ajaxWrapper.postJson(url, data, {}, function(data) {
					$('#uuId').val(data.config.uuId);
					me.startProgress(step);
				});
			};
			this.startProgress = function(step) {
				var $container = getCurStepContainer(step);
				$container.empty();
				var fileSize = $('#fileSize').val();
				if (!fileSize) {
					fileSize = 1;
				}
				var param = {
					container : $container,
					entry : 'ScannerDirectoryService',
					finishedCallBack : function() {
						var uuId = $('#uuId').val();
						me.renderStatResult(uuId, step);
						$('#step' + step + 'Finished').val('true');
						$('#uuId').val('');
					},
					data : {
						uuId : $('#uuId').val(),
						type : step === 2 ? 1 : 2,
						fileSize : fileSize
					}
				};
				progress.init(param);
			};
			this.renderStatResult = function(uuId, step) {
				var type = step === 2 ? 1 : 2;
				var data = me.getConfig();
				var url = '/importPaper/' + type + '/stat/' + uuId;
				ajaxWrapper.postHtml(url, data, {}, function(html) {
					getCurStepContainer(step).html(html);
					$('.actionBar .buttonNext').removeClass('buttonDisabled')
							.attr("disabled", false);
					$('.actionBar .buttonPrevious').removeClass(
							'buttonDisabled').attr("disabled", false);
				});
			};

			function viewImage() {
				$('#importPaperWizard').on(
						'click',
						'.viewImage',
						function() {
							var $this = $(this);
							var imagePath = $this.attr("imagePath");

							dialog.modal({
								header : {
									text : '预览图片'
								},
								footer : {
									show : false
								},
								body : '<img src="' + imagePath
										+ '" style="width:100%;height:300px;">'
							});
						});
			}
			this.render = function() {
				$('#myBotton').click(function() {
					console.log("ddddddddddddd");
				});

				viewImage();
				$('#importPaperWizard').smartWizard(
						{
							transitionEffect : 'slide',
							labelNext : '下一步',
							labelPrevious : '上一步',
							labelFinish : '完成',
							enableAllSteps : false,
							noForwardJumping : true,
							onLeaveStep : function(container, obj) {
								if (obj.fromStep === 1 && obj.toStep === 2) {
									if ($('#step2Finished').val() === 'false') {
										me.startScan(2);
									}
								} else if (obj.fromStep === 2
										&& obj.toStep === 3) {
									if ($('#step3Finished').val() === 'false') {
										me.startScan(3);
									}
								}
								return true;
							},
							onShowStep : function(container, obj) {
								if (obj.fromStep === 1 && obj.toStep === 2) {
									if ($('#step2Finished').val() === 'false') {
										$('.actionBar .buttonNext').addClass(
												'buttonDisabled').attr(
												"disabled", true);
										$('.actionBar .buttonPrevious')
												.addClass('buttonDisabled')
												.attr("disabled", true);
									}

								} else if (obj.fromStep === 2
										&& obj.toStep === 3) {
									if ($('#step3Finished').val() === 'false') {
										$('.actionBar .buttonNext').addClass(
												'buttonDisabled').attr(
												"disabled", true);
										$('.actionBar .buttonPrevious')
												.addClass('buttonDisabled')
												.attr("disabled", true);
									}

								}

								return false;
							},
							onFinish : function() {
								// TODO点击完成按钮
								console.log("11111")
							}
						});

			};
		}
		return new obj();
	});
})();