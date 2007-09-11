<%@ page contentType="text/html" %>
<%@ page import="java.util.*" %>   
    <%-- I18N Formatting with EL --%>
<%@ taglib uri="/WEB-INF/fmt.tld" prefix="fmt" %>
<%@ taglib uri="/WEB-INF/c.tld" prefix="c" %>
    
    <%-- SQL with EL --%>
<%@ taglib uri="/WEB-INF/sql.tld" prefix="sql" %>
    
    <%-- XML with EL --%>
<%@ taglib uri="/WEB-INF/x.tld" prefix="x" %>
    
      <%-- functions with EL --%>
<%@ taglib uri="/WEB-INF/fn.tld" prefix="fn" %>
     <%-- core tags --%>
<%@ page import="org.nescent.mmdb.util.NoCache" %>

<%       
NoCache.nocache(response);
boolean loggedIn=(request.getSession().getAttribute("permission_manager")!=null);
if(loggedIn)
	 request.setAttribute("loggedin","yes");
else
	 request.setAttribute("loggedin","no");	 
 
 String personOID = (String)request.getAttribute("personOID");
 request.setAttribute("personOID",personOID);

	
 String loginuserRole = (String)request.getSession().getAttribute("loginuserRole");
 request.setAttribute("loginuserRole",loginuserRole);
 
 %>
        
<img src="/mmdb/images/menutop.jpg" />
<ul>
<li><a href="/mmdb/jsp/home.jsp">Home</a></li>
<c:choose>
<c:when test='${loggedin == "yes"}'>	
   
    <li>  
 	  	<a href="/mmdb/jsp/searchStudy.jsp">Search Studies</a>
 	</li>
 	
 	
 	<li><a href="/mmdb/jsp/logout.jsp">Logout</a></li>
	
	</c:when>
<c:otherwise>

	<li><a href="/mmdb/jsp/login.jsp">Login</a></li>	
	
</c:otherwise>
</c:choose>
</ul>

<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<br/>
<div class="copyright">&copy;2007 National Evolutionary Synthesis Center.
<br/>All rights reserved.
<br/><br/>
2024 W. Main Street<br/>Durham, NC 27705.</div>

   

