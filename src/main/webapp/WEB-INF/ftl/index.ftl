<#assign security=JspTaglibs["http://www.springframework.org/security/tags"] />
<#assign CONTEXT_PATH><@spring.url "" /></#assign>
<#assign RESOURCES_PATH><@spring.url "/resources" /></#assign>
<#assign BOWER_PATH>${RESOURCES_PATH}/bower_components</#assign>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=10">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<title>SSMS</title>

	<link rel="stylesheet" href="${BOWER_PATH}/bootstrap/dist/css/bootstrap.css">
	<link rel="stylesheet" href="${BOWER_PATH}/font-awesome/css/font-awesome.min.css">

	<!--[if lt IE 9]>
	<script type="text/javascript" src="${BOWER_PATH}/html5shiv/dist/html5shiv.min.js"></script>
	<script type="text/javascript" src="${BOWER_PATH}/respond/dest/respond.min.js"></script>
	<script type="text/javascript" src="${BOWER_PATH}/es5-shim/es5-shim.min.js"></script>
	<![endif]-->

	<script type="text/javascript" src="${BOWER_PATH}/jquery/dist/jquery.min.js"></script>
	<script type="text/javascript" src="${BOWER_PATH}/jquery-migrate/jquery-migrate.min.js"></script>

	<link rel="stylesheet" href="${BOWER_PATH}/jquery-ui/themes/redmond/jquery-ui.min.css" />
	<script type="text/javascript" src="${BOWER_PATH}/jquery-ui/jquery-ui.min.js"></script>

	<script type="text/javascript" src="${BOWER_PATH}/underscore/underscore-min.js"></script>
	<script type="text/javascript" src="${BOWER_PATH}/handlebars/handlebars.min.js"></script>

	<script type="text/javascript" src="${BOWER_PATH}/jquery.serializeJSON/jquery.serializejson.min.js"></script>

	<link href="${BOWER_PATH}/select2/dist/css/select2.min.css" rel="stylesheet">
	<script type="text/javascript" src="${BOWER_PATH}/select2/dist/js/select2.min.js"></script>

	<link href="${BOWER_PATH}/toastr/toastr.min.css" rel="stylesheet">
	<script type="text/javascript" src="${BOWER_PATH}/toastr/toastr.min.js"></script>

	<script src="${BOWER_PATH}/bootstrap/dist/js/bootstrap.min.js"></script>
	<link href="${BOWER_PATH}/select2-bootstrap-theme/dist/select2-bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<#attempt>
	<#if template??><#include template></#if>
	<#recover>
	</#attempt>
</body>
</html>