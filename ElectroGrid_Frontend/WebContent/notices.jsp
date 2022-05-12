<%@page import="com.Notice"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Notices Management</title>
	<link rel="stylesheet" href="Views/bootstrap.min.css">
	<script src="Components/jquery-3.6.0.min.js"></script>
	<script src="Components/notices.js"></script>
</head>
<body> 
	<div class="container"><div class="row"><div class="col-6"> 
	<h1 align="center"><b>Notice Management</b></h1>
	<form id="formNotice" name="formNotice" method="post" action="notices.jsp">
 		Admin ID: 
 		<input id="userId" name="userId" type="text" 
 		class="form-control form-control-sm">
 		<br> Notice Subject: 
 		<input id="noticeSubject" name="noticeSubject" type="text" 
 		class="form-control form-control-sm">
 		<br> Notice Body: 
 		<input id="noticeBody" name="noticeBody" type="text" 
 		class="form-control form-control-sm">
 
 		<br>
 		<input id="btnSave" name="btnSave" type="button" value="Save" 
		 class="btn btn-primary">
 		<input type="hidden" id="hidNoticeIDSave" 
 		name="hidNoticeIDSave" value="">
	</form>
	<br>
	<div id="alertSuccess" class="alert alert-success"></div>
	<div id="alertError" class="alert alert-danger"></div>
	<br>
	<div id="divNoticesGrid">
		 <%
		 Notice noticeObj = new Notice(); 
		 out.print(noticeObj.readNotices()); 
		 %>
	</div>
	</div> </div> </div> 
</body>
</html>