<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="org.mmec.proj.sae.main.SentimentAnalyzerAPI" %>
<html>
<head>
<title>TweetAnalyzer</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
body {
	
	background-image:url(images/twit.jpg);

	}

</style>
</head>
<body ><center>
<h1><i>Project Test Form</i></h1>
<br/>
<img src="images/twitter.png" height="250" width="250"> 	
	<form name="myForm" action="result.jsp" method="post">
	<table valign: middle>
			<tr>
				<td><b>Enter Keyword To Search :</b></td>
				<td><input type="text" name="inputText" /></td>
			</tr>
			<tr>
				<td columnspan=2><button type="submit">Search</button></td>
			</tr>
		</table>
	</form></center>
</body>
</html>
