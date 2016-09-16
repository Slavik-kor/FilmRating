(function(){
	
	var app = {
			
			initialize : function () {
				this.setUpListeners();
			},
			
			setUpListeners: function () {
				$('form').on('submit',app.submitForm),
				$('form').on('keydown','input',app.removeError)
			},
			
			submitForm: function (e) {
				e.preventDefault();
												
				if(app.validateForm() === false){return  false};
				
				document.forms["reg"].submit();
				
			},
			
			validateForm: function (form){
				var	valid = true,
				pass = $("#pass"),
				rePass = $("#re-pass"),
				formGroupRePass = rePass.parents('.form-group');


				if(pass.val() != rePass.val()){
					$('#passRepeat').modal('show');
					formGroupRePass.addClass('has-error').removeClass('has-success');
					valid = false;
				}else{
					formGroupRePass.addClass('has-succes').removeClass('has-error');
				}
				
				
				return valid;
			},
			
			removeError: function(){
				$(this).tooltip('destroy').parents('.form-group').removeClass('has-error');
			}
	}
	
	app.initialize();
}()
		);