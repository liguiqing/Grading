(function() {
	"use strict";
	define([ 'jquery','logger' ], function($,logger) {
		//得分情况
		var _score = {name:"",total:0,points:[],eq:function(){
			var pt = this.pointsTotal();
			return this.total == pt;
		},toJson:function(){
			var json = {};
			var obj = this;
			for(var o in obj){
				if(!$.isFunction(obj[o])){
					json[o.name] = obj[o];
				}
			}
			return json;
		},pointsTotal:function(){
			var pt = 0;
			$.each(this.points,function(i,n){
				pt += this.value;
			});
			return pt;
		}}; 
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
				var $curInput = $('aside.point-panel input:focus');
				if($curInput.size() ==0){
					_$detailPointValues[0].focus();
					return;
				}
				if($curInput.parents('.point-panel-total').size()){
					var score = this.validate();
					this.grading.record(score);
				}else{
					var _this = this;
					_$detailPointValues.each(function(i){
						if(this.name == $curInput[0].name){
							if(i <  _$detailPointValues.size()-1 ){
								_$detailPointValues[i+1].focus();
								return false;
							}else{
								if(_$totalPointValue.attr('disabled')){
									var score = _this.validate();
									_this.grading.record(score);
								}else{
									_$totalPointValue.focus();
									_$totalPointValue.select();									
								}

							}
						}
					});
				}
			};
			
			this.prev = function(){
				var $curInput = $('aside.point-panel input:focus');
				if($curInput.parents('.point-panel-total').size()){
					_$detailPointValues.last().focus().select();;
				}else{
					_$detailPointValues.each(function(i){
						if(this.name == $curInput[0].name){
							if(i != 0 ){
								$(_$detailPointValues[i-1]).focus().select();
								return false;
							}
						}
					});
				}
			};
			
			this.validate = function(){
				var score = {};
				$.extend(true,score,{},_score);
				_$detailPointValues.each(function(i) {
					score.points[i] = {};
					score.points[i]["value"]= this.value * 1;
					score.points[i]["name"]= this.name;
				});
				score.total = _$totalPointValue[0].value == ""?0:_$totalPointValue[0].value *1;
				score.name = _$totalPointValue.attr("name");
				score.title = "第五题";
				return score;
			};
			
			this.givePoint = function($input,valueAttr){
				$input.val($input.attr(valueAttr) || 0);
			};
			
			this.init = function(grading) {
				this.grading = grading;
				var self = this;
				$(_$detailPointValues[0]).focus().select();
				//受父级多种样式的叠加影响，打分框的左边圆角无效
				$('aside.point-panel .point-input').css({'border-top-left-radius':'4px','border-bottom-left-radius':'4px'});
				//点击打分
				$('aside.point-panel').on('click', '.point-btn', function() {
					var $btn = $(this);
					if($btn.attr("disabled")){
						return true;
					}
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
				    }else{
				    	removeFirstZero(this);
				    }
				});
				
				function removeFirstZero(obj){
					if(obj.value.indexOf("0") == 0 && obj.value.indexOf(".") != 1){
						obj.value = obj.value.substring(1,obj.value.length);
						removeFirstZero(obj);
					}
				};
			};
		};
		 
		return new Point
	});
})();
