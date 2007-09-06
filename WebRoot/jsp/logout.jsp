<%@ taglib uri="/WEB-INF/spring.tld" prefix="spring" %>
<%@ page import = "java.util.*" %> 
<%@ page import = "org.nescent.mmdb.hibernate.HibernateSessionFactory" %> 

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>NESCent AdminDB System - Login</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
     
  <body>
    <%

	for(Enumeration e=request.getSession().getAttributeNames(); e.hasMoreElements();)
	{
		String attrName=(String)e.nextElement();
		Object o=request.getSession().getAttribute(attrName);
		if(o instanceof List)
		{
			List l=(List)o;
			l.clear();
		}
		else if(o instanceof HashMap)
		{
			HashMap m=(HashMap)o;
			m.clear();
		}

		
		request.getSession().setAttribute(attrName,null);
	}
	
	HibernateSessionFactory.getSession().close();
	System.gc();
	response.sendRedirect("login.jsp");
	
	%>
</body>
</html>
