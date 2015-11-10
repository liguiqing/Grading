(function(){
	"use strict";
	define(['jquery','ajax'],function($,ajax){
		var subject = function(){
			this.name = '';
			this.oid = -1;
			this.fullScore = 0;
			this.objectivityScore = 0;
			this.subjectivityScore = 0;
		};
		
		var editorForm = function(editorForm){
			
			this.subject = undefined,
			this.show = function(subject){
				for(var o in subject){
					editorForm.find('#'+o).val(subject[o]);
				}
				editorForm.find(':text:first').focus();
				this.subject = subject;
			};
			
			this.validate = function(){
				var b = true;
				var score = [0,0,0];
				editorForm.find(':text').each(function(i,n){
					if(i == 0){
						if(this.value.length < 1 ){
							b = false;
							return false;
						}	
					}else{
						score[i-1] = this.value;
					}
				});
				if(b && score[0] != (score[1]*1 + score[2]*1)){
					b = false;
				}
				return b;
			};
			
			this.save = function(){
				if(this.validate()){
					//TODO save
				}

			};
			var self = this;
			editorForm.submit(function(){
				self.save();
				return false;
			});
		};
		
		var o = function(){
			var myTable = $('div.subject-container>table');
			var myForm = new editorForm($('div.subject-container>.subject-editor>form'));
			
			var currentSubject = {
				isNew:false,
				row:undefined,
				show:function(){
					var s = new subject();
					if(!this.isNew){
						var sd = this.row.find('td:first a[data-rr-name="subjectName"]');
						s.name = sd.text();
						s.oid = sd.attr('data-rr-value');
						s.fullScore = this.row.find('td:eq(3)').text();
						s.objectivityScore = this.row.find('td:eq(4)').text();
						s.subjectivityScore = this.row.find('td:eq(5)').text();
					}
					myForm.show(s);
				}
			};

			myTable.on('click','tbody #newSubject',function(e){
				currentSubject.isNew = true;
				currentSubject.row = $(this).parent().parent();
				currentSubject.show();
			}).on('click','tbody a[data-rr-name="subjectName"]',function(e){
				currentSubject.isNew = false;
				currentSubject.row = $(this).parent().parent();
				currentSubject.show();
			});
		};

		return {
			render:function(){
				
				return new o();
			}
		};
	});
})();