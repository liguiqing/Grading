(function () {
	var deps = ["jquery", "logger"];
	var browser = getBrowser();
	if (browser.isMobile()) {
		deps = ["jquery", "logger", 'jqueryM']
	}
	define(deps, function ($, logger) {
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
				var mine = this;
				$image.load(function () {
					self.initView();
					if(opts.imgLoaded){
						opts.imgLoaded.call(mine);
					}
				});
			},
			hilightArea:function(offset,content){
				var $area = getHilightArea();
				$area.width(offset.width);
				$area.height(offset.height);
				var position = $image.position();
				var areaPosition = {top:offset.top+position.top,left:offset.left+position.left};
				//$area.offset(areaPosition);
				$area[0].style.position = "relative";
				$area[0].style.top = offset.top+position.top + "px";
				$area[0].style.left = offset.left+position.left + "px";
				$area.text(content);
			},
			next : function (url) {
				//调用下一张图片
				url += "?"+ + new Date;
				opts.imgSrc = url;
				console.log(url);
				$image.attr("src", url);
			},
			autoAdaptationHeight : function () {
				//自动适应高度
				opts.imageHeight = opts.viewContainerHeight;
				$image.css({
					height : opts.imageHeight,
					top : 0
				});
				this.setPositionFrameSizeAndPostion();

			},
			autoAdaptationWidth : function () {
				//自动适应宽度
				opts.imageWidth = opts.viewContainerWidth;
				$image.css({
					width : opts.imageWidth,
					left : 0
				});
				this.setPositionFrameSizeAndPostion();
			},
			autoAdaptationWidthAndHeight : function () {
				//自动适应宽度和高度
				opts.imageWidth = opts.viewContainerWidth;
				opts.imageHeight = opts.viewContainerHeight;
				$image.css({
					width : opts.imageWidth,
					height : opts.imageHeight,
					left : 0,
					top : 0
				});
				this.setPositionFrameSizeAndPostion();

			},
			zoomOut : function () {
				//放大图片
				var width = parseInt($image.width() * 0.01);
				var height = parseInt($image.height() * 0.01);

				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth = opts.imageWidth + width; //图片的宽度
				var iH = opts.imageHeight = opts.imageHeight + height; //图片的高度

				var position = $image.position();
				var left = 0;
				if (iW < cW) {
					left = parseInt((cW - iW) / 2);
				} else {
					left = (Math.abs(position.left) + parseInt(width / 2)) * -1;
				}
				var top = 0;
				if (iH < cH) {
					top = parseInt((cH - iH) / 2);
				} else {
					top = (Math.abs(position.top) + parseInt(height / 2)) * -1;
				}

				$image.css({
					"left" : left,
					"top" : top,
					width : opts.imageWidth,
					height : opts.imageHeight
				});
				this.setImageMaxLeftAndTop();

				this.setPositionFrameSizeAndPostion();

			},
			zoomIn : function () {
				//缩小图片按比例1%
				var width = parseInt($image.width() * 0.01);
				var height = parseInt($image.height() * 0.01);

				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth = opts.imageWidth - width; //图片的宽度
				var iH = opts.imageHeight = opts.imageHeight - height; //图片的高度

				var position = $image.position();
				var left = 0;
				if (iW < cW) {
					left = parseInt((cW - iW) / 2);
				} else if($image.data("maxLeft")>= position.left) {
					left = (Math.abs(position.left) - parseInt(width)) * -1;
				}else{
					left = (Math.abs(position.left) - parseInt(width / 2)) * -1;
				}
				var top = 0;
				if (iH < cH) {
					top = parseInt((cH - iH) / 2);
				} else if($image.data("maxTop")>= position.top){
					top = (Math.abs(position.top) - parseInt(height)) * -1;
				}else{
					top = (Math.abs(position.top) - parseInt(height / 2)) * -1;
				}

				$image.css({
					"left" : left,
					"top" : top,
					width : opts.imageWidth,
					height : opts.imageHeight
				});
				this.setImageMaxLeftAndTop();
				this.setPositionFrameSizeAndPostion();
			},
			openAndClose:function(){
				//关闭鹰眼图（右下角的小图）
				if($eagleEyeContainer.is(":hidden")){
					$eagleEyeContainer.show();
				}else{
					$eagleEyeContainer.hide();
				}
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
				opts.divbug = 1; //!img.webkit && navigator.userAgent.indexOf('WebKit') > -1 ? 1 : 0; //img有兼容问题
				opts.gecko = navigator.userAgent.indexOf('Gecko') > -1 && window.updateCommands ? 1 : 0;
				opts.presto = window.opera ? 1 : 0;
				opts.bshadow = true; //$.support.boxModel;
				opts.mode = (opts.trident && (document.compatMode == 'BackCompat' || document.compatMode == 'QuirksMode') ? true : false);
				opts.modeNum = opts.mode ? 0 : 2;
				opts.oppositeModeNum = opts.mode ? 2 : 0;
				opts.active = true;
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
					//backgroundColor:"#cccccc",
					webkitUserSelect : "none"
					//border:"1px solid"
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
				this.setImageMaxLeftAndTop();
				$image.on("mousedown", this.startMove);
				$image.on("taphold", this.startMoveM);
			},
			setImageMaxLeftAndTop : function () {
				var maxLeft = $image.width() - $viewContainer.width();
				var maxTop = $image.height() - $viewContainer.height();
				$image.data("maxLeft", maxLeft);
				$image.data("maxTop", maxTop);
			},
			setImgeCss : function () {
				$image.css({
					position : "absolute",
					KhtmlUserSelect : "none",
					MozUserSelect : "none",
					cursor : "move",
					border : "none"
				});
				this.setImageLeftAndTop();
				$image.attr("unselectable", "on");
			},
			setImageLeftAndTop : function () {
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				var left = 0;
				var top = 0;
				if (iW < cW) {
					left = parseInt((cW - iW) / 2);
				}
				if (iH < cH) {
					top = parseInt((cH - iH) / 2);
				}

				$image.css({
					"left" : left,
					"top" : top,
					width : opts.imageWidth,
					height : opts.imageHeight
				});
			},
			getImageWidthAndHeight : function () {
				if (!opts.imageWidth) {
					opts.imageWidth = parseInt($image.width());
				}
				if (!opts.imageHeight) {
					opts.imageHeight = parseInt($image.height());
				}
			},
			startMove : function (e) {
				$image.data("mouseX", e.clientX);
				$image.data("mouseY", e.clientY);
				$(document).on("mousemove", self.whileMove);
				$(document).on("mouseup", self.stopMove);

				var position = $image.position();
				logger.log("left:" + position.left + ";top:" + position.top);
				return false;
			},
			startMoveM : function (e) {
				$image.data("mouseX", e.clientX);
				$image.data("mouseY", e.clientY);
				//self.whileMove();

				//$(document).on("mousemove", self.whileMove);
				//$(document).on("mouseup", self.stopMove);
				var position = $image.position();
				logger.log("left:" + position.left + ";top:" + position.top);
				return false;
			},
			whileMove : function (e) {

				var curMouseX = e.clientX;
				var curMouseY = e.clientY;
				var pfMouseX = $image.data("mouseX");
				var pfMouseY = $image.data("mouseY");
				var position = $image.position();

				var left = Math.abs(position.left) - (curMouseX - pfMouseX);
				var top = Math.abs(position.top) - (curMouseY - pfMouseY);

				var maxLeft = $image.data("maxLeft");
				var maxTop = $image.data("maxTop");
				logger.log("maxLeft:" + maxLeft + ";maxTop:" + maxTop + ";left:" + left + ";top:" + top + ";");
				left = Math.max(0, Math.min(maxLeft, left));
				top = Math.max(0, Math.min(maxTop, top));

				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				if (iW > cW) {
					$image.css("left", left * -1);
				}
				if (iH > cH) {
					$image.css("top", top * -1);
				}
				$image.data("mouseX", curMouseX);
				$image.data("mouseY", curMouseY);

				logger.log("move");
				return false;
			},
			stopMove : function () {
				logger.log("stop");
				$(document).off("mousemove");
				$(document).off("mouseup");

				self.setPositionFrame();
				return false;
			},
			setPositionFrame:function(){
				var ratio = opts.eagleEyeRatio; //缩放比例
				var times = getTimes();
				var position = $image.position();

				var left = position.left / times.WTimes * ratio;
				var top = position.top / times.HTimes * ratio;

				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				if (iW > cW) {
					$positionFrame.css("left", left * -1);
				}
				if (iH > cH) {
					$positionFrame.css("top", top * -1);
				}
			},
			setPositionFrameSizeAndPostion:function(){
				
				var ratio = opts.eagleEyeRatio; //缩放比例
				var times = getTimes();
				var position = $image.position();

				var left = position.left / times.WTimes * ratio;
				var top = position.top / times.HTimes * ratio;
				
				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度
				var _left = 0;
				var _top=0;
				if (iW > cW) {
					_left = left*-1;
				}
				if (iH > cH) {
					_top=top*-1;
				}
				var maxWidth = $eagleEyeContainer.width();
				var maxHeight = $eagleEyeContainer.height();
				var width = Math.min(maxWidth, (Math.round((ratio * cW) / times.WTimes)) - opts.modeNum);
				var height = Math.min(maxHeight, (Math.round((ratio * cH) / times.HTimes)) - opts.modeNum);
				$positionFrame.css({
					"width":width,
					"height":height,
					"left":_left,
					"top":_top
				});
				var maxLeft = $eagleEyeContainer.width() - $positionFrame.width() - opts.modeNum;
				var maxTop = $eagleEyeContainer.height() - $positionFrame.height() - opts.modeNum;
				$positionFrame.data("maxLeft", maxLeft);
				$positionFrame.data("maxTop", maxTop);
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
				if ($eagleEyeContainer) {
					return;
				}
				$eagleEyeContainer = $("<div>", {
						id : "eagleEyeContainer",
						css : {
							width : parseInt(opts.viewContainerWidth * opts.eagleEyeRatio) + "px",
							height : parseInt(opts.viewContainerHeight * opts.eagleEyeRatio) + "px",
							border : "none",
							position : "absolute",
							zIndex : 10000
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
					"left" : left - opts.mode-1,
					"top" : top - opts.mode - 12
				});
			},
			createImage : function () {
				if ($eagleEyeImage) {
					$eagleEyeImage.attr("src", opts.imgSrc);
					return;
				}
				$eagleEyeImage = $("<img>", {
						src : opts.imgSrc,
						css : {
							width : parseInt(opts.viewContainerWidth * opts.eagleEyeRatio),
							height : parseInt(opts.viewContainerHeight * opts.eagleEyeRatio),
							border : "none",
							position : "absolute",
							left : 0,
							top : 0
						}
					});
				$eagleEyeContainer.append($eagleEyeImage);
			},
			createPositionFrame : function () {
				if ($positionFrame) {
					return;
				}
				$positionFrame = $("<div>", {
						id : "positionFrame",
						css : {
							width : parseInt(opts.viewContainerWidth * opts.eagleEyeRatio - opts.modeNum) + "px",
							height : parseInt(opts.viewContainerHeight * opts.eagleEyeRatio - opts.modeNum) + "px",
							border : "1px solid red",
							position : "absolute",
							cursor : "pointer"
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

				//var iparentOffseet = $viewContainer.offset();
				//var iOffseet = $image.parent().offset();
				//var iLeft = iOffseet.left - iparentOffseet.left; //左移动的位置
				//var iTop = iOffseet.top - iparentOffseet.top; //向下移动的位置
				var position = $image.position();
				var iLeft = position.left;
				var iTop = position.top;

				var maxWidth = $eagleEyeContainer.width();
				var maxHeight = $eagleEyeContainer.height();

				var width = Math.min(maxWidth, (Math.round((ratio * cW) / iWTimes)) - opts.modeNum);
				var height = Math.min(maxHeight, (Math.round((ratio * cH) / iHTimes)) - opts.modeNum);
				var left = Math.min(0, Math.round((Math.abs(iLeft) / (iWTimes)) * ratio) - opts.oppositeModeNum);
				var top = Math.min(0, Math.round((Math.abs(iHTimes) / (iHTimes)) * ratio) - opts.oppositeModeNum);

				$positionFrame.css({
					"width" : width,
					"height" : height,
					"left" : left,
					"top" : top
				});

				var maxLeft = $eagleEyeContainer.width() - $positionFrame.width() - opts.modeNum;
				var maxTop = $eagleEyeContainer.height() - $positionFrame.height() - opts.modeNum;
				$positionFrame.data("maxLeft", maxLeft);
				$positionFrame.data("maxTop", maxTop);
			},
			startMove : function (e) {
				$positionFrame.data("mouseX", e.clientX);
				$positionFrame.data("mouseY", e.clientY);
				$(document).on("mousemove", eagleEyeImage.whileMove);
				$(document).on("mouseup", eagleEyeImage.stopMove);

				var position = $image.position();
				logger.log("left:" + position.left + ";top:" + position.top);

				return false;
			},
			whileMove : function (e) {

				if ($(e.target).attr("id") !== $positionFrame.attr("id")) {
					return false;
				}

				var curMouseX = e.clientX;
				var curMouseY = e.clientY;
				var pfMouseX = $positionFrame.data("mouseX");
				var pfMouseY = $positionFrame.data("mouseY");
				var position = $positionFrame.position();

				var left = curMouseX - pfMouseX + position.left;
				var top = curMouseY - pfMouseY + position.top;
				var maxLeft = $positionFrame.data("maxLeft");
				var maxTop = $positionFrame.data("maxTop");

				left = Math.max(0, Math.min(maxLeft, left));
				top = Math.max(0, Math.min(maxTop, top));

				$positionFrame.css({
					"left" : left - opts.mode,
					"top" : top - opts.mode
				});
				logger.log("maxLeft:" + maxLeft + ";maxTop:" + maxTop + ";left:" + left + ";top:" + top + ";");

				$positionFrame.data("mouseX", curMouseX);
				$positionFrame.data("mouseY", curMouseY);

				//设置图片移动
				var cW = opts.viewContainerWidth; //显示的宽度
				var cH = opts.viewContainerHeight; //显示的高度
				var iW = opts.imageWidth; //图片的宽度
				var iH = opts.imageHeight; //图片的高度

				var ratio = opts.eagleEyeRatio; //缩放比例
				var times = getTimes();
				maxLeft = $image.data("maxLeft");
				maxTop = $image.data("maxTop");

				if (iW > cW) {
					left = Math.max(0, Math.min(maxLeft, Math.abs(left * times.WTimes * (1 / ratio))));
					$image.css("left", -1 * left);
				}
				if (iH > cH) {
					top = Math.max(0, Math.min(maxTop, Math.abs(top * times.HTimes * (1 / ratio))));
					$image.css("top", -1 * top);
				}

				return false;
			},
			stopMove : function () {
				$(document).off("mousemove");
				$(document).off("mouseup");
				return false;
			}
		};

		function getTimes() {
			var cW = opts.viewContainerWidth; //显示的宽度
			var cH = opts.viewContainerHeight; //显示的高度
			var iW = opts.imageWidth; //图片的宽度
			var iH = opts.imageHeight; //图片的高度
			var iWTimes = iW / cW; //图片是显示的倍数-宽
			var iHTimes = iH / cH; //图片是显示的倍数-高
			var result = {};
			result.WTimes = iWTimes;
			result.HTimes = iHTimes;
			return result;
		};
		
		function getHilightArea(){
			var $area = $viewContainer.find('div.img-hilight-area');
			if($area.size() == 0){
				$area = $('<div>');
				$area.addClass('img-hilight-area');
				//$area.css({"-webkit-animation":"twinkling 1s infinite ease-in-out"});
				//$area.append('<div class="img-hilight-area-content"></div>');
				$viewContainer.append($area);
				return $area;
			}else{
				return $area;
			}
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
