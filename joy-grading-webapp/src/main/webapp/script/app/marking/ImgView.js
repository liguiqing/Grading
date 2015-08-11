(function () {
	var modle = ["jquery","logger", 'jqueryM'];
	define(modle, function ($,logger) {
		var defOpts = {
			containerId : "img",
			imgSrc : null,
			eagleEyeRatio : 0.25
		};
		var opts = {};

		var $viewContainer = undefined;
		var $image = undefined;

		var self = ImgView = {
			init : function (_opts) {
				$.extend(opts, defOpts, _opts);
				$image = $("<img>", {
						id : "myImage",
						src : opts.imgSrc
					});
				$image.load(function () {
					self.initView();
				});
			},
			initView : function () {
				this.initVariable();
				this.initContainer();
				this.createImage();
				logger.log(opts);
				eagleEyeImage.create();
			},
			initVariable : function () {
				opts.trident = document.all && !window.opera ? 1 : 0;
				opts.notrans = opts.trident && !window.XMLHttpRequest ? 1 : 0;
				opts.webkit = window.atob != undefined && !window.updateCommands ? 1 : 0;
				opts.divbug = 1;//!img.webkit && navigator.userAgent.indexOf('WebKit') > -1 ? 1 : 0; //img有兼容问题
				opts.gecko = navigator.userAgent.indexOf('Gecko') > -1 && window.updateCommands ? 1 : 0;
				opts.presto = window.opera ? 1 : 0;
				opts.bshadow = this.boxShadow();
				opts.mode = (opts.trident && (document.compatMode == 'BackCompat' || document.compatMode == 'QuirksMode') ? true : false);
				opts.modeNum = opts.mode ? 0 : 2;
				opts.oppositeModeNum = opts.mode ? 2 : 0;
				opts.active = true;
			},
			boxShadow : function () {
				var bs = false,
				mbs = false,
				kbs = false,
				wbs = false;
				try {
					bs = (document.body.style.boxShadow !== undefined);
				} catch (e) {}

				try {
					mbs = (document.body.style.MozBoxShadow !== undefined);
				} catch (e) {}

				try {
					kbs = (document.body.style.KhtmlBoxShadow !== undefined);
				} catch (e) {}

				try {
					wbs = (document.body.style.WebkitBoxShadow !== undefined);
				} catch (e) {}

				return (bs || mbs || kbs || wbs ? true : false);
			},
			initContainer : function () {
				$viewContainer = $("#" + opts.containerId);
				this.setContainerCss();
				this.getContainerWidthAndHeight();
			},
			setContainerCss : function () {
				$viewContainer.css({
					position : "relative",
					overflow : "hidden",
					KhtmlUserSelect : "none",
					MozUserSelect : "none",
					backgroundColor:"#cccccc",
					webkitUserSelect : "none",
					border:"1px solid"
				});
				$viewContainer.attr("unselectable", "on");
				$viewContainer.on("selectstart", function () {
					return false;
				}); //设置不能选择内容
				$viewContainer.on("contextmenu", function () {
					return false;
				}); //设置鼠标右键出菜单失效
			},
			getContainerWidthAndHeight : function () {
				opts.viewContainerWidth = parseInt($viewContainer.width());
				opts.viewContainerHeight = parseInt($viewContainer.height());
			},
			createImage : function () {
				$viewContainer.append($image);
				this.getImageWidthAndHeight();
				this.setImgeCss();
				var maxLeft = $image.width() - $viewContainer.width();
				var maxTop = $image.height() - $viewContainer.height();
				$image.data("maxLeft",maxLeft);
				$image.data("maxTop",maxTop);
				$image.on("mousedown",this.startMove);
				$image.on("taphold",this.startMoveM);
			},
			setImgeCss : function () {
				
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				var left =0;
				var top =0;
				if(iW<cW){
					left = parseInt((cW-iW)/2);
				}
				if(iH<cH){
					top = parseInt((cH-iH)/2);
				}
				
				$image.css({
					position : "absolute",
					KhtmlUserSelect : "none",
					MozUserSelect : "none",
					"left":left,
					"top":top,
					cursor : "move",
					border:"none"
				});
				$image.attr("unselectable", "on");
			},
			getImageWidthAndHeight : function () {
				opts.imageWidth = parseInt($image.width());
				opts.imageHeight = parseInt($image.height());
			},
			startMove : function (e) {
				$image.data("mouseX",e.clientX);
				$image.data("mouseY",e.clientY);
				$(document).on("mousemove", self.whileMove);
				$(document).on("mouseup", self.stopMove);
				var offset=getOffset($image);
				logger.log("left:"+offset.left+";top:"+offset.top);
				return false;
			},
			startMoveM : function (e) {
				$image.data("mouseX",e.clientX);
				$image.data("mouseY",e.clientY);
				//self.whileMove();
				
				//$(document).on("mousemove", self.whileMove);
				//$(document).on("mouseup", self.stopMove);
				var offset=getOffset($image);
				logger.log("left:"+offset.left+";top:"+offset.top);
				return false;
			},			
			whileMove : function (e) {
				
				var curMouseX=e.clientX;
				var curMouseY=e.clientY;
				var pfMouseX=$image.data("mouseX");
				var pfMouseY=$image.data("mouseY");
				var offset=getOffset($image);
				
				var left=Math.abs(offset.left)-(curMouseX-pfMouseX);
				var top =Math.abs(offset.top)-(curMouseY-pfMouseY);
				
				var maxLeft=$image.data("maxLeft");
				var maxTop=$image.data("maxTop");
				logger.log("maxLeft:"+maxLeft+";maxTop:"+maxTop+";left:"+left+";top:"+top+";");
				left = Math.max(0, Math.min(maxLeft, left));
				top = Math.max(0, Math.min(maxTop, top));
				
				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				if(iW>cW){
					$image.css("left",left*-1);
				}
				if(iH>cH){
					$image.css("top",top*-1);
				}
				$image.data("mouseX",curMouseX);
				$image.data("mouseY",curMouseY);
				
				logger.log("move");
				return false;
			},
			stopMove : function () {
				logger.log("stop");
				$(document).off("mousemove");
				$(document).off("mouseup");	

				var ratio = opts.eagleEyeRatio; //缩放比例
				var times = getTimes();
				var offset=getOffset($image);
				
				var left=offset.left/times.WTimes*ratio;
				var top=offset.top/times.HTimes*ratio;
				
				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				if(iW>cW){
					$positionFrame.css("left",left*-1);
				}
				if(iH>cH){
					$positionFrame.css("top",top*-1);
				}
				
				return false;
			}
		};

		//鹰眼图
		var $eagleEyeContainer = undefined;
		var $eagleEyeImage = undefined;
		var $positionFrame = undefined;
		var eagleEyeImage = {
			create : function () {
				this.createContainer();
				this.createImage();
				this.createPositionFrame();
				this.zooming();
			},
			createContainer : function () {
				$eagleEyeContainer = $("<div>", {
						id : "eagleEyeContainer",
						css : {
							width : parseInt(opts.viewContainerWidth * opts.eagleEyeRatio) + "px",
							height : parseInt(opts.viewContainerHeight * opts.eagleEyeRatio) + "px",
							border : "none",
							position : "absolute"
						}
					});
				if (opts.bshadow) {
					$eagleEyeContainer.css({
						boxShadow : "0px 0px 8px black",
						WebkitBoxShadow : "0px 0px 8px black",
						MozBoxShadow : "0px 0px 8px black",
						KhtmlBoxShadow : "0px 0px 8px black"
					});
				}
				$viewContainer.append($eagleEyeContainer);
				this.positionEagleEyeContainer();
			},
			positionEagleEyeContainer : function () {
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var eW = parseInt($eagleEyeContainer.width());
				var eH = parseInt($eagleEyeContainer.height());
				var left = cW - eW;
				var top = cH - eH;
				$eagleEyeContainer.css({
					"left" : left - opts.mode,
					"top" : top - opts.mode-2
				});
			},
			createImage : function () {
				$eagleEyeImage = $("<img>", {
						src : opts.imgSrc,
						css : {
							width : parseInt(opts.viewContainerWidth * opts.eagleEyeRatio),
							height : parseInt(opts.viewContainerHeight * opts.eagleEyeRatio),
							border : "none",
							position : "absolute",
							left:0,
							top:0
						}
					});
				$eagleEyeContainer.append($eagleEyeImage);
			},
			createPositionFrame : function () {
				$positionFrame = $("<div>", {
						id : "positionFrame",
						css : {
							width : parseInt(opts.viewContainerWidth * opts.eagleEyeRatio - opts.modeNum) + "px",
							height : parseInt(opts.viewContainerHeight * opts.eagleEyeRatio - opts.modeNum) + "px",
							border : "1px solid red",
							position : "absolute",
							cursor:"pointer"
						}
					});
				$eagleEyeContainer.append($positionFrame);
				$positionFrame.on("mousedown", this.startMove);

			},
			zooming : function () {
				var ratio = opts.eagleEyeRatio; //缩放比例
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				var iWTimes = iW / cW; //图片是显示的倍数-宽
				var iHTimes = iH / cH; //图片是显示的倍数-高
				
				var iparentOffseet = $viewContainer.offset();
				var iOffseet = $image.parent().offset();
				var iLeft = iOffseet.left - iparentOffseet.left; //左移动的位置
				var iTop = iOffseet.top - iparentOffseet.top; //向下移动的位置
				
				var maxWidth=$positionFrame.width();
 				var maxHeight=$positionFrame.height();
 				

				var width = Math.min(maxWidth,(Math.round((ratio * cW) / iWTimes)) - opts.modeNum);
				var height = Math.min(maxHeight,(Math.round((ratio * cH) / iHTimes)) - opts.modeNum);
				var left = Math.round((Math.abs(iLeft) / (iWTimes)) * ratio) - opts.oppositeModeNum;
				var top = Math.round((Math.abs(iHTimes) / (iHTimes)) * ratio) - opts.oppositeModeNum;
				$positionFrame.css({
					"width" : width,
					"height" : height,
					"left" : left,
					"top" : top
				});
				
				var maxLeft = $eagleEyeContainer.width()-$positionFrame.width()-opts.modeNum;
				var maxTop = $eagleEyeContainer.height()-$positionFrame.height()-opts.modeNum;
				$positionFrame.data("maxLeft",maxLeft);
				$positionFrame.data("maxTop",maxTop);
			},
			startMove : function (e) {
				$positionFrame.data("mouseX",e.clientX);
				$positionFrame.data("mouseY",e.clientY);
				$(document).on("mousemove", eagleEyeImage.whileMove);
				$(document).on("mouseup", eagleEyeImage.stopMove);
				
				var offset=getOffset($image);
				logger.log("left:"+offset.left+";top:"+offset.top);
				
				return false;
			},
			whileMove : function (e) {
			
				if($(e.target).attr("id") !== $positionFrame.attr("id")){
					return false;
				}
				
				var curMouseX=e.clientX;
				var curMouseY=e.clientY;
				var pfMouseX=$positionFrame.data("mouseX");
				var pfMouseY=$positionFrame.data("mouseY");
				var offset=getOffset($positionFrame);
				
				var left=curMouseX-pfMouseX+offset.left;
				var top =curMouseY-pfMouseY+offset.top;
				var maxLeft=$positionFrame.data("maxLeft");
				var maxTop=$positionFrame.data("maxTop");
				
				left = Math.max(0, Math.min(maxLeft, left));
				top = Math.max(0, Math.min(maxTop, top));
				
				$positionFrame.css({
					"left" : left-opts.mode,
					"top" : top-opts.mode
				});
				logger.log("maxLeft:"+maxLeft+";maxTop:"+maxTop+";left:"+left+";top:"+top+";");
		
				$positionFrame.data("mouseX",curMouseX);
				$positionFrame.data("mouseY",curMouseY);
				
				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				
				var ratio = opts.eagleEyeRatio; //缩放比例
				var times = getTimes();
				maxLeft = $image.data("maxLeft");
				maxTop = $image.data("maxTop");
				
				if(iW>cW){
					left = Math.max(0, Math.min(maxLeft, Math.abs(left *times.WTimes * (1 / ratio))));
					$image.css("left",-1*left);
				}
				if(iH>cH){
					top = Math.max(0, Math.min(maxTop, Math.abs(top *times.HTimes * (1 / ratio))));
					$image.css("top",-1*top);
				}
				
				return false;
			},
			stopMove : function () {
				$(document).off("mousemove");
				$(document).off("mouseup");
				return false;
			}
		};
		
		function getTimes(){
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				var iWTimes = iW / cW; //图片是显示的倍数-宽
				var iHTimes = iH / cH; //图片是显示的倍数-高
				var result ={};
				result.WTimes=iWTimes;
				result.HTimes = iHTimes;
				return result;
		};

		function getOffset($obj){
			var offset = {};
			var parentOffset=$obj.parent().offset();
			var curOffset=$obj.offset();
			offset.top=parseInt(curOffset.top)-parseInt(parentOffset.top);
			offset.left=parseInt(curOffset.left)-parseInt(parentOffset.left);
			return offset;			
		};
		
		var result = {
			init : function (opts) {
				ImgView.init(opts);
				return ImgView;
			}
		};
		return result;
	});
})();
