
<% 
	String successStr = (String) request.getAttribute("successMsg");
	String errorStr = (String) request.getAttribute("errorMsg");
	if( successStr != null && !successStr.isEmpty()) 
									
	{ %>
<div class="alert alert-success alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<%= successStr %>
</div>
<% } else if( errorStr != null && !errorStr.isEmpty()) { %>
<div class="alert alert-error alert-dismissible" role="alert">
	<button type="button" class="close" data-dismiss="alert"
		aria-label="Close">
		<span aria-hidden="true">&times;</span>
	</button>
	<%= errorStr %>
</div>
<% } %>