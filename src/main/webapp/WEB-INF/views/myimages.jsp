<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head>
<c:url value="/resources/styles/styles.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
<title>Lista Obrazków. Kefir Image Scrambler</title>
</head>
<body>
	<div id="topDiv">
		<h1>User's images</h1>
	</div>
	<div id="navigation">
		<a href="home">Go Home</a> | <a href="unlogin">Logout</a> | <a
			href="login">Login</a> | <a href="myimages">MyImages</a>| <a
			href="register">Register</a>| <a href="upload">Upload image</a>|
	</div>
	<div id="centreDiv">
		<table border="1">
			<th>Image</th>
			<th>Index</th>
			<th>Key</th>
			<th>Actions</th>

			<c:set var="imgsrc"
				value="https://s3-us-west-2.amazonaws.com/s3-piotr-rodziewicz-kci/" />
			<c:forEach var="key" items="${keys}" varStatus="counter">
				<tr>

					<c:url value="/delete" var="delurl">


						<c:param name="imgkey">${key}</c:param>

					</c:url>
					<c:url value="/process" var="procurl">

						<c:param name="imgkey">${key}</c:param>
					</c:url>
					<c:url value="/processBySQS" var="procbysqsurl">

						<c:param name="imgkey">${key}</c:param>
					</c:url>
					<c:url value="/download" var="downloadUrl">

						<c:param name="imgkey">${key}</c:param>
					</c:url>
					<td><c:set var="path" value="${imgsrc}${key}" /> <img
						src="${path}" alt="${path}" style="width: 100px; height: 100px;">
					</td>
					<td>${counter.count}</td>
					<td>${key}</td>
					<td><a href="${delurl}">del</a> <br /> 
					<a href="${procurl}"> process</a>
					<br /> <a href="${procbysqsurl}">process by sqs</a>
						<br /> <a href="${downloadUrl}">download</a>
						 <br /></td>


				</tr>
			</c:forEach>
		</table>
	</div>


	<div id="statusDiv">
		<p>
			File to download : <a href="${fileUrl}"
				style="visibility : ${linkVisibility} ">Click for img</a>
		</p>
		<br />
		<p>Status: ${message}.</p>
	</div>

</body>
</html>
