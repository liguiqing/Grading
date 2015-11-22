(function(){
	"use strict";
	define(['jquery','ajaxwrapper','ui', 'dialog'],function($,ajaxWrapper,ui,dialog){
		
		var editorForm = function($form){
			this.isNew =  true;
			this.teacher = undefined;
			this.show = function(teacher){
				this.isNew  = teacher.isNew;
				this.teacher = teacher;
				if(!teacher.isNew){
					$('#teacherName').val(teacher.o.name).focus();
					$('#subject').selectpicker('val',teacher.o.subject.code);
					$('#teacherAccount').val(teacher.o.teacherAccount);
					$('#amount').val('1').attr('disabled','disabled');
					$('#isLeader').iCheck('disable');
					if(teacher.o.isLeader()){
						$('#isLeader').iCheck('check');
					}else{
						$('#isLeader').iCheck('uncheck');
					}
				}else{
					$('#teacherName').val('').focus();
					$('#teacherAccount').val('');
					$('#amount').val('1').removeAttr('disabled');
					$('#isLeader').iCheck('enable');

				}
			};
			
			//验证表单
			this.validate = function(){
				var b = true;
				//TODO
				return b;
			};
			var self = this;
			//单独抽出来的取值拼接json格式
			function createTeacher(){
				var leader = 0;
				if($('#isLeader')[0].checked)
					leader = 1;
				var teacher = {teacherName:$('#teacherName').val(),leader:leader,subject:{id:$('#subject :selected').attr('data-rr-value'),subjectCode:$('#subject').val()}};
				if(!self.isNew){
					teacher['teacherId'] = self.teacher.o.teacherId;
				}
				return teacher;
			};
			
			
			//保存响应
			this.save = function(){
				if(this.validate()){
					var amount = $("#amount").val();					
					if(amount * 1 < 1){
						amount = 1;
					}
					var leader = 0;
					if($('#isLeader')[0].checked){
						leader = 1;
					}else{
						leader = 0;
					}
					
					var teacher = createTeacher();
					ajaxWrapper.postJson("/teacher/"+amount ,teacher,
						{beforeMsg:{tipText:"",show:false},successMsg:{tipText:"创建成功",show:true}},
						function(data){
							if(data.status.success){
								setTimeout(function(){
									location.reload();
								},1000);
							}
						}
					);
				}
			};
			
			//修改响应
			this.update = function(){
				if(this.validate()){
					var teacher = createTeacher();
					ajaxWrapper.putJson("/teacher",teacher,{beforeMsg:{tipText:"",show:false},successMsg:{tipText:"更新成功",show:true}},function(data){
						if(data.status.success){
							self.teacher.row.find('td:eq(1)').text(teacher.teacherName);
						}
					});
				}
			};
			
			
			$form.submit(function(){
				if(self.isNew){
					self.save();
				}else{
					self.update();
				}
				return false;
			});
		};
		
		var o = function(){
			var myTable = $('div.subject-container>table');
			var  $form = $('div.subject-container>.subject-editor>form');
			var $outer = $('div.subject-container');
			var myForm = new editorForm($form);
			ui.pretty($form);
			ui.pretty(myTable.prev());
			
			var self = this;
			var currentTeacher = {
				isNew:false,
				row:undefined,
				o:undefined,
				show:function(){
					if(!this.isNew){
						this.o = {
							name:this.row.find('td:eq(1)').text(),
							leader:this.row.find('td:eq(0) a').attr('data-rr-leader'),
							teacherId:this.row.find('td:eq(0) a').attr('data-rr-tid'),
							teacherAccount:this.row.find('td:eq(2)').text(),
							subject:{
								id:this.row.find('td:eq(0) a').attr('data-rr-sid'),
								name : this.row.find('td:eq(0)').text(),
								code:this.row.find('td:eq(0) a').attr('data-rr-scode')
							},
							isLeader:function(){
								return this.leader > 0;
							}
						};
					}else{
										
					}

					myForm.show(this);
				}
			};
			

			ui.pretty($outer);
			
			var redrawPage = false;
			
			this.query = function(pager){

				if(!pager){
	        	    pager = _pager.concreator.init();
	        	}
				
				var messageObj = {beforeMsg:{tipText:"",show:true},successMsg:{show:false}};
				var subjectId = $("#subject-query").val();
				var url = "teacher/query/"+pager.pageNum+"/"+pager.pageSize;
				ajaxWrapper.getHtml(url,{subjectid:subjectId},messageObj,function(html){
					  var rowSize = myTable.find('tbody tr').size();
					  myTable.find('tbody tr:lt('+(rowSize-1)+')').remove();
					  var $html = $(html);
					  $html.find('tr').insertBefore(myTable.find('tbody tr:last'));
					  
					  if(redrawPage){
						  pager.concreator.redraw($html);
						  redrawPage = false;
					  }
				});
			};
			//加入页码块
			var _pager = new ui.pager().create('pager',self.query);
			
			//select 的下拉change响应事件
			$("#subject-query").change(function(){
				redrawPage = true;
				self.query();
			});
			
			myTable.on('click','#newTeacher',function(e){
				currentTeacher.isNew = true;
				currentTeacher.show();
			}).on('click','tbody tr td a[data-rr-name=teacher]',function(e){
				currentTeacher.row = $(this).parent().parent();
				currentTeacher.isNew = false;
				currentTeacher.show();
			}).on('click','tbody tr td a[data-rr-name="resetPass"]',function(e){
				var sd = $(this).parent().parent().find('td:first a[data-rr-name="teacher"]');
				var account =sd.attr("data-rr-account");
				var tId = sd.attr("data-rr-tid");
				var btns = [{text:'确定',clazz :'btn-primary',callback:function(){
					var teacher = {teacherId:tId,teacherAccount:account};	
					ajaxWrapper.putJson("teacher/password/reset/"+tId,teacher,{beforeMsg:{tipText:"",show:false},successMsg:{tipText:""+account+"账号密码重置成功",show:true}},function(data){
						
					});
					$(this).trigger('close');				
				}},{text:'放弃',callback:function(){
					$(this).trigger('close');
				}}];
				var message1="是否确定重置账号"+account+"的密码";
				var modal = ui.modal("重置密码",message1,'sm',btns);
			});
			$form.show();
		};
		
		return {
			render:function(){
				return new o();
			}
		};
	});
})();