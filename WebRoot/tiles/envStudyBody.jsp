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
String tab = (String)request.getAttribute("tab");
String message=(String)request.getSession().getAttribute("Message");
	

//created term array for population sample attributes

String sql="FROM MmCvTerm term where term.namespace = 'exp_pop_attribute'";
Session sess=HibernateSessionFactory.getSession();
Query q=sess.createQuery(sql);
List cvterms=q.list();
out.write("<script language='javascript'>");
if(cvterms!=null)
{
	out.write("var pop_sample_attributes=new Array();\n");
	out.write("var pop_sample_attributes_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="pop_sample_attributes["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="pop_sample_attributes_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
}

//created term array for inbreeding study attributes
sql="FROM MmCvTerm term where term.namespace = 'inbreed_exp_attribute'";
q=sess.createQuery(sql);
cvterms=q.list();
out.write("<script language='javascript'>");
if(cvterms!=null)
{
	out.write("var inbreeding_attributes=new Array();\n");
	out.write("var inbreeding_attributes_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="inbreeding_attributes["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="inbreeding_attributes_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
}

//created term array for pollination study  attributes
sql="FROM MmCvTerm term where term.namespace = 'pollination_attribute'";
q=sess.createQuery(sql);
cvterms=q.list();
if(cvterms!=null)
{
	out.write("var pollination_attributes=new Array();\n");
	out.write("var pollination_attributes_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="pollination_attributes["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="pollination_attributes_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
	
	
}

out.write("</script>");
sess.close();
%>
<script language="javascript">
function deletedatarecord(id)
{
	if(confirm("Do you really want to delete this data record?"))
	{
		var url="deletedatarecord.go?id=" + id;
		document.location=url;
	}
}
</script> 

<h2>Experimental Study</h2>

<%

String newFid="";
String newVid="";
String newDid="";
String tdclass="";
int count =0;
Set set=null;
MmExperimentStudy study=(MmExperimentStudy)request.getAttribute("envstudy");
if(study==null)
	out.write("No study specified.");
else
{
	//add cancel function
	out.write("<script language ='javascript'>\n");
	out.write("function cancelEditing(tab)\n");
	out.write("{\n");
	out.write("    var url='envstudy.go?id="+study.getExperimentStudyOid()+"&tab='+tab;\n");
	out.write("	   document.location=url;\n");
	out.write("}\n");
	out.write("</script>\n");
	
	String spart="";
	String sfull="";
	String scite="";
	MmReferencePart refPart=study.getMmReferencePart();
	if(refPart!=null)
	{
		spart=(refPart.getName()!=null)?refPart.getName():"";
		MmReference ref=refPart.getMmReference();
		if(ref!=null)
		{
			scite=(ref.getCitation()!=null)?ref.getCitation():"";
			sfull=(ref.getFullReference()!=null)?ref.getFullReference():"";
		}
	}
	String fa="";
	String ge="";
	String sp="";
		
	MmPopulationSample sample=study.getMmPopulationSample();
	if(sample!=null)
	{
		MmSpecies species=sample.getMmSpecies();
		if(species!=null)
		{
			if(species.getFamily()!=null)
				fa=species.getFamily();
			if(	species.getGenus()!=null)
				ge=species.getGenus();
			if(species.getSpecies()!=null)
				sp=species.getSpecies();
		}
	}
	
	String familyid=Fields.getFieldId("Family");
	String genusid=Fields.getFieldId("Genus");
	String speciesid=Fields.getFieldId("Species");
	String citationid=Fields.getFieldId("Citation");
	String fullreferenceid=Fields.getFieldId("FullReference");
	String partid=Fields.getFieldId("Part");
	%>
		
	<!-- add tabs -->	
	
	<div class="TabView" id="TabView">
		<div class="Tabs">
  			<a>Species</a>
  			<a>Population</a>
  			<a>Measurement</a>
  			<a>Statistics</a>
  		</div>
  		<div class="Pages">	
 			<!-- species tab -->
 				<div class='Page'>
					<div class='Pad'>	
						<form action='saveexperimental.go' method='post' name='Edit_ExperimentalStudy_Form'>
							<input type='hidden' name='id' value='<%= study.getExperimentStudyOid() %>' />	
							<table>
								<tr>
									<td class='TdField'>Family</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= familyid %> 
											onmouseout='onValueMouseOut(<%= familyid %>)' 
											onmouseover='onValueMouseOver(<%= familyid %>)' 
											onclick='onValueClicked(<%= familyid %>,"Family")'><%= fa %></span>
									</td>
								</tr>	
								<tr>
									<td class='TdField'>Genus</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= genusid %> 
											onmouseout='onValueMouseOut(<%= genusid %>)' 
											onmouseover='onValueMouseOver(<%= genusid %>)' 
											onclick='onValueClicked(<%= genusid %>,"Genus")'><%= ge %></span>
									</td>
								</tr>	
								<tr>
									<td class='TdField'>Species</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= speciesid %> 
											onmouseout='onValueMouseOut(<%= speciesid %>)' 
											onmouseover='onValueMouseOver(<%= speciesid %>)' 
											onclick='onValueClicked(<%= speciesid %>,"Species")'><%= sp %></span>
									</td>
								</tr>
								<tr>
									<td class='TdField'>Citation</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= citationid %> 
											onmouseout='onValueMouseOut(<%= citationid %>)' 
											onmouseover='onValueMouseOver(<%= citationid %>)' 
											onclick='onValueClicked(<%= citationid %>,"Citation")'><%= 	scite %></span>
									</td>
								</tr>	
								<tr>
									<td class='TdField'>Full Reference</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= fullreferenceid %> 
											onmouseout='onValueMouseOut(<%= fullreferenceid %>)' 
											onmouseover='onValueMouseOver(<%= fullreferenceid %>)' 
											onclick='onValueClicked(<%= fullreferenceid %>,"FullReference")'><%= sfull %></span>
									</td>
								</tr>	
								<tr>
									<td class='TdField'>Part</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= partid %> 
											onmouseout='onValueMouseOut(<%= partid %>)' 
											onmouseover='onValueMouseOver(<%= partid %>)' 
											onclick='onValueClicked(<%= partid %>,"Part")'><%= spart %></span>
									</td>
								</tr>	
							</table>
						</form>
						<br/><br/>
						
						<table>
							<tr>
								<td><input type="submit" value="save"/></td>
								<td class="TdAction"><a href="javascript:cancelEditing()">Cancel</a></td>
							</tr>
						</table>
						</div></div>
<%
							

	//write population sample
	
			
	String str="";	
	String fid="";	
	if(sample!=null)
	{
		
  		str="<div class='Page'>";
		str+="<div class='Pad'>";	
		str+="<Form name='View_Population_Form' method='post' action='savepopulation.go'>";
		str+="<input type='hidden' name='id' value='" + study.getExperimentStudyOid()+"' />";
		str+="<table>";
		MmSpecies species=sample.getMmSpecies();
		String comments=sample.getComments()!=null?sample.getComments():"";
		String loc=sample.getGeographicLocation()!=null?sample.getGeographicLocation():"";
		String popu=sample.getPopulation()!=null?sample.getPopulation():"";
		String year=sample.getYear()!=null?sample.getYear():"";
		Integer sampleId=sample.getPopulationSampleOid()!=null?sample.getPopulationSampleOid():Integer.valueOf(-1);

		fid=Fields.getFieldId("GeoLocation");	
		str+="<tr><td class='TdField'>GeoLocation<td class='TdValue'>"+
			"<span  class='editableValue' id="+fid+" onmouseout='onValueMouseOut("+fid+")' onmouseover='onValueMouseOver("+fid+")' onclick='onValueClicked("+fid+",\"GeoLocation\")'>"+
			loc+"</span></td></tr>";	
			
		
			
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
		
		str+="<table width='100%'>";
		str+="<tr><th>Attribute</th><th>Value</th><th>Description</th></tr>";
		tdclass="TdOdd";
		count=0;
		set=sample.getMmPopSampleAttrCvtermAssocs();
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
			
			str+="<tr class='"+tdclass+"'><td  class='TdEditableField'>"+tname+"</td><td class='TdEditable'>"+
			"<span class='editableValue' id="+vid+" onmouseout='onValueMouseOut("+vid+")' onmouseover='onValueMouseOver("+vid+")' onclick='onValueClicked("+vid+",\""+tname+"\")'>"+
			v+"</span></td><td >"+description+"</td></tr>";
		}
		
		str+="<tr class='TrNew'>";
		newFid=Fields.getFieldId("NewPopulationSampleAttribute");
		newVid=Fields.getFieldId("NewPopulationSampleValue");
		newDid=Fields.getFieldId("NewPopulationSampleDesc");
		str+="<td><span id="+newFid+" onmouseout='onFieldMouseOut("+newFid+")' onmouseover='onFieldMouseOver("+newFid+")' onclick='onFieldClicked("+newFid+",\"NewPopulationSampleAttribute\")'>(add new record)</span></td>";
		str+="<td><span id="+newVid+"></span></td>";
		str+="<td><span id="+newDid+"></span></td>";
		str+="</tr>";
		str+="</table>";
		str+="</td></tr></table>";
		str+="<br/><br/>";
		str+="<table>";
		str+="<tr>";
		str+="	<td><input type='submit' value='save'/></td>";
		str+="	<td class='TdAction'><a href='javascript:cancelEditing(\"population\")'>Cancel</a></td>";
		str+="</tr>";
		str+="</table>";
		
		str+="</form>";
		str+="<p>Note: empty attributes will be deleted if you save the data.</p>";
	
		str+="</div></div>";
		out.write(str);
		
	}
%>
<!-- measurements page -->
 	<div class="Page">
		  <div class="Pad">			
		  		<Form name="View_EnvStudyAttr_Form" method="post" action="saveenvstudyattr.go">	
		  				<input type='hidden' name='id' value='<%= study.getExperimentStudyOid() %>' />
							<table>
	
<%	
	//write study

	
	
	str="<tr><th>Attribute</th><th>Value</th><th>Description</th></tr>";
	tdclass="TdOdd";
	count=0;
		
	set= study.getMmExperimentValues();
	for(Iterator it=set.iterator();it.hasNext();)
	{
		if(count%2==0)
			tdclass="TrEven";
		else
			tdclass="TrOdd";
		count++;
			
		MmExperimentValue value=(MmExperimentValue)it.next();
		
		MmCvTerm term=value.getMmCvTerm();
		if(term!=null)
		{
			Integer cvid=term.getCvtermOid();
			String desc=term.getDescription();
			String attr=term.getName();
			Integer vid=value.getExperimentValueOid();
		
			str+="<tr class='"+tdclass+"'><td class='TdEditableField'>"+attr+"</td><td  class='TdEditable'>"+
				"<span  class='editableValue' id="+vid+" onmouseout='onValueMouseOut("+vid+")' onmouseover='onValueMouseOver("+vid+")' onclick='onValueClicked("+vid+",\""+attr+"\")'>"+
				value.getValue()+"</span></td><td>"+term.getDescription()+"</td></tr>";
		}
		else
			str+="<tr class='"+tdclass+"'><td colspan=2>no term found</td></tr>";
			
			
		
	}
	str+="<tr class='TrNew'>";
	newFid=Fields.getFieldId("NewEnvironmentStudyAttribute");
	newVid=Fields.getFieldId("NewEnvironmentStudyValue");
	newDid=Fields.getFieldId("NewEnvironmentStudyDesc");
	str+="<td><span id="+newFid+" onmouseout='onFieldMouseOut("+newFid+")' onmouseover='onFieldMouseOver("+newFid+")' onclick='onFieldClicked("+newFid+",\"NewEnvironmentStudyAttribute\")'>(add new record)</span></td>";
	str+="<td><span id="+newVid+"></span></td>";
	str+="<td><span id="+newDid+"></span></td>";
	str+="</tr>";
	str+="</table>";
	
	out.write(str);	
	

%>	
	<br/><br/>
	<table>
		<tr>
			<td><input type="submit" value="save"/></td>
			<td class="TdAction"><a href="javascript:cancelEditing('envattr')">Cancel</a></td>
		</tr>
	</table>
	
	</form>
	<p>Note: empty attributes will be deleted if you save the data.</p>
	
	</div>
	</div>
		<div class="Page">
			<div class="Pad">		
				<Form name="View_Records_Form" method="post" action="savedatarecord.go">	
		  				<input type='hidden' name='id' value='<%= study.getExperimentStudyOid() %>' />	
<%	
	
	//write data records
	
	
	
	out.write("<table>");
	
	str="<tr><th>Name</th>";
	str+="<th>Type</th>";
	str+="<th>OutCrossing Std. Dev.</th>";
	str+="<th>OutCrossing Value</th>";
	str+="<th>Selfing Std. Dev.</th>";
	str+="<th>Selfing Value</th>";
	str+="<th>Developmental Stage</th>";
	str+="<th>Action</th><tr>";
	
	out.write(str);
	count=0;
	tdclass="TrOdd";
	set= study.getMmDataRecords();
	String idstr="";
	for(Iterator it=set.iterator();it.hasNext();)
	{
		if(count%2==0)
			tdclass="TrEven";
		else
			tdclass="TrOdd";
		
		count++;
		
		MmDataRecord record=(MmDataRecord)it.next();
		
		String record_id="record_id_"+count;
		idstr+="<input type='hidden' name='"+record_id+"' value='" + record.getDataRecordOid()+"' />";
		
		String rname=record.getName()!=null?record.getName():"";
		String outsd=record.getOutCrossingStdDev()!=null?record.getOutCrossingStdDev().toString():"";
		String outv=record.getOutCrossingValue()!=null?record.getOutCrossingValue().toString():"";
		String selfsd=record.getSelfingStdDev()!=null?record.getSelfingStdDev().toString():"";
		String selfv=record.getSelfingValue()!=null?record.getSelfingValue().toString():"";
		String type=record.getType()!=null?record.getType():"";
		String stage="";
		MmCvTerm t=record.getMmCvTerm();
		if(t!=null)
		{
			stage = t.getName();
		}
		int ridbase=-10000*count;
		int rid=0;
		str="<tr class='"+tdclass+"'>";
		rid=--ridbase;
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"RecordName\")'>"+
			rname+"</span></td>";
		rid=--ridbase;
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"RecordType\")'>"+
			type+"</span></td>";	
		rid=--ridbase;	
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"OutcrossingSD\")'>"+
			outsd+"</span></td>";
		rid=--ridbase;	
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"OutcrossingValue\")'>"+
			outv+"</span></td>";
		rid=--ridbase;	
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"SelfingSD\")'>"+
			selfsd+"</span></td>";
		rid=--ridbase;	
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"SelfingValue\")'>"+
			selfv+"</span></td>";
		rid=--ridbase;	
		str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"DevelopStage\")'>"+
			stage+"</span></td>";
		str+="<td class='TdAction'><a href='javascript:deletedatarecord("+record.getDataRecordOid()+")'>Delete</a></td></tr>";
		out.write(str);
	}
	
	count++;
	int ridbase=-10000*count-1;
	int rid=0;
	str="<tr class='TrNew'>";
	rid=ridbase--;
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"RecordName\")'>(add new record)</span></td>";
	rid=ridbase--;
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"RecordType\")'></span></td>";	
	rid=ridbase--;	
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"OutcrossingSD\")'>"+
		""+"</span></td>";
	rid=ridbase--;	
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"OutcrossingValue\")'>"+
		""+"</span></td>";
	rid=ridbase--;	
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"SelfingSD\")'>"+
		""+"</span></td>";
	rid=ridbase--;	
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"SelfingValue\")'>"+
		""+"</span></td>";
	rid=ridbase--;	
	str+="<td><span class='editableValue' id="+rid+" onmouseout='onValueMouseOut("+rid+")' onmouseover='onValueMouseOver("+rid+")' onclick='onValueClicked("+rid+",\"DevelopStage\")'>"+
		""+"</span></td></tr>";	
	out.write(str);
	
	out.write("</table>");
	out.write(idstr);
	str="<br/><br/>";
	str+="<table>";
		str+="<tr>";
		str+="	<td><input type='submit' value='save'/></td>";
		str+="	<td class='TdAction'><a href='javascript:cancelEditing(\"staticstics\")'>Cancel</a></td>";
		str+="</tr>";
		str+="</table>";
	out.write(str);
	out.write("<form>");
	out.write("</div></div>");
	out.write("</div></div>");
}
%>

<script type="text/javascript">
<%
	if(tab==null)
		out.write("tabview_initialize('TabView')");
	else
	{
		if(tab.equals("population"))
			out.write("tabview_switch('TabView',2)");
		else if(tab.equals("envattr"))
			out.write("tabview_switch('TabView',3)");
		else if(tab.equals("staticstics"))
			out.write("tabview_switch('TabView',4)");	
		else
			out.write("tabview_initialize('TabView')");
	}
%>
</script>	





