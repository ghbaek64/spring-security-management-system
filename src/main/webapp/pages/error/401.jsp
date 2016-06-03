<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<%@ include file="_resources.jsp" %>
</head>

<body>
<div class="container" style="width: 600px;">
	<div class="alert alert-danger" style="margin-top:100px; padding: 50px;">
		<div class="row">
			<div class="col-xs-2 text-center">
				<i class="fa fa-5x fa-ban"></i>
			</div>
			<div class="col-xs-10">
				<h4>[ 오류발생 ]</h4>
				<p>HTTP Error 401 - Unauthorized: Access is denied due to invalid credentials.</p>
			</div>
		</div>
	</div>
</div>
</body>
</html>
