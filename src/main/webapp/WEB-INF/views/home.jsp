<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<c:url value="/resources/styles/styles.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
<head>
<title>Kefir Image Scrambler</title>
</head>
<body>
	<div id="topDiv">
		<h1>HOME Kefir Image Scrambler</h1>

	</div>
	<div id="navigation">
		<a href="home">Go Home</a> | <a href="unlogin">Logout</a> | <a
			href="login">Login</a> | <a href="myimages">MyImages</a>| <a
			href="register">Register</a>| <a href="upload">Upload image</a>|
	</div>
	<div id="centreDiv"> =====</div>
	<div id="statusDiv">
		<p>Status: ${message}.</p>
	</div>


</body>
</html>
