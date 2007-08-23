<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring" %>


    <%
	String message=(String)request.getSession().getAttribute("Message");
    if(message!=null)
    {
    %>
    
    <h1>Error</h1><br/><br/>
    <span><font color="red"><%=message %></font></span><br/>
    <%
    request.getSession().setAttribute("Message",null);
    }
    %>

