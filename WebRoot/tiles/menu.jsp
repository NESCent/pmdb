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
           
<img src="/mmdb/images/tag1.jpg" />
<div style="background-color: #fcfcfc; width:120px; height:100%;padding-right:4; margin-right:40px; ">
<table width=100 cellspacing=0 cellpadding=0>
<tr><td>&nbsp;</td></tr>
<tr><td>&nbsp;</td></tr>
<c:choose>
<c:when test='${loggedin == "yes"}'>	
	<tr>
    <td  class="menuItem">  
 	  	<a href="/mmdb/jsp/searchStudy.jsp">Search Studies</a>
 	</td>
  </tr>
	<tr><td>&nbsp;</td></tr>
	<tr><td class="menuItem"><a href="/mmdb/jsp/logout.jsp">Logout</a></td></tr>
</c:when>
<c:otherwise>
	<tr><td class="menuItem"><a href="/mmdb/jsp/login.jsp">Login</a></td></tr>	
</c:otherwise>
</c:choose>
</table>

</div>

   

