$(document).ready(function(){
	$.ajax({
        type: "GET",
        url: 'EditWorkerSelect',
        success: function(response){
        	
        	$("#first_name").val(response.firstName);
        	$("#last_name").val(response.lastName);
        	$("[name='skill']").each(function(index,ele){
        		if($(ele).val() === response.skill){
        			$(ele).attr("checked","checked");
        		}
        	});
        	$("#location").val(response.location);
        	$("#experience").val(response.experience);
        	$("#rate").val(response.rate);
        	$("#availability").val(response.availability);
        	
        }
   });
});