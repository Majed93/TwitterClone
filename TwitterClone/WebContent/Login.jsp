<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
   
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/login.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Welcome</title>
</head>
<body>
Welcome
<h1>Please login.</h1>


 <!-- Login form -->
 <form action = Login method="POST">
    <div id="loginbox">
    <table id="tlog">
    <tr>
	    <td>
	    	<label>Username: </label>
	    </td>
	    <td>
	    	<input type="text" id="username" name="username" size="20"/>
	    </td>
    </tr>
    <tr>
	    <td>
	    	<label>Password: </label>
	    </td>
	    <td>
	    	<input type="password" id="password" name="password" size="20"/><br />
	    </td>
     
 	</tr>
 
       </table>
       	<input type="submit" value="Login" class=submit />
   
   <%  	
   		if(request.getAttribute("added") != null)
	   	{
	   		%> Successfully Registered <% } 
	   	else
	 	{
		session.invalidate();
		}
	%>
   
   <%
   		if(request.getAttribute("loggedin") != null)
	  	{%>
	  		<p><b><font color="white">Incorrect Username or Password.</font></b></p>
	 	
	    <%   
	    }
	     %>
       </div>
      
</form>
       
       <br/>
       <br/>
       
       <a href="${pageContext.request.contextPath}/Register" style="color:#CCCCCC;">Register Here.</a> 
       <script language="javascript" type="text/javascript">

var mytext = document.getElementById("username");
mytext.focus();

</script>
</body>
</html>