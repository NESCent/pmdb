<%@ page contentType="text/html" %>
<%
    String message=(String)request.getAttribute("message");
    if(message!=null && !message.trim().equals("")){
	out.write("<span><font color='blue'>" + message +"</font></span>");
	request.setAttribute("message",null);
    }else{
	message=(String)request.getSession().getAttribute("message");
	if(message!=null && !message.trim().equals("")){
	    out.write("<span><font color='blue'>" + message +"</font></span>");
	    request.getSession().setAttribute("message",null);
	}
    }
%>

