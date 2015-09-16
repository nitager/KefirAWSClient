<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head ><c:url value="/resources/styles/styles.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
	<title >Kefir Image Scrambler</title>
</head>
<body >
<div id="topDiv">
		<h1>Log out!</h1>
	</div>
	<div id="navigation">
		<a href="home">Go Home</a> | <a href="unlogin">Logout</a> | <a
			href="login">Login</a> | <a href="myimages">MyImages</a>| <a
			href="register">Register</a>| <a href="upload">Upload image</a>|
	</div>
	<div id="centreDiv">
		<p>Want to logout? Yes/No?</p>
	</div>
	<div>
<P >  The time on the server is ${serverTime}. </P></div>
</body>
</html>
