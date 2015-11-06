(function() {
	"use strict";
	define([ 'jquery', 'logger' ], function($, logger) {
		var _panelUrl = 'points/';
		var _panelSelector = 'div.point-panel-marking';

		var PointGroup = {
				points : [],
				point:undefined,
				init : function(panel){
					var self = this;
					this.points = [];
					panel.find('.input-group').each(function(i){
						var $apoint = $(this);
						var $poinInput = $apoint.find(':text');
						var point = {
								input : $poinInput,
								_input : $poinInput[0],
								grid : $apoint.find('div.input-group-btn'),
								name:$poinInput.attr('name'),
								dataFrom : $poinInput.attr('data-pointfrom') * 1,
								dataTo : $poinInput.attr('data-pointto') * 1,
								validValues : [],
								focus:false,
								position:{
									top:$poinInput.attr('data-positiontop')  * 1 ,
									left:$poinInput.attr('data-positionleft')  * 1 ,
									width:$poinInput.attr('data-positionwidth')  * 1 ,
									height:$poinInput.attr('data-positionheight')  * 1
								},
								validate : function(){
									if(this.validValues.length == 0){
										var validValuesStr = $poinInput.attr('data-validScores').split(",");
										for(var j=0;j<validValuesStr.length;j++){
											if($.isNumeric(validValuesStr[j])){
												this.validValues[this.validValues.length] = validValuesStr[j] *1;
											}
										}																				
									}
									//onlyNumber(this._input);
									// 打分框只能输入一个小数点
									this._input.value = this._input.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3'); 
									//是否在有效值范围内
									if(!this.validValues.contains(this._input.value * 1)){
										this._input.value = this._input.value.substr(0,this._input.value.length-1);
									}
									
								},
								clean:function(){
									this._input.value = "";
									this._input.focus();
								},
								removeFirstZero :function(){
									this._input.value = this._input.value * 1;								
								},
								actived:function(){
									this._input.focus();									
									for(var k=0;k<self.points;k++){
										self.points[k].focus = false;
									}
									this.focus = true;
									self.point = this;		
									this.input.select();		
								},
								filled:function(){
									this.validate();
									return this._input.value != '';
								},
								reset:function(){
									this._input.value = "";
								},
								valueOf:function(){
									return this._input.value  * 1;
								},
								autoFill:function(score){
									if(score < 0)
										this._input.value = 0;
									else
										this._input.value = this.dataTo;
								}
						};
						$apoint.on('hidden.bs.dropdown', function () {
							point._input.value = point._input.value *1;
							point.validate();
						});
						if(i == 0){
							self.point = point;
						}else if(i == 1){
								self.point['next'] = point;
								point['prev'] = self.point;							
						}else{
							self.points[i-1]['next'] =point;
							point['prev'] = self.points[i-1] ;
						}	
						self.points[i] = point;
						$poinInput.on('focus',function(){
							point.actived();
						});
					});
		            return this;
				},
				addGridListenner:function(){
					var self = this;
					$.each(this.points,function(i,point){
						point.grid.on('click', '.nine-grid', function(e) {
							var $btn = $(this);

							//点击了清除
							if($btn.hasClass('btn-success')){
								point.clean();
								e.stopPropagation();
								return false;
							}
							
							//点击了数字
							if(!$btn.hasClass('button-action')){
								point.input.val(point.input.val()+$btn.text());
								point.validate();
								e.stopPropagation();
							}else{//点击了确定
								point.removeFirstZero();
							}
							
						});
					});
					return this;
				},
				addFocusListener:function(event){
					var self = this;
					$.each(this.points,function(i,point){
						point.input.on('focus',function(){
							event.call(point);
						});
					});
					return this;
				},
				actived:function(){
					this.point.actived();
				},
				reset:function(){
					$.each(this.points,function(){
						this.reset();
					});
					this.point = this.points[0];
					this.actived();
				},
				lastFocus:function(){
					this.points[this.points.length-1].actived();
					this.points[this.points.length-1]._input.focus();
				}
		};
		
		var PointPanel = function() {
			var panel = undefined;
			var inputFocusListener  = undefined;
			var self = undefined;
			this.newInstance = function() {
				panel = $(_panelSelector);
				//必须把form的submit事件处理掉
				panel.find('form').submit(function(){
					return false;
				});
				PointGroup.init(panel).addGridListenner();			
				return this;
			};
			
			
			this.addFocusListener = function(event){
				PointGroup.addFocusListener(event);
				return this;
			};
			this.actived = function(){
				PointGroup.actived();
			};
			this.next = function(checked){
				if(checked && !PointGroup.point.filled()){
					return;
				}
				PointGroup.point.next  && PointGroup.point.next.actived();
			};
			
			this.prev = function(){
				PointGroup.point.prev  && PointGroup.point.prev.actived();
			};
			
			this.hasNext = function(){
				return PointGroup.point.next != undefined;
			};
			
			this.checked = function(){
				PointGroup.point.validate();
			};
			this.reset = function() {
				PointGroup.reset();
			};
			
			this.fullScore = function(){
				$.each(PointGroup.points,function(){
					this.autoFill(1);
				});
				PointGroup.lastFocus();
			};
			
			this.zeroScore = function(){
				$.each(PointGroup.points,function(){
					this.autoFill(-1);
				});
				PointGroup.lastFocus();
			};
			
			this.excellent = function(){
				
			};
			
			this.sample = function(){
				
			};
		
			this.blanked = function(){
				
			};
		
			this.error = function(){
				
			};
		
			this.total = function(){
				var total = {value:0,points:[],onlyValues:function(){
					var values= [];
					$.each(this.points,function(i,p){
						values[i] = p.value;
					});
					return values;
				}};
				$.each(PointGroup.points,function(i){
					total.value += this.valueOf();
					total.points[i] = {name:this.name,value:this.valueOf()};
				});
				return total;
			};
		};

		return new PointPanel();
	});
})();