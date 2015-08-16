(function() {
	"use strict";
	var deps = [ 'jquery' ];
	var PointModle = {};
	define(deps, function($,dialog) {
		var point = function() {
			var _$detailPointValues = $('div.point-panel-detail input.point-input');
			var _$totalPointValue = $('div.point-panel-total input.point-input');
			this.count = function() {
				var total = 0;
				_$detailPointValues.each(function() {
					total += this.value * 1;
				});
				_$totalPointValue.val(total);
			};
			this.reset = function(){
				_$totalPointValue.val(0);
				_$detailPointValues.val(0);
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
			this.init = function(model) {
				var self = this;
				$('div.point-panel .point-input').css({'border-top-left-radius':'4px','border-bottom-left-radius':'4px'});
				$('div.point-panel').on('click', '.point-btn', function() {
					var $btn = $(this);
					var dataToggle = $btn.attr('data-toggle');
					var $input = $('#' + dataToggle);

					if ($btn.attr('data-value')) {
						$input.val($input.attr($btn.attr('data-value')) || 0);
					} else {
						$input.val($input.attr("data-to") || 0);
					}
					if($btn.parents('div.point-panel-total').size() == 0){
						self.count();
					}else{
						if($btn.attr('data-value')=='data-from'){
							self.reset();
						}
					}
				}).on('keyup','.point-input',function(e){
					onlyNumber(this);
					this.value = this.value.replace(/^(\-)*(\d+)\.(\d).*$/,'$1$2.$3'); // 只能输入一个小数
					if(this.value * 1 > $(this).attr('data-to') * 1){
						this.value = "";
						return true;
					}
					var interval = $(this).attr('data-interval') * 1;
					this.value = Math.floor(this.value * 1) +  interval;
				}).on('keydown','.point-input',function(e){

				});
			};
		};
		return new point();
	});
})();
