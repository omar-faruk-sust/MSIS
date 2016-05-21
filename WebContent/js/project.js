$(document).ready(function(){
	$.ajax({
        type: "GET",
        url: 'EditProject',
        data : {
        	"project_id" :$("#project_id").val() 
        },
        success: function(response){
        	
        	$("#project_name").val(response.projectName);
        	$("#project_description").val(response.projectDescription);
        	$("#project_skill").val(response.skill);
        	$("#project_availability").val(response.availability);
        	$("#project_location").val(response.location);
        	$("#project_rate").val(response.rate);
        	
        }
        });
     });