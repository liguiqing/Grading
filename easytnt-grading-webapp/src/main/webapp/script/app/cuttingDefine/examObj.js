(function() {
	'use strict';
	var deps = [ 'jquery', "easyui"];
	define(deps, function($, easyui, Resize, SubQuestionPanel) {
		//用于记录试卷对象
		function ExamObj() {
			this.current = 0;//计数器，记录当前被使用的答题卡索引
			this.paperId = 1;//当前试卷id
			this.answerCardCuttingTemplates = [{
				index: 0,
				url: window.app.rootPath + 'static/css/images/shijuan.jpg'
			},{
				index: 1,
				url: window.app.rootPath + 'static/css/images/shijuan.jpg'
			}];
			this.examPapers = [];//每一张试卷都对应一个selection对象
		}
		
		var o={
				newInstance:function(){
					return new ExamObj();
				}
		};
		return o;
	});
})();