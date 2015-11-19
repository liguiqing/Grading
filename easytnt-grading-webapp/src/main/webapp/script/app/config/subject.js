(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui', 'dialog','intense'],function($,ajaxWrapper,ui,dialog,intense){
		var subjectExam = function(){
			this.testId=undefined;
			this.desc={};
			this.subject = {};
			this.usedPaper=[];
		};
		var examPaper = function(){
			this.name = '';
			this.fullScore = 0;
			this.objectivityScore = 0;
			this.subjectivityScore = 0;
		};
		var subject = function(){
			this.name = '';
			this.subjectCode = '';
		};
		var editorForm = function(editorForm){
			
			this.examPaper = undefined,
			this.isNew =  true,
			this.show = function(examPaper,subject,subjectExam,isNew){
				for(var o in examPaper){
					editorForm.find('#'+o).val(examPaper[o]);
				}
				for(var o in subject){
					editorForm.find('#'+o).val(subject[o]);
				}
				for(var o in subjectExam){
					editorForm.find('#'+o).val(subjectExam[o]);
				}
				editorForm.find(':text:first').focus();
				this.isNew = isNew;
				this.examPaper = examPaper;
				this.subject = subject;
				this.subjectExam = subjectExam;
			};
			
			this.validate = function(){
				var b = true;
				var score = [0,0,0];
				editorForm.find(':text').each(function(i,n){
					if(i == 0){
						if(this.value.length < 1 ){
							b = false;
							dialog.fadedialog(getOpts("科目不能为空"));
							return false;
						}	
					}else{
						score[i-1] = this.value;
					}
				});
				if(b && (score[0]=="" || score[1] =="" || score[2] =="" )){
					dialog.fadedialog(getOpts("满分或客观题满分或主观题满分不能为空"));
					return false;
				}
				var reg = new RegExp("^[0-9]*$");
				if (b && (!reg.test(score[0]) || !reg.test(score[1]) || !reg.test(score[2])) ) { 
					dialog.fadedialog(getOpts("满分或客观题满分或主观题满分必须为正整数"));
					return false;
				} 
				if(score[0]>999 || score[1] >999 || score[2] >999 ){
					dialog.fadedialog(getOpts("满分或客观题满分或主观题满分不超过999"));
					return false;
				}
				if(b && score[0] != (score[1]*1 + score[2]*1)){
					dialog.fadedialog(getOpts("客观题满分和主观题满分之和要等于满分"));
					b = false;
				}
				return b;
			};
			
			this.save = function(){
				if(this.validate()){
					var p = {
						subjectName :editorForm.find('#name').val(),
						fullScore :editorForm.find('#fullScore').val(),
						objectivityScore :editorForm.find('#objectivityScore').val(),
						subjectivityScore :editorForm.find('#subjectivityScore').val()
					};
					ajaxWrapper.postJson("subjectExam",p,
							{beforeMsg:{tipText:".",show:false},
						     successMsg:{tipText:"保存成功",show:true}},
							function(m){
								if(m.status.success){
									setTimeout(function(){location.reload();},1000);
								}
							});
				}

			};
			this.update = function(){
				if(this.validate()){
					ajaxWrapper.putJson("subjectExam",setInfo.setValue(),
						{beforeMsg:{tipText:".",show:false},
						 successMsg:{tipText:"保存成功",show:true}},
						function(m){
							if(m.status.success){
								setTimeout(function(){location.reload();},1000);
							}
						});
				}

			};
			var self = this;
			editorForm.submit(function(){
				if(self.isNew){
					self.save();
				}else{
					self.update();
				}
				return false;
			});
			
		};
		var setInfo = {
				setValue:function(){
					var myForm = $('div.subject-container>.subject-editor>form');
					var e = new examPaper();
					var s = new subject();
					var se = new subjectExam();
					e.name = myForm.find('#name').val();
					s.name = myForm.find('#name').val();
					se.desc.name=myForm.find('#name').val();
					s.subjectCode = myForm.find('#subjectCode').val();
					e.fullScore = myForm.find('#fullScore').val();
					e.objectivityScore = myForm.find('#objectivityScore').val();
					e.subjectivityScore = myForm.find('#subjectivityScore').val();
					se.testId=myForm.find('#testId').val();
					se.subject = s;
					se.usedPaper = [e];
					return se;
				}
			};
		var o = function(){
			var myTable = $('div.subject-container>table');
			var myForm = new editorForm($('div.subject-container>.subject-editor>form'));
			var currentSubject = {
				isNew:false,
				row:undefined,
				show:function(){
					var e = new examPaper();
					var s = new subject();
					var se = new subjectExam();
					if(!this.isNew){
						var sd = this.row.find('td:first a');
						e.name = sd.text();
						s.name = sd.text();
						s.subjectCode = sd.attr('data-rr-value');
						se.testId = sd.attr('data-rr-testId');
						e.fullScore = this.row.find('td:eq(4)').text();
						e.objectivityScore = this.row.find('td:eq(5)').text();
						e.subjectivityScore = this.row.find('td:eq(6)').text();
						se.subject = s;
						se.usedPaper = [e];
					}
					myForm.show(e,s,se,this.isNew);
				}
			};
            
			
			myTable.on('click','.cuttingDefine',function(e){
				var testId = $(this).attr('data-paperid');
				window.open(window.app.rootPath+'cuttingDefine/1/'+testId);
			}).on('click','tbody #newSubject',function(e){
				currentSubject.isNew = true;
				currentSubject.row = $(this).parent().parent();
				currentSubject.show();
			}).on('click','tbody tr td a[data-rr-name=subjectName]',function(e){
				currentSubject.isNew = false;
				currentSubject.row = $(this).parent().parent();
				currentSubject.show();
			}).on('click','tbody tr td.doing i',function(e){
				var sd = $(this).parent().parent().find('td:first a[data-rr-name="subjectName"]');
				var testId = sd.attr('data-rr-testId');
				ajaxWrapper.removeJson("subjectExam",{testId:testId},
						{beforeMsg:{tipText:".",show:true},
						successMsg:{tipText:"删除成功",show:true}},
						function(m){
							if(m.status.success){
								setTimeout(function(){location.reload();},1000);
							}
						});
			}).on('click','tbody tr td a[data-rr-name="addImage"]',function(e){
				var btns = [{text:'取消',callback:function(){
					$(this).trigger('close');
					location.reload();
				}}];
				var message=
					'<form id="uploadForm"  method="POST" action="" enctype="multipar/form-data">'+
			    	   '<div class="input-group file-preview">'+
				    	'<input type="text" class="form-control file-preview-filename" disabled="disabled">'+
				    	'<div class="input-group-btn"> '+
						   '<button type="button" class="btn btn-default file-preview-clear" style="display:none;">'+
						      '<span class="glyphicon glyphicon-remove"></span>清除'+
						    '</button>'+
						    '<div class="btn btn-default file-preview-input" >'+
						      '<span class="glyphicon glyphicon-folder-open"></span>'+
						      '<span class="file-preview-input-title">选择文件</span>'+
						      '<input id="fileName" type="file" name="fileName" accept="image/gif, image/jpeg,image/png,image/tiff" style="display:none;">'+
						    '</div>'+
						    '<button type="type" class="btn btn-default " id="upload">'+
						      '<span class="glyphicon glyphicon-upload"></span>上传'+
						    '</button>'+
					    '</div>'+
				       '</div>'+
				  '</form>';
				var modal = ui.modal("图片上传",message,'md',btns);
				ui.fileUpload(modal);
				$(modal.find('#uploadForm')).submit(function(){
					return false;
				});
				var row = $(this).parent().parent().parent();
				var paperId = row.find('td:eq(4)').attr('data-rr-paperId');
				$(modal.find('#upload')).click(function(){
					if($('div.file-preview-filename').val() == 0){
						return;
					}
					ajaxWrapper.upload('examPaper/'+paperId,
							'fileName',
							{beforeMsg:{tipText:".",show:false},
							 successMsg:{tipText:"上传成功",show:true}},
							function(m){
									if(m.status.success){
										
									}
							});
				});
				$(modal.find('.close')).click(function(){
					location.reload();
				});
			}).on('mouseover','tbody tr td a[data-rr-name="image"]',function(e){
				$(this).find('i').show();
			}).on('mouseout','tbody tr td a[data-rr-name="image"]',function(e){
				$(this).find('i').hide();
			}).on('click','tbody tr td a[data-rr-name="image"] i',function(e){
				var $p = $(this).parent();
				var card = {cardId:$p.attr('data-rr-cardid'),paper:{paperId:$p.attr('data-rr-paperid')}};
				ajaxWrapper.removeJson('examPaper/removePaperCard/'+card.paper.paperId,card,'<b>确定要删除题卡图片吗？</b>',function(){
					$p.remove();
				});
				e.stopPropagation();
			});
		};
		var elements = document.querySelectorAll( '.demo-image' );
		logger.log(elements);
		logger.log(elements.length);
		if(elements!=undefined && elements.length!=0){
			Intense( elements );
		}
		function getOpts(message){
			var DialogSize = {SM:'sm',MD:'md',LG:'lg'};
			var opts = {
					size : DialogSize.SM,
					header : {
						show : true,
						text : "操作提示"
					},
					iconInfo:'error',
					tipText :message
				};
			return opts;
		}
		return {
			render:function(){
				
				return new o();
			}
		};
	});
})();