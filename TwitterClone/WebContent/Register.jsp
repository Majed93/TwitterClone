<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link rel="stylesheet" href="css/reg.css" type="text/css">
<link rel="stylesheet" href="css/main.css" type="text/css">
<script type="text/javascript" src="js/email.js"></script> 
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Register</title>
</head>
<body>
<div class="mainbg-01">
		<img src="images/reglogo.png" width="367" height="111" alt="">
	</div>

<br/>

  <!--Add user -->
        
        <form name="myForm" action = Register method="POST" onsubmit="return validateForm();">
        
         <div class="mainbg-05">
		<img src="images/email.png" width="186" height="53" alt="">
	</div>
   
    <div class="mainbg-08">
   	 <input type="text" id="email" name="email" size="20">
   	 </div>
    
  <div class="mainbg-12">
		<img src="images/username.png" width="187" height="45" alt="">
	</div>
  
  <div class="mainbg-14">
 	 <input type="text" id="user" name="user" size="20">
   </div>
   
   
   <div class="mainbg-17">
		<img src="images/password.png" width="191" height="48" alt="">
	</div>
   
   <div class="mainbg-19">
     <input type="password" id="pass" name="pass" size="20">
     </div>
     
     		<div class="mainbg-22">
		<input type="submit" value="Register" class=submit />
		
			<br/>

<input class=submit type="button" value="Back" 
 onClick="history.go(-1);return true;"> 
		<br/>
	</div>
	
            
            </form>
            
            <div class="mainbg-25">
            <br/>
            <br/>
            <br/>
            <br/>
            
            <b><%=request.getAttribute("success")%></b>
            </div>
	  
	 
        
        <!--End of Add User -->

<script language="javascript" type="text/javascript">

var mytext = document.getElementById("email");
mytext.focus();

</script>
</body>
</html>