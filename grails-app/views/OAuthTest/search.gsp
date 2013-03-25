<%@ page contentType="text/html;charset=UTF-8" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1"/>
		<meta name="layout" content="main"/>
		<title>Insert title here</title>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
		<script type="text/javascript">
			function doSearch(){
				jQuery.ajax({
				type:'POST', 
				url: '/FriendFace/OAuthTest/queryData?',
				success:function(data,textStatus){
					    	var person = eval(data)
					    	$('#outputTable').show();
					    	$('#companyOutputTable').hide();
					    	$('#outputTable tbody').empty();
					    	$('#outputTable tbody').append('<tr><td>' + person.firstName + " " + person.lastName + '</td><td>' + person.headline + '</td></tr>');
						 },
				error:function(XMLHttpRequest,textStatus,errorThrown){
						}
				});
			}

			function doCompanySearch(){
				jQuery.ajax({
					type:'POST', 
					url: '/FriendFace/OAuthTest/queryData?searchString=' + $('#companySearchString').val(),
					success:function(data,textStatus){
						    	var company = eval(data)
						    	$('#companyOutputTable').show();
						    	$('#outputTable').hide();
						    	$('#companyOutputTable tbody').empty();
						    	$('#companyOutputTable tbody').append('<tr><td>' + company.values[0].name + '</td></tr>');
							 },
					error:function(XMLHttpRequest,textStatus,errorThrown){
							}
				});
			}
		</script>
	</head>
	<body>
	  <div class="body">
	  	<input type="button" id="searchNow" name="searchNow" value="Get My Data" onclick="doSearch();"/><br/><br/>
	  	<input type="textbox" id="companySearchString" name="companySearchString"/>
	  	<input type="button" value="Search Companies by Domain Name" onclick="doCompanySearch();"/><br/><br/>
	  </div>
	  <table id="outputTable" name="outputTable" style="display:none;">
	  	<thead>
	  		<tr>
	  			<th>Name</th>
	  			<th>Position</th>
	  		</tr>
	  	</thead>
	  	<tbody>
	  	</tbody>
	  </table>
	  <table id="companyOutputTable" name="companyOutputTable" style="display:none;">
	  	<thead>
	  		<tr>
	  			<th>Name</th>
	  		</tr>
	  	</thead>
	  	<tbody>
	  	</tbody>
	  </table>
	</body>
</html>