<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="com.twitter.stores.*" %>
<%@ page import="java.util.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<style type="text/css">


div.Table_01 {
	
	position:absolute;
	left:10px;
	top:0px;
	width:100%;
	height:768px;
}

div.bg-01 {
	background-image:url('${pageContext.request.contextPath}/images/bg_01.png');
	position:absolute;
	left:0px;
	top:0px;
	width:100%;
	height:82px;
	
}

div.bg-02 {
	background-image:url('${pageContext.request.contextPath}/images/bg_02.png');
	background-repeat:repeat-x;
	position:absolute;
	left:0px;
	top:82px;
	width:100%;
	height:686px;
	
}
input {
	background-color:#303030;
	-webkit-border=radius: .3em;
	-moz-border-radius: .3em;
	border-radius: .3em;

	

	-moz-box-shadow: 0 -1px 0 0px #000;
	-webkit-box-shadow: 0 -1px 0 0px #000;
	box-shadow: 0 -1px 0 0px #000;	
	opacity:0.9;
	

	border: none;
	width:316px;
	height:27px;	
	margin:15px 0;
	color:#fff;
	padding:0 0 0 10px;

	font-family:Arial, Helvetica, sans-serif;
	font-size:1.2em;
	letter-spacing:2px;
	
}

input.submit
{
	background-image:url('${pageContext.request.contextPath}/images/btnbg.png');
	width:161px;
	height:50px;
	border:0;
	color:white;
}

input.submit:hover
{
	background-image:url('${pageContext.request.contextPath}/images/btnbghover.png');
	width:161px;
	height:50px;
	border:0;
	color:white;
}


</style>


<!-- End Save for Web Styles -->
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=request.getAttribute("profuser")%></title>
</head>
<body style="font-family:arial;color:white;font-size:20px;">

<div class="Table_01">

	<div class="bg-01">
	<table width="100%" height="82" cellpadding="5" cellspacing="0" left="5px"> <tr> <td valign="bottom">
	 <p><b><font color="#ffffff"><center>Profile of <%=request.getAttribute("profuser")%>
	 </center>	
	 </font></b></p>
	  </td> </tr> </table>
		</div>
		
		<div class="bg-02">

	<table width="100%" height="686" cellpadding="10" cellspacing="1"> <tr> <td valign="top">
	 <p><b><font color="#E8E8E8">
<br/><!-- <%=request.getAttribute("loggedin") %>  -->

 
 
<% if(request.getAttribute("loggedin") != "")
	  {%>
	  
	  <% if(request.getAttribute("followed") != "")
	  {%>
	  
	<form method="POST" action="User">
		<input class=submit type = "submit" value = "UnFollow!"/>
	</form>
	
	  
	  <%}
	  else
	 
	  {%>
	  
	<form method="POST" action="User">
		<input class=submit type = "submit" value = "Follow!"/>
	</form>
	
	  
	  <%}
	  }
		else
		{%>

		<b>You must be logged in</b>
		
		<%} %> 
	
<input class=submit type="button" value="Back" onClick="window.navigate('${pageContext.request.contextPath}/Tweet')">

 <!-- onClick="history.go(-1);return true;">  -->
	 </font></b></p>
	  </td> </tr>  


<br/>
<%
System.out.println("In render");
List<TweetStore> lTweet = (List<TweetStore>)request.getAttribute("UserTweets");
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
  Tweeted by @<%=ts.getUsername()%> at <%=ts.getTime() %></p><hr/>
  
  <%}
}%>
</table>
</div>
</div>
</body>
</html>