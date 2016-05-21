var hasWorkerValidationError=false;

function workerRegistration() {
	var hasErrors = false;
	var emailId = document.getElementById("email").value;
	if(emailId.indexOf("@") == -1 || emailId.indexOf(".") == -1){
		hasErrors = true;
	}
	if (hasErrors) {
		return false;
	}
	else return true;
}

$(document).ready(function(){
	$("#email").blur(validateEmail);
	$("#SignupWorkerForm").submit(function(){
		if(hasWorkerValidationError){
			return false;
		}
	});
});
function emailValidationSuccess(resp){
	if(!resp){
		$("#email").css("border-color","red");
		$("#submitBtn").attr("disabled", true);
		$("#emailValidationError").html("<p>Email already exists!</p>");
	}
	else {
		$("#email").css("border-color","black")
	}
}
function emailValidationFailure(resp){
	hasWorkerValidationError = true;
	console.log(resp);
}
function validateEmail(evt){
		$.ajax({
			  url: 'EmailValidationServlet',
			  data: {
				'email' :this.value  
			  },
			  success: emailValidationSuccess,
			  failure: emailValidationFailure
			});
}