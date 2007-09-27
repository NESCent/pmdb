<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>
<%@ page import="org.nescent.mmdb.hibernate.dao.*" %>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<%@ page import="org.nescent.mmdb.util.Species" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.nescent.mmdb.hibernate.HibernateSessionFactory" %>
<h2>Species List</h2>
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
	<table>
	<tr>
	<th>Name</th>
	<th>Populations</th>
	<th>Pollination Studies</th>
	<th>Inbreeding Studies</th>
	<th>Outcrossing Studies</th>
	</tr>	
<%
	int count=0;
	String trclass="TrOdd";
	
	for(int i=0;i<specieses.size();i++)
	{
	
		
		Species mmSpecies=(Species)specieses.get(i);
		String family=mmSpecies.getFamily();
		if(family==null)
			family="Unknown";
		if(!family.trim().toUpperCase().equals(oldFamily))
		{
			
			count=0;	
			out.write("<tr><td colspan=5>"+family+"</td></tr>");	
			oldFamily=family.trim().toUpperCase();
		}
		trclass=(count%2==0)?"TrEven":"TrOdd";
		count++;
		
		int popnum=mmSpecies.getPopulationNum();
		int outcrossnum=mmSpecies.getOutcrossingNum();
		int inbreednum=mmSpecies.getInbreedNum();
		int pollnum=mmSpecies.getPollinationNum();
		
	
	
		String spopnum=(popnum!=0)?String.valueOf(popnum):"";
		String soutcrossnum=(outcrossnum!=0)?String.valueOf(outcrossnum):"";
		String sinbreednum=(inbreednum!=0)?String.valueOf(inbreednum):"";
		String spollnum=(pollnum!=0)?String.valueOf(pollnum):"";
		String tr="<tr class="+trclass+"><td>&nbsp;&nbsp;&nbsp;<a href='species.go?id=" + mmSpecies.getId()+
			"'>"+mmSpecies.getGenus()+" "+mmSpecies.getSpecies()+"</a></td>"+
			"<td>"+spopnum+"</td>"+
			"<td>"+spollnum+"</td>"+
			"<td>"+sinbreednum+"</td>"+
			"<td>"+soutcrossnum+"</td>"+
			"</tr>";
		out.write(tr);	
		
		
		
		
	}

%>
	</table>
<%
}
%>

