<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>
<%@ page import="org.nescent.mmdb.hibernate.dao.*" %>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<%@ page import="org.nescent.mmdb.util.Fields" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.nescent.mmdb.hibernate.HibernateSessionFactory" %>
<%
NoCache.nocache(response);
//created term array for population sample attributes
String sql="FROM MmCvTerm term where term.namespace = 'inbreed_pop_attribute'";
Session sess=HibernateSessionFactory.getSession();
Query q=sess.createQuery(sql);
List cvterms=q.list();
if(cvterms!=null)
{
	out.write("<script language='javascript'>");
	out.write("var pop_sample_attributes=new Array();");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="pop_sample_attributes[pop_sample_attributes.length]='"+term.getName()+"';";
		out.write(s);
	}
	
	out.write("</script>");
}
sess.close();
%>
<h2>Environmental Study</h2>

<%


MmExperimentStudy study=(MmExperimentStudy)request.getAttribute("envstudy");
if(study==null)
	out.write("No environmental study specified.");
else
{
	String part="";
	String full="";
	String cite="";
	MmReferencePart refPart=study.getMmReferencePart();
	if(refPart!=null)
	{
		part=refPart.getName();
		MmReference ref=refPart.getMmReference();
		if(ref!=null)
		{
			cite=ref.getCitation();
		}
	}
	String fa="";
	String ge="";
	String sp="";
		
	MmPopulationSample sample=study.getMmPopulationSample();
	if(sample!=null)
	{
		MmSpecies species=sample.getMmSpecies();
		if(species.getFamily()!=null)
			fa=species.getFamily();
		if(	species.getGenus()!=null)
			ge=species.getGenus();
		if(species.getSpecies()!=null)
			sp=species.getSpecies();
	}
	String str="<table><tr><td class='TdField'>Species<td class='TdValue'>"+ge+" "+sp+" ("+fa+")</td></tr>";
	str+="<tr><td class='TdField'>Reference<td class='TdValue'>"+cite+"("+part+")</td></tr>";
	str+="</table>";
	out.write(str);	
%>	
	<br/><br/>
	<!-- add tabs -->	
	
	<div class="TabView" id="TabView">
		<div class="Tabs">
  			<a>Study</a>
  			<a>Population</a>
  			<a>Statistics</a>
  		</div>
  		<div class="Pages">
  			<!-- study page -->
  			<div class="Page">
		  		<div class="Pad">			
		  			<Form name="View_EnvStudy_Form" method="post" action="saveenvstudy.go">	
		  				<input type='hidden' name='id' value='<%= study.getExperimentStudyOid() %>' />
							<table>
	
<%	
	//write study

	MmDevelopmentalStage stage=study.getMmDevelopmentalStage();
	
	String fid=Fields.getFieldId("DevelopStage");
	
	str="<tr><td class='TdField'>Development Stage<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"DevelopStatge\")'>"+
			stage.getName()+"</span></td></tr>";
	str+="<tr><td class='TdField'>Attributes</td><td>";
	str+="<table>";
	str+="<tr><th>Attribute</th><th>Value</th><th>Description</th></tr>";
	String tdclass="TdOdd";
	int count=0;
		
	Set set= study.getMmExperimentValues();
	for(Iterator it=set.iterator();it.hasNext();)
	{
		if(count%2==0)
			tdclass="TrEven";
		else
			tdclass="TrOdd";
		count++;
			
		MmExperimentValue value=(MmExperimentValue)it.next();
		
		MmCvTerm term=value.getMmCvTerm();
		Integer cvid=term.getCvtermOid();
		String desc=term.getDescription();
		String attr=term.getName();
		Integer vid=value.getExperimentValueOid();
		
		str+="<tr class='"+tdclass+"'><td>"+attr+"</td><td>"+
			"<span  class='editableValue' id="+vid+" onmouseout='onValueMouseOut("+vid+")' onmouseover='onValueMouseOver("+vid+")' onclick='onValueClicked("+vid+",\""+attr+"\")'>"+
			value.getValue()+"</span></td><td>"+term.getDescription()+"</td></tr>";
			
			
		
	}
	str+="<tr class='TrNew'>";
	String newFid=Fields.getFieldId("NewEnvironmentStudyAttribute");
	String newVid=Fields.getFieldId("NewEnvironmentStudyValue");
	str+="<td><span id="+newFid+" onmouseout='onFieldMouseOut("+newFid+")' onmouseover='onFieldMouseOver("+newFid+")' onclick='onFieldClicked("+newFid+",\"NewEnvironmentStudyAttribute\")'>(add new attribute)</span></td>";
	str+="<td><span id="+newVid+"></span></td>";
	str+="<td></td>";
	str+="</tr>";
	str+="</table>";
	str+="</td></tr></table>";
	out.write(str);	
	

%>	
	<br/><br/>
	<input type="submit" value="save"/>
	</form>
	<p>Note: empty attributes will be deleted if you save the data.</p>
	
	</div>
	</div>
	
<%
							

	//write population sample
	
			
			
	if(sample!=null)
	{
		
  		str="<div class='Page'>";
		str+="<div class='Pad'>";	
		str+="<Form name='View_Population_Form' method='post' action='savepopulation.go'>";
		str+="<table>";
		
		MmSpecies species=sample.getMmSpecies();
					
		String comments=sample.getComments()!=null?sample.getComments():"";
		comments=comments.trim().equals("")?"&nbsp;":comments;
		String env=sample.getEnvironment()!=null?sample.getEnvironment():"";
		env=env.trim().equals("")?"&nbsp;":env;
		String loc=sample.getGeographicLocation()!=null?sample.getGeographicLocation():"";
		loc=loc.trim().equals("")?"&nbsp;":loc;
		String name=sample.getName()!=null?sample.getName():"";
		name=name.trim().equals("")?"&nbsp;":name;
		String popu=sample.getPopulation()!=null?sample.getPopulation():"";
		popu=popu.trim().equals("")?"&nbsp;":popu;
		String year=sample.getYear()!=null?sample.getYear():"";
		year=year.trim().equals("")?"&nbsp;":year;
		Integer sampleId=sample.getPopulationSampleOid()!=null?sample.getPopulationSampleOid():Integer.valueOf(-1);
		
		fid=Fields.getFieldId("Genus");
		str+="<tr><td class='TdField'>Species<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"Genus\")'>"+
			ge+"</span></td></tr>";
		fid=Fields.getFieldId("Species");	
		str+="<tr><td class='TdField'>Genus<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"Species\")'>"+
			sp+"</span></td></tr>";

		fid=Fields.getFieldId("Family");	
		str+="<tr><td class='TdField'>Family<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"Family\")'>"+
			fa+"</span></td></tr>";
		
		fid=Fields.getFieldId("Environment");	
		str+="<tr><td class='TdField'>Environment<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"Environment\")'>"+
			env+"</span></td></tr>";	
		
		fid=Fields.getFieldId("GeoLocation");	
		str+="<tr><td class='TdField'>GeoLocation<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"GeoLocation\")'>"+
			loc+"</span></td></tr>";	
			
		fid=Fields.getFieldId("PopulationName");	
		str+="<tr><td class='TdField'>Name<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"PopulationName\")'>"+
			name+"</span></td></tr>";	
			
		fid=Fields.getFieldId("Population");	
		str+="<tr><td class='TdField'>Population<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"Population\")'>"+
			popu+"</span></td></tr>";			
		
		fid=Fields.getFieldId("PopulationYear");	
		str+="<tr><td class='TdField'>Year<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"PopulationYear\")'>"+
			year+"</span></td></tr>";	
			
		fid=Fields.getFieldId("PopulationComments");	
		str+="<tr><td class='TdField'>Comments<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"PopulationComments\")'>"+
			comments+"</span></td></tr>";
		
	
		str+="<tr><td class='TdField'>Attributes</td><td>";
		
		str+="<table>";
		str+="<tr><th>Attribute</th><th>Value</th><th>Description</th></tr>";
		tdclass="TdOdd";
		count=0;
		set =sample.getMmPopSampleAttrCvtermAssocs();
		for(Iterator it=set.iterator();it.hasNext();)
		{
			if(count%2==0)
				tdclass="TrEven";
			else
				tdclass="TrOdd";
			count++;
			MmPopSampleAttrCvtermAssoc cvAssoc=(MmPopSampleAttrCvtermAssoc)it.next();
			Integer vid=cvAssoc.getMpsacaOid();
			MmCvTerm term=cvAssoc.getMmCvTerm();
			String tname=term.getName()!=null?term.getName():" ";
			String description=term.getDescription()!=null?term.getDescription():" ";
			String v=cvAssoc.getValue()!=null?cvAssoc.getValue():"&nbsp;&nbsp;";
			str+="<tr class='"+tdclass+"'><td>"+tname+"</td><td>"+
			"<span class='editableValue' id="+vid+" onmouseout='onValueMouseOut("+vid+")' onmouseover='onValueMouseOver("+vid+")' onclick='onValueClicked("+vid+",\""+tname+"\")'>"+
			v+"</span></td><td>"+description+"</td></tr>";
		}
		
		str+="<tr class='TrNew'>";
		newFid=Fields.getFieldId("NewPopulationSampleAttribute");
		newVid=Fields.getFieldId("NewPopulationSampleValue");
		str+="<td><span id="+newFid+" onmouseout='onFieldMouseOut("+newFid+")' onmouseover='onFieldMouseOver("+newFid+")' onclick='onFieldClicked("+newFid+",\"NewPopulationSampleAttribute\")'>(add new attribute)</span></td>";
		str+="<td><span id="+newVid+"></span></td>";
		str+="<td></td>";
		str+="</tr>";
		str+="</table>";
		str+="</td></tr></table>";
		str+="<br/><br/>";
		str+="<input type='submit' value='save'/>";
		str+="</form>";
		str+="<p>Note: empty attributes will be deleted if you save the data.</p>";
	
		str+="</div></div>";
		out.write(str);
		
	}
%>
		<div class="Page">
			<div class="Pad">			
<%	
	
	//write data records
	
	
	
	out.write("<table >");
	
	str="<tr><th>Name</th>";
	str+="<th>Type</th>";
	str+="<th>OutCrossing Std. Dev.</th>";
	str+="<th>OutCrossing Value</th>";
	str+="<th>Selfing Std. Dev.</th>";
	str+="<th>Selfing Value</th><tr>";
	
	out.write(str);
	count=0;
	tdclass="TrOdd";
	set= study.getMmDataRecords();
	for(Iterator it=set.iterator();it.hasNext();)
	{
		if(count%2==0)
				tdclass="TrEven";
			else
				tdclass="TrOdd";
		count++;
		MmDataRecord record=(MmDataRecord)it.next();
		String rname=record.getName()!=null?record.getName():"";
		String outsd=record.getOutCrossingStdDev()!=null?record.getOutCrossingStdDev().toString():"";
		String outv=record.getOutCrossingValue()!=null?record.getOutCrossingValue().toString():"";
		String selfsd=record.getSelfingStdDev()!=null?record.getSelfingStdDev().toString():"";
		String selfv=record.getSelfingValue()!=null?record.getSelfingValue().toString():"";
		String type=record.getType()!=null?record.getType():"";
		str="<tr class='"+tdclass+"'><td>"+rname+"</td>";
		str+="<td>"+type+"</td>";
		str+="<td>"+outsd+"</td>";
		str+="<td>"+outv+"</td>";
		str+="<td>"+selfsd+"</td>";
		str+="<td>"+selfv+"</td></tr>";
		out.write(str);
	}
	out.write("</table></div></div>");
	out.write("</div></div>");
}
%>

<script type="text/javascript">
	tabview_initialize('TabView');
</script>	





