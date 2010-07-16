<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="org.nescent.mmdb.util.Population" %>
<%@page import="org.nescent.mmdb.hibernate.dao.*" %>

<h2>Studies</h2>
<%
MmSpecies species=(MmSpecies)request.getAttribute("species");
List pops=(List)request.getAttribute("populations");
if(species==null)
{
	out.write("No species specified.");
}
else
{
	Set mmStudies=species.getMmMatingSystemStudies();
	/*
	out.write("<p><b>Species Descriptors ("+mmStudies.size()+")</b></p>");	
	out.write("<ul>");
	
	for(Iterator it=mmStudies.iterator();it.hasNext();)
	{
		MmMatingSystemStudy mmStudy=(MmMatingSystemStudy)it.next();
		String lat=mmStudy.getLatitude();
		MmReferencePart refPart=mmStudy.getMmReferencePart();
		
		String part="";
		String cite="";
		String full="";
		if(refPart!=null)
		{
			part=refPart.getName();
			MmReference ref=refPart.getMmReference();
			if(ref!=null)
			{
				cite=ref.getCitation();
				full=ref.getFullReference();
			}
		}
		
		out.write("<li><a href='descriptor.go?id="+mmStudy.getMatingSystemStudyOid()+"'>"+species.getGenus()+" "+species.getSpecies()+", (lat="+lat+") ("+cite+")</a></li>");
	}
	out.write("</ul>");		
	
	*/
	Set mmSamples=species.getMmPopulationSamples();
	out.write("<p><b>Population Samples ("+mmSamples.size()+")</b></p>");	
	out.write("<table>");
	out.write("<tr>");
	out.write("<th>Population</th>");
	out.write("<th>Pollination Studies</th>");
	out.write("<th>Inbreeding Studies</th>");
	out.write("<th>Outcrossing Studies</th>");
	out.write("</tr>");	
	String tdclass="TrOdd";
	for(int i=0;i<pops.size();i++)
	{
		tdclass=(i%2==0)?"TrEven":"TrOdd";
		Population mmSample=(Population)pops.get(i);
		String loc=mmSample.getGeographicLocation();
		String popu=mmSample.getPopulation();
		String year=mmSample.getYear();
		String showString="";
		
		if(popu!=null)
			showString+=popu;
			
		
		if(loc!=null && ! loc.trim().equals(""))
		{
			if(showString!="")			
				showString+=", ";
			showString+=loc;
		}		
		
		if(year!=null && ! year.trim().equals(""))
		{
			if(showString!="")			
				showString+=", ";
			showString+=year;
		}	
				
		
		int outcrossnum=mmSample.getOutcrossingNum();
		int inbreednum=mmSample.getInbreedNum();
		int pollnum=mmSample.getPollinationNum();
		String soutcrossnum=(outcrossnum!=0)?String.valueOf(outcrossnum):"";
		String sinbreednum=(inbreednum!=0)?String.valueOf(inbreednum):"";
		String spollnum=(pollnum!=0)?String.valueOf(pollnum):"";
		out.write("<tr class='"+tdclass+"'><td><a href=population.go?id="+mmSample.getId()+">"+showString+"</a></td>");	
		
		out.write("<td>"+spollnum+"</td>");
		out.write("<td>"+sinbreednum+"</td>");
		out.write("<td>"+soutcrossnum+"</td>");
		out.write("</tr>");
	}
	out.write("</table>");
	
}	

%>


    
    