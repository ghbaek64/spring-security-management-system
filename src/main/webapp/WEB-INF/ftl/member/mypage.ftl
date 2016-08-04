<@security.authentication property="principal" var="principal" />
<@security.authentication property="details" var="details" />

<div class="container">

	<h2><i class="fa fa-inbox" aria-hidden="true"></i> User Profile</h2>

	<div class="panel panel-default">
		<div class="panel-body">
			<div class="page-header">
				<h1>Session Info</h1>
			</div>
			<div><i class="fa fa-user" aria-hidden="true"></i> User Name: ${principal.username?if_exists}</div>
			<div><i class="fa fa-key" aria-hidden="true"></i> Session ID: ${details.sessionId?if_exists}</div>
			<div class="page-header">
				<h1>Member Info</h1>
			</div>
			<div><i class="fa fa-user" aria-hidden="true"></i> User Name: ${member.userId?if_exists}</div>
			<div><i class="fa fa-key" aria-hidden="true"></i> Session ID: ${member.sessionId?if_exists}</div>
			<div><i class="fa fa-key" aria-hidden="true"></i> Last Access Date: ${member.lastAccessDate?if_exists}</div>
			<div><i class="fa fa-key" aria-hidden="true"></i> IP Address: ${member.lastAccessIp?if_exists}</div>
		</div>
	</div>

	<div class="text-center">
		<a class="btn btn-default" href="<@spring.url "/member/visitor" />" role="button">접속현황</a>
		<a class="btn btn-default" href="<@spring.url "/member/logout" />" role="button">로그아웃</a>
	</div>

</div>