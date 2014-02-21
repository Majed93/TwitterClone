<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.twitter.stores.*" %>
    <%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/tweet.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Viewing all tweets</title>
</head>
<body>

Welcome ${sessionScope['username']} <br/>

<br/>

<input class=submit type="button" value="Back" 
 onClick="history.go(-1);return true;"> 
<br/>  
  

<%
System.out.println("In render");
List<TweetStore> lTweet = (List<TweetStore>)request.getAttribute("AllTweets");
if (lTweet==null){
 %>
<p>No Tweet found</p>
<%
}else{
%>

<%
Iterator<TweetStore> iterator;


iterator = lTweet.iterator();
while (iterator.hasNext()){
TweetStore ts = (TweetStore)iterator.next();

%>

<p>
<%=ts.getTweet() %>
<% if(request.getAttribute("loggedin") == null)
	  {%>
		Tweeted by <a href="${pageContext.request.contextPath}/User/<%=ts.getUsername()%>" style="color:#CCCCCC;">@<%=ts.getUsername()%></a> at <%=ts.getTime() %></p><hr/><%
	  }
		else
		{ %>
			Tweeted by @<%=ts.getUsername()%> at <%=ts.getTime() %></p><hr/><%
		  }
	}
}
%>
<br/>
</body>
</html>