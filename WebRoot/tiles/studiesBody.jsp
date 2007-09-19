<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="java.util.*" %>
<%@page import="org.nescent.mmdb.hibernate.dao.*" %>

<h2>Studies</h2>
<%
MmSpecies species=(MmSpecies)request.getAttribute("species");
if(species==null)
{
	out.write("No species specified.");
}
else
{
	Set mmStudies=species.getMmMatingSystemStudies();
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
		
		out.write("<li><a href='descriptor.go?id="+mmStudy.getMatingSystemStudyOid()+"'>"+species.getGenus()+" "+species.getSpecies()+", "+lat+" ("+cite+")</a></li>");
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
		String showString="";
		
		if(popu!=null)
			showString+=popu;
			
		if(sname!=null && ! sname.trim().equals(""))
		{
			if(showString!="")			
				showString+=", ";
				
			showString+=sname;
		}
		if(loc!=null && ! loc.trim().equals(""))
		{
			if(showString!="")			
				showString+=", ";
			showString+=loc;
		}		
		if(env!=null && ! env.trim().equals(""))
		{
			if(showString!="")			
				showString+=", ";
			showString+=env;
		}		
		if(year!=null && ! year.trim().equals(""))
		{
			if(showString!="")			
				showString+=", ";
			showString+=year;
		}	
				
		Set mmEnvStudies=mmSample.getMmExperimentStudies();
		out.write("<li>"+showString+" ("+mmEnvStudies.size()+" experimental studies found)</li>");	
		out.write("<ul>");
		
		for(Iterator it1=mmEnvStudies.iterator();it1.hasNext();)
		{
			MmExperimentStudy mmEnvStudy=(MmExperimentStudy)it1.next();
			String studyName=mmEnvStudy.getName();
			MmReferencePart refPart=mmEnvStudy.getMmReferencePart();
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
			
			String str=species.getGenus()+" "+species.getSpecies();
			if(studyName!=null)
				str+=", "+studyName;
			
			
			
			if(cite!=null && ! cite.trim().equals(""))
			{
				str+="("+cite+")";
			}		
			
			if(str=="")
				str="Study "+ mmEnvStudy.getExperimentStudyOid();
				
			out.write("<li><a href='envstudy.go?id=" + mmEnvStudy.getExperimentStudyOid()+"'>"+ str+" </a></li>");
		}
		out.write("</ul>");
		
	}
	out.write("</ul>");
	
}	

%>


    
    