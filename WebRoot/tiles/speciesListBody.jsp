<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>
<%@ page import="org.nescent.mmdb.hibernate.dao.*" %>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<h3>Species List</h3>
<%

NoCache.nocache(response);
List specieses=(List)request.getAttribute("list");
if(specieses==null)
	out.write("No species list species.");
else
{
	out.write("<p>"+specieses.size()+ " species found. </p>");
%>
	<table width=80%>
	
<%
	String trclass="TrOdd";
	for(int i=0;i<specieses.size();i++)
	{
		MmSpecies mmSpecies=(MmSpecies)specieses.get(i);
		
		if(i%2==0)
			trclass="TrEven";
		else
			trclass="TrOdd";
		String tr="<tr class='"+trclass+"'><td><a href='species.go?id=" + mmSpecies.getSpeciesOid()+
			"'>"+mmSpecies.getGenus()+" "+mmSpecies.getSpecies()+" ("+mmSpecies.getFamily()+")</a></td></tr>";
		out.write(tr);	
	}
	
%>
	</table>
<%
}
%>

