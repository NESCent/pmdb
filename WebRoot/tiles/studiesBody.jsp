<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="org.nescent.mmdb.hibernate.dao.*" %>

<h3>Studies</h3>
<%
MmSpecies species=(MmSpecies)request.getAttribute("species");
if(species==null)
{
	out.write("No species specified.");
}
else
{
	Set mmStudies=species.getMmMatingSystemStudies();
	out.write("<p><b>Species Discriptors ("+mmStudies.size()+")</b></p>");	
	out.write("<ul>");
	for(Iterator it=mmStudies.iterator();it.hasNext();)
	{
		MmMatingSystemStudy mmStudy=(MmMatingSystemStudy)it.next();
		String lat=mmStudy.getLatitude();
		MmReferencePart refPart=mmStudy.getMmReferencePart();
		String part=refPart.getName();
		MmReference ref=refPart.getMmReference();
		String cite=ref.getCitation();
		String full=ref.getFullReference();
		out.write("<li><a href='descriptor.go?id="+mmStudy.getMatingSystemStudyOid()+"'>"+species.getGenus()+" "+species.getSpecies()+", "+lat+" ("+part+" <i>in</i>: "+full+")</a></li>");
	}
	out.write("</ul>");		
	
	Set mmSamples=species.getMmPopulationSamples();
	out.write("<p><b>Population Samples ("+mmSamples.size()+")</b></p>");	
	out.write("<ul>");
	for(Iterator it=mmSamples.iterator();it.hasNext();)
	{
		MmPopulationSample mmSample=(MmPopulationSample)it.next();
		String loc=mmSample.getGeographicLocation();
		String env=mmSample.getEnvironment();
		String sname=mmSample.getName();
		String popu=mmSample.getPopulation();
		String year=mmSample.getYear();
		
		Set mmEnvStudies=mmSample.getMmExperimentStudies();
		out.write("<li>"+popu+", "+loc +", "+env+", "+sname+", "+year+"("+mmEnvStudies.size()+"Environmental Studies)</li>");	
		out.write("<ul>");
		
		for(Iterator it1=mmSamples.iterator();it1.hasNext();)
		{
			MmExperimentStudy mmEnvStudy=(MmExperimentStudy)it.next();
			String studyName=mmEnvStudy.getName();
			MmReferencePart refPart=mmEnvStudy.getMmReferencePart();
			String part=refPart.getName();
			MmReference ref=refPart.getMmReference();
			String cite=ref.getCitation();
			String full=ref.getFullReference();
			out.write("<li><a href='envstudy.go?id=" + mmEnvStudy.getExperimentStudyOid()+"'>"+studyName+" ("+part+" <i>in</i>: "+full+")</a></li>");
		}
		out.write("</ul>");
	}
	out.write("</ul>");
}	

%>


    
    