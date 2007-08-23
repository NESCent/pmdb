<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring" %>

<h3>Login</h3>
<%
     boolean loggedIn=(request.getSession().getAttribute("permission_manager")!=null);

 %>

    <%
    
	String message=(String)request.getSession().getAttribute("Message");
	if(message==null || message.trim().equals(""))
		message=(String)request.getAttribute("message");
    if(message!=null)
    {
    %>
    	<br/><font color="red"><%=message %></font><br/>
    <%
    	request.getSession().setAttribute("Message",null);
    	message=null;
    }
    String usertype = "newuser";
	request.setAttribute("usertype",usertype);
	String Applicant = "Applicant";
	request.setAttribute("Applicant",Applicant);
    %>
    
    
    
     
    <form method="post" action="login.go">
    
    <table border=1>

	    <tr>
	    	<td class="TdField">Username</td>
	    	<td class="TdValue">

		    		<input type="text" name="userName" value=""/>

			</td>
		</tr>
		<tr>
			<td class="TdField">Password</td>
			<td class="TdValue">
    			
			    	<input type="password" name="password" value=""/>
		    	
			</td>
		</tr>
				
		<tr>
			<td></td>
			<td>
				<input type="submit" value="login" />
			</td>
		</tr>
		</table>
		
<!-- 
		<h4>Forgot your username and password? <a href="/nead/jsp/forgotpassword.jsp">Please retrieve it.</a></h4> 
		
		<br/>
		
		

			<h4>To report bugs, Please contact <a href="m&#97;i&#108;t&#x6f;:&#110;&#101;a&#x64;&#x40;ne&#x73;ce&#110;&#116;&#46;&#111;r&#103;">&#x6e;ea&#100;&#x40;n&#101;&#115;c&#x65;nt&#x2e;&#111;r&#x67;</a></h4> 
		
		

			<h4>New user? <a href="/nead/add/cv.go?access=add">please register</a></h4> 
 -->
  	 	  

	</form>

