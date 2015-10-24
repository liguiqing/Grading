(function() {
	'use strict';
	var deps = [ 'jquery', 'ajaxwrapper', 'smartWizard',
			'../../commons/progress' ];
	define(deps, function($, ajaxWrapper, smartWizard, progress) {
		var obj = function() {
			var me = this;
			function getCurStepContainer(step) {
				return $('step' + step + '_container');
			}

			this.getConfig = function() {
				var data = {};
				data['testId'] = $('#testId').val();
				data['rootUrl'] = $('#rootUrl').val();
				data['fileDir'] = $('#fileDir').val();
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
				var param = {
					container : $container,
					entry : '',
					finishedCallBack : function() {
						var uuId = $('#uuId').val();
						renderStatResult(uuId, step);
						$('step' + step + 'Finished').val('true');
						$('#uuId').val('');
					},
					data : {
						uuId : $('#uuId').val()
					}
				};
				progress.init(param);
			};
			this.renderStatResult = function(uuId, step) {
				var type = step === 2 ? 1 : 2;
				var url = '/importPaper/' + type + '/stat/' + uuId;
				ajaxWrapper.getHtml(url, {}, {}, function(html) {
					getCurStepContainer(step).html(html);
				});
			};
			this.render = function() {
				$('#importPaperWizard').smartWizard({
					transitionEffect : 'slide',
					labelNext : '上一步',
					labelPrevious : '下一步',
					labelFinish : '完成',
					enableAllSteps : false,
					noForwardJumping : true,
					onLeaveStep : function(container, obj) {
						if (obj.fromStep === 1 && obj.toStep === 2) {
							if ($('step2Finished').val === 'false') {

							}
						} else if (obj.fromStep === 2 && obj.toStep === 3) {
							if ($('step3Finished').val === 'false') {

							}
						}
						return true;
					},
					onShowStep : function(container, obj) {
						// TODO渲染页面
						return false;
					},
					onFinish : function() {
						// TODO点击完成按钮
					}
				});

			};
		}
		return new obj();
	});
})();