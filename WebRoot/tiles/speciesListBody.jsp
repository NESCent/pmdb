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
	String oldFamily="";
	out.write("<p>"+specieses.size()+ " species found. </p>");
%>
	<table width=80%>
	
<%
	int count=0;

	for(int i=0;i<specieses.size();i++)
	{
		MmSpecies mmSpecies=(MmSpecies)specieses.get(i);
		if(!mmSpecies.getFamily().trim().toUpperCase().equals(oldFamily))
		{
			count=0;	
			out.write("<tr><td colspan=2>"+mmSpecies.getFamily()+"</td></tr>");	
			oldFamily=mmSpecies.getFamily().trim().toUpperCase();
		}
		
		String tr="<tr><td width=15></td><td><a href='species.go?id=" + mmSpecies.getSpeciesOid()+
			"'>"+mmSpecies.getGenus()+" "+mmSpecies.getSpecies()+"</a></td></tr>";
		out.write(tr);	
		
		count++;
	}
	
%>
	</table>
<%
}
%>

