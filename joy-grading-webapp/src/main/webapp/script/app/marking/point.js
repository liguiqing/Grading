(function() {
	"use strict";
	var deps = [ 'jquery','logger' ];
	define(deps, function($,logger) {
		var _reg = /\./g;
		var Point = function() {
			var _$detailPointValues = $('div.point-panel-detail input.point-input:enabled');
			var _$totalPointValue = $('div.point-panel-total input.point-input');
			this.count = function() {
				var total = 0;
				_$detailPointValues.each(function() {
					total += this.value * 1;
				});
				_$totalPointValue.val(total);
			};
			
			this.reset = function(){
				_$totalPointValue.val("");
				_$detailPointValues.val("");
				_$detailPointValues[0].focus();
			};
			
			this.next = function(){
				logger.log('next');
				var $curInput = $('div.point-panel input:focus');
				if($curInput.parents('.point-panel-total').size()){
					this.grading.record();
				}else{
					_$detailPointValues.each(function(i){
						if(this.name == $curInput[0].name){
							if(i <  _$detailPointValues.size()-1 ){
								_$detailPointValues[i+1].focus();
								return false;
							}else{
								_$totalPointValue.focus();
							}
						}
					});
				}
			};
			
			this.prev = function(){
				logger.log('prev');
				var $curInput = $('div.point-panel input:focus');
				if($curInput.parents('.point-panel-total').size()){
					_$detailPointValues.last().focus();
				}else{
					_$detailPointValues.each(function(i){
						if(this.name == $curInput[0].name){
							if(i != 0 ){
								_$detailPointValues[i-1].focus();
								return false;
							}
						}
					});
				}
			};
			
			this.validate = function(callback){
				var data = {success:true,message:"",item:{name:'item 5'}};
				var detailToal = 0;
				_$detailPointValues.each(function(){
					detailToal += this.value * 1;
				});
				if(detailToal != _$totalPointValue.val() * 1){
					data.success = false;
					data.message = "该题得分点合计分(<b style='color:#c83025'>"+detailToal+"</b>)与总分(<b style='color:#c83025'>"+ _$totalPointValue.val()+"</b>)不一致";
				}
				callback(data);
			};
			
			this.givePoint = function($input,valueAttr){
				$input.val($input.attr(valueAttr) || 0);
			};
			
			this.init = function(grading) {
				this.grading = grading;
				var self = this;
				_$detailPointValues[0].focus();
				//受父级多种样式的叠加影响，打分框的左边圆角无效
				$('div.point-panel .point-input').css({'border-top-left-radius':'4px','border-bottom-left-radius':'4px'});
				//点击打分
				$('div.point-panel').on('click', '.point-btn', function() {
					var $btn = $(this);
					var dataToggle = $btn.attr('data-toggle');
					var $input = $('#' + dataToggle);
					self.givePoint($input,$btn.attr('data-value'))
					
					if($btn.parents('div.point-panel-total').size() == 0){
						self.count();
					}else{
						if($btn.attr('data-value')=='data-from'){
							self.reset();
						}
					}
				}).on('keyup','.point-input',function(e){
					//限制打分框只能输入数值
					onlyNumber(this);
					if(this.value * 1 == 0)
						return;
					this.value = this.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3'); // 打分框只能输入一个小数
					//打分框可输入的最大值
					var max = $(this).attr('data-to')  * 1;
					if(this.value * 1 > max){//超过最大值禁止输入
						this.value = "";
						return true;
					}
					
					if(this.value * 1 == max){
						return true;
					}
					
					//包含小数点自动补全到区间值  
					//注：这里有个问题，如果区间值不是0.5，需要更复杂的处理，但打分最小值一般都0.5分
					if(_reg.test(this.value)){
					    var interval = $(this).attr('data-interval') * 1;
					    this.value = Math.floor(this.value * 1) +  interval;
				    }
					return true;
				}).on('blur','.point-input',function(e){
					//失焦时如果值包含有小数点，将小数点去掉
					if(_reg.test(this.value)){
					    this.value = this.value.substring(0,this.value.indexOf('.'));
				    }
				});
			};
		};
		 
		return new Point
	});
})();
