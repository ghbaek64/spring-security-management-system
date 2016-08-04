
<div class="container">

	<h2><i class="fa fa-inbox" aria-hidden="true"></i> Visitor List</h2>
	<hr>
	<table class="table">
		<thead>
		<tr>
			<th>Username</th>
			<th>SESSION ID</th>
			<th>Last Request</th>
			<th>Delete</th>
		</tr>
		</thead>
		<thead>
		<c:forEach var="visitor" items="${visitors}">
			<tr>
				<td>${visitor.username}</td>
				<td>${visitor.sessionId}</td>
				<td>${visitor.lastRequest}</td>
				<td><button class="btn btn-default btn-xs" type="button" data-session-id="${visitor.sessionId}"><i class="fa fa-trash" aria-hidden="true"></i></button></td>
			</tr>
		</c:forEach>
		</thead>
	</table>

	<div class="text-center">
		<a class="btn btn-default" href="<@spring.url "/member/mypage" />" role="button">마이페이지</a>
		<a class="btn btn-default" href="<@spring.url "/member/logout" />" role="button">로그아웃</a>
	</div>

</div>

<script type="application/javascript">
	$("[data-session-id]").click(function() {
		var sessionId = $(this).data('session-id');
		console.log(sessionId);

		$.ajax({
			url : '<@spring.url "/member/visitor" />',
			data: '{ "sessionId" : "' + sessionId + '" }',
			type: 'DELETE',
			processData: false,
			contentType: 'application/json'
		}).done(function(responseData) {
			location.reload();
		});

	});
</script>
