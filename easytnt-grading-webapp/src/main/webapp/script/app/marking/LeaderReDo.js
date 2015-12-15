(function() {
	"use strict";
	define([ 'jquery', 'logger','ajaxwrapper'], function($, logger,ajax) {
		var _imgServer = $('#imgServer').val();
		var $reDoPanel = $('#redo');
		var ReDo = function() {
			this.grading=undefined;
			if($reDoPanel.size() == 0){
				return;
			}
			var self = this;
			$('#navigation ul.navbar-nav li:last a').click(function(){
				$(this).toggleClass('exception-doing');
				if(!$(this).hasClass('exception-doing')){
					this.style.color = "#FFF";
					self.grading.mode='normal';
					return false;
				}
				self.grading.mode='exception';
				this.style.color = "#FF4351";
				logger.log(this.baseURI);
				var url = this.baseURI.substring(this.baseURI.indexOf('grading')+8);
				ajax.getJson(url+"/exception",{},{show:false},function(data){					
					logger.log(data);
					if(data.redo){
						var $redoUl = $reDoPanel.find('ul');
						$redoUl.empty();
						var k = 1;
						$.each(data.redo,function(i,d){
							var text = "";
							var uuid = "";
							$.each(d,function(j,e){
								text += '<span>'+j + "(<em>" +e[0]+"</em>)</span>";
								uuid = e[2];
							});
							var $li = $('<li><a url="'+i+'" href="javascript:void(0);">'+(k++)+"</a>"+(text)+'</li>');
							$li.data('uuid',uuid);
							$redoUl.append($li);
						});
						$reDoPanel.show();
					}
				});
			});
			
			$reDoPanel.on('click','ul li a',function(){
				$reDoPanel.find('li').removeClass('actived');
				var uuid = $(this).parent().addClass('actived').data('uuid');
				logger.log(uuid);
				self.grading.uuid = uuid;
				self.grading.nextPaper();
			});
			
			this.remove = function(){
				var $li =  $reDoPanel.find('li.actived');
				if($li.next().size()){
					$li.next().find('a').click();
				}
				$li.remove();
			};
			
			this.next = function(){
				return $reDoPanel.find('li.actived a').attr('url');
			};
			
		};
		return new ReDo();
	});
})();