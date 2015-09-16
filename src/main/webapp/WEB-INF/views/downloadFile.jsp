<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<c:url value="/resources/styles/styles.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
<title>Upload File Request Page</title>
</head>
<body>
	<div id="topDiv">
		
			<h1>Download file</h1>
	
	</div>
	<div id="navigation">
		<a href="home">Go Home</a> | <a href="unlogin">Logout</a> | <a
			href="login">Login</a> | <a href="myimages">MyImages</a>| <a
			href="register">Register</a>| <a href="upload">Upload image</a>|
	</div>
	<div id="centreDiv">
		<form method="POST" action="downloadFile" enctype="multipart/form-data">
			File seve location <input type="file" name="file" accept="image/*"><br />
			<br /> <br /> <input type="submit" value="Download"> Press
			here to download the file!
		</form>
	</div>
	<div id="statusDiv">
		<p>Status: ${message}.</p>
	</div>
</body>
</html>