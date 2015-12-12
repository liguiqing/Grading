(function() {
	'use strict';
	var deps = [ 'jquery' ];
	define(deps, function($) {
		
		/*小题面板*/
		//如果是遍历小题集合，需要传递index
		//如果是按钮点击添加，则不需要传递index,index=小题集合长度
		function SubQuestionPanel(index) {
			var panel = this;
			panel.view = create_sub_question_panel(index);
			
			//添加panel事件
			panel.init_event = function() {
				//添加分值onchange事件
				$(panel.view).find('input[name=subFullScore]').change(function() {
					init_sub_question_values($(panel.view));
				});
				
				//添加给分率onchange事件
				$(panel.view).find('select#seriesScore').change(function() {
					init_sub_question_values($(panel.view));
				});
				
				$(panel.view).find('input[name=interval]').change(function() {
					init_sub_question_values($(panel.view));
				});
			};
			
			//关闭题目信息弹出框按钮样式
			panel.close_btn_style = function() {
				//添加关闭按钮样式
				var closeBtn = $(panel.view).find('.close-btn');
				closeBtn[0].panel = panel;//缓存面板对象，便于获取
				var closebtn_defaulticon = window.app.rootPath + 'static/css/images/close_default.png';
				var closebtn_focusicon = window.app.rootPath + 'static/css/images/close_focus.png';
				selection.btn_mouse_style($(closeBtn), closebtn_defaulticon, closebtn_focusicon);
				//添加关闭按钮点击事件
				$(closeBtn).click(function() {
					//得到面板
					var panelview = $(this)[0].panel.view;
					
					var id = panelview.attr('id');
					
					//删除小题定义数组中的当前小题对象
					selection.currentElement.data.giveScorePoints[id] = null;//当前还不能彻底删除该对象，会对后面元素位置有影响，所以只设置为空
					$(panelview).remove();
				});
			};
			
			//创建后初始化事件
			panel.init_event();
			//小题窗口关闭样式
			panel.close_btn_style();
		}

		//赋值小题数据
		function init_sub_question_values(panelview) {
			//得到用户输入的分值
			var fullScoreInput = $(panelview).find('input[name=subFullScore]');
			//得到要设置的分序列
			var validValuesInput = $(panelview).find('input[name=validValues]');
			//分值间隔
			var intervalInput = $(panelview).find('input[name=interval]');
			
			var seriesScore = $(panelview).find('select#seriesScore').val();
			seriesScore = Number(seriesScore);
			//判断当前选中的是连续还是不连续
			if(!seriesScore) {//不连续
				$(validValuesInput).removeAttr('readonly');
				$(intervalInput).css({display:'none'});
				$(validValuesInput).val('');
			}else {
				$(validValuesInput).attr({
					readonly : 'readonly'
				});
				$(intervalInput).css({display:'inline'});
				var fullScore = $(fullScoreInput).val();
				var interval = $(intervalInput).val();
				var val = get_score_rate_val(Number(fullScore), Number(interval));
				$(validValuesInput).val(val);
			}
		}

		//根据rate值拼接字符串
		function get_score_rate_val(score, interval) {
			var val = '';
			if(score != '' && !isNaN(score)) {
				var num = 0;
				while(num <= score) {
					val += num;
					if(num < score) {
						val += ',';
					}else if (num == score) {
						break;
					}
					
					num += interval;
				}
				
				if(num > score) {
					val += score;
				}
			}
			
			return val;
		}

		//面板内容布局
		function create_sub_question_panel(index) {
			if(index == undefined) {
				index = selection.currentElement.data.giveScorePoints.length;
			}
			var panel = $('#subQuestionPanel').clone();
			
			$(panel).attr('id', index);
			return panel;
		}
		
		var o={
				newInstance:function(index){
					return new SubQuestionPanel(index);
				}
		};
		return o;
		
	});
})();