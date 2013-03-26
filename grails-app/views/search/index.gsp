<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<title>Search Friend Face</title>
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.4.3/jquery.min.js"></script>
		<g:javascript src="jquery.tablesorter.min.js" />
		<script type="text/javascript" passthrough="true">
			function doSearch(searchStyle){

				$('#errorDiv').hide('fast');
				$('#resultsTable tbody').empty();
				addWaitingNotice();
				
				var query = $('#name').val();

				var url = '/FriendFace/search/' + searchStyle + '?username=' + query;
				executeSearch(url);
				
			}

			function executeSearch(url){
				jQuery.ajax({
					type:'POST', 
					url: url,
					success:function(data,textStatus){
								renderResults(data);
							 },
					error:function(XMLHttpRequest,textStatus,errorThrown){
						  		$('#resultsOutput').empty().html(XMLHttpRequest.reponseText);
							}
					});
			}

			function renderResults(data){
				var root;
				try {
				  root = eval(data);
				} catch(err) {
				  $('#errorDiv').show('fast');
				  $('#resultsTable tbody').empty();
				  return;
				}

				$('#resultsTable tbody').empty();
				
				var users = root.users;	// evaluate JSON

				for(var i=0; i < users.length; i++){
					var user = users[i];
					appendSearchResultToTable(user);
				}
			}

			function addWaitingNotice(){
				$('#resultsTable tbody').append('<tr><td>Loading, Please Wait...</td><td></td><td></td></tr>"');
			}

			function appendSearchResultToTable(user){
				$('#resultsTable tbody').append('<tr><td>' + user.name + '</td><td>' + user.distanceAway + ' Miles</td><td><img src="' + user.profile_img_url + '"/></td></tr>"');
			}

		</script>
	</head>
	<body>
		<br/>
		<b style="padding-left:5px;">Input Username</b>
		<input type="textbox" id="name" name="name" style="margin-left:10px;margin-bottom:10px;"/>
		<input type="button" id="goName" name="goName" value="Find People Near Me!" onClick="doSearch('getNearbyPeople');"/>
		<input type="button" id="searchSandwich" name="searchSandwich" value="Find People on the Opposite Side of the Earth!" onClick="doSearch('getSandwichCandidates');"/><br/>
		<div id="errorDiv" name="errorDiv" style="margin-left:20px;color:red;font-weight:bold;display:none;">User Not Found</div><br/>
		<table id="resultsTable" name="resultsTable">
			<thead>
				<tr>
					<th>Name</th>
					<th>Distance Away</th>
					<th>Image</th>
				</tr>
			</thead>
			<tbody>
			</tbody>
		</table>
	</body>
</html>
