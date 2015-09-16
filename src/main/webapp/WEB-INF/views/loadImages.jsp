<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<html>
<head><c:url value="/resources/styles/styles.css" var="mainCss" />
<link href="${mainCss}" rel="stylesheet" />
<title>Lista Obrazków. Kefir Image Scrambler</title>
</head>
<body>
<div></div>	<a href="home">Go Home</a> |
	<a href="unlogin">Logout</a> |
	<a href="login">Login</a> |
	<a href="myimages">MyImages</a>|
	<a href="register">Register</a>|
	<a href="uploadMultiple">Upload multiple image</a> |
	<a href="upload">Upload image</a>|</div>
	<P><a href="loadImages">load images</a></P>
	<div id="imageHoleder">
	       <div align="center">
            <h1>Contact List</h1>
            <h3><a href="/upload">Add Image</a></h3>
            <table border="1">
                <th>No</th>
                <th>Name</th>
                <th>Email</th>
                <th>Address</th>
                <th>Telephone</th>
                <th>Action</th>
                 
                <c:forEach var="file" items="${listContact}" varStatus="status">
                <tr>
                    <td>${status.index + 1}</td>
                    <td>${contact.name}</td>
                    <td>${contact.email}</td>
                    <td>${contact.address}</td>
                    <td>${contact.telephone}</td>
                    <td>
                        <a href="/editContact?id=${contact.id}">edit</a>
                        &nbsp;&nbsp;&nbsp;&nbsp;
                        <a href="/deleteContact?id=${contact.id}">delete</a>
                        <a href="/deleteContact?id=${contact.id}">delete</a>
                    </td>
                             
                </tr>
                </c:forEach>             
            </table>
        </div>
	
	</div>
	
	<P>The time on the server is ${serverTime}.</P>
</body>
</html>
