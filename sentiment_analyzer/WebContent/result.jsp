<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="org.mmec.proj.sae.main.*" %>
<%@ page import="org.mmec.proj.sae.twitter.*" %>
<%@ page import="org.mmec.proj.sae.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>DisplayTweets</title>

<style>
body {
	
	background-image: url(images/simple2.jpg);
	
	
	}

</style>
<body>
<a href="TweetWebApp.jsp"><h5>Go Back</h5></a>
		<%
		
		String keyword = null;
		if((keyword = request.getParameter("inputText")) == null || keyword.trim().isEmpty()) {
			%>
			<h3>Please Enter Valid Keyword !!</h3>
			<%
		} else {%>
		
			<%SentimentAnalyzerAPI s1 = new SentimentAnalyzerAPI();
			SentimentResponse sr = s1.process(keyword);
			ArrayList<TweetSentiment> tlist =  sr.getTweets(); %>
			<h2><i><u>Summary</u></i></h2>
			<h3><% out.print("#TotalPositive:");%>
			<b><%out.print(sr.getTotalPositive());%></b>
			<br/>
			<% out.print("\n#TotalNegative:");%>
			<b><%out.print(sr.getTotalNegative());%></b>
			<br/>
			<% out.print("\n#TotalNeutral:");%>
			<b><%out.print(sr.getTotalNeutral());%></b></h3>
			
			
			<table cellpadding="5" cellspacing="10" border="1">
			<tr>
				
				<td><b>SENTIMENT</b></td>
				<td><b>TWEET</b></td>
			</tr> 
			<%
			
			int count = 1;
			for(TweetSentiment tweet : tlist) {
				out.print("<tr>");
				out.print("<td>"+tweet.getSentiment()+"</td><td>"+tweet.getTweet()+"</td>");
				out.print("</tr>");
				count++;
			}
			%>
			
			</table>
		<% }%>
</body>
</html>