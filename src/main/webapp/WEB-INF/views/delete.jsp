<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><c:url value="/resources/styles/styles.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<div id="topDiv">
		<h1>Deletetion</h1>
	</div>
	<div id="navigation">
		<a href="home">Go Home</a> | <a href="unlogin">Logout</a> | <a
			href="login">Login</a> | <a href="myimages">MyImages</a>| <a
			href="register">Register</a>| <a href="upload">Upload image</a>|
	</div>
	<div id="centreDiv"></div>
	<div id="statusDiv">
		<p>Status: ${message}.</p>
	</div>

</body>
</html>