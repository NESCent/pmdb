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
sql="FROM MmCvTerm term where term.namespace='species_attribute'";
q=sess.createQuery(sql);
cvterms=q.list();
if(cvterms!=null)
{
	
	out.write("var attrs=new Array();\n");
	out.write("var attrs_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		if(term.getNamespace().equals("species_attribute"))
		{
			String s="attrs["+i+"]='"+term.getName()+"';\n";
			out.write(s);
			s="attrs_desc["+i+"]='"+term.getDescription()+"';\n";
			out.write(s);
		}
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


<%

String newFid="";
String newVid="";
String newDid="";
String tdclass="";
int count =0;
Set set=null;
MmPopulationSample sample=(MmPopulationSample)request.getAttribute("population");
if(sample==null)
	out.write("No population specified.");
else
{
	//add cancel function
	out.write("<script language ='javascript'>\n");
	out.write("function cancelEditing(tab)\n");
	out.write("{\n");
	out.write("    var url='population.go?id="+sample.getPopulationSampleOid()+"&tab='+tab;\n");
	out.write("	   document.location=url;\n");
	out.write("}\n");
	out.write("</script>\n");

	%>
		
	<!-- add tabs -->	
	<h2>Population: <%= sample.getPopulation() %></h2>

	<div class="TabView" id="TabView">
		<div class="Tabs">
  			<a>Species</a>
  			<a>Population</a>
  			<a>Studies</a>
   		</div>
  		<div class="Pages">	
 			<!-- species tab -->
 				<div class='Page'>
					<div class='Pad'>	
					<%
						MmSpecies mmSpecies=sample.getMmSpecies();
						MmMatingSystemStudy study=null;
						Set mmstudies=mmSpecies.getMmMatingSystemStudies();
						if(mmstudies.size()>0)
						{
							study=(MmMatingSystemStudy)mmstudies.toArray()[0];
						}
						String spart="";
						String scite="";
						String sfull="";
						
						MmReferencePart part=study.getMmReferencePart();
						
						if(part!=null)
						{
							if(part.getName()!=null && ! part.getName().trim().equals(""))
								spart=part.getName();
								
							MmReference ref=part.getMmReference();
							if(ref!=null)
							{
								if(ref.getFullReference()!=null && ! ref.getFullReference().trim().equals(""))
									sfull=ref.getFullReference();
								
								if(ref.getCitation()!=null && ! ref.getCitation().trim().equals(""))
									scite=ref.getCitation();
							}
						}	
						
						String familyid=Fields.getFieldId("Family");
						String genusid=Fields.getFieldId("Genus");
						String speciesid=Fields.getFieldId("Species");
						String latitudeid=Fields.getFieldId("Latitude");
						String citationid=Fields.getFieldId("Citation");
						String fullreferenceid=Fields.getFieldId("FullReference");
						String partid=Fields.getFieldId("Part");
						%>
							<form action='savedescriptor.go' method='post' name='Edit_Descriptor_Form'>
							<input type='hidden' name='id' value='<%= study.getMatingSystemStudyOid() %>' />	
							<table>
								<tr>
									<td class='TdField'>Family</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= familyid %> 
											onmouseout='onValueMouseOut(<%= familyid %>)' 
											onmouseover='onValueMouseOver(<%= familyid %>)' 
											onclick='onValueClicked(<%= familyid %>,"Family")'><%= 	mmSpecies.getFamily() %></span>
									</td>
								</tr>	
								<tr>
									<td class='TdField'>Genus</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= genusid %> 
											onmouseout='onValueMouseOut(<%= genusid %>)' 
											onmouseover='onValueMouseOver(<%= genusid %>)' 
											onclick='onValueClicked(<%= genusid %>,"Genus")'><%= 	mmSpecies.getGenus() %></span>
									</td>
								</tr>	
								<tr>
									<td class='TdField'>Species</td>
									<td class='TdValue'>
										<span  class='editableValue' id=<%= speciesid %> 
											onmouseout='onValueMouseOut(<%= speciesid %>)' 
											onmouseover='onValueMouseOver(<%= speciesid %>)' 
											onclick='onValueClicked(<%= speciesid %>,"Species")'><%= 	mmSpecies.getSpecies() %></span>
									</td>
								</tr>	
							</table>
					
						<br/>
					
						
					
					</form>
				
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
		str+="<input type='hidden' name='id' value='" + sample.getPopulationSampleOid()+"' />";
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
	
		
		str+="</form>";
//		str+="<p>Note: empty attributes will be deleted if you save the data.</p>";
	
		str+="</div></div>";
		out.write(str);
		
	}
	
%>
<!-- study page -->
 	<div class="Page">
		  <div class="Pad">			
		  <%
		 
		  Set exps=sample.getMmExperimentStudies();
		  for(Iterator it=exps.iterator();it.hasNext();)
		  {
		  	
		  	MmExperimentStudy exp=(MmExperimentStudy)it.next();
		  	String expName=exp.getName();
		  	MmCvTerm expTypeTerm=exp.getMmCvTerm();
		  	String expType=expTypeTerm.getName();
		  	String expRefPart="";
		  	String expRefCite="";
		  	String expRefFull="";
		  	
		  	MmReferencePart expPart=exp.getMmReferencePart();
		  	if(expPart!=null)
		  	{
		  		expRefPart=expPart.getName();
		  		MmReference expRef=expPart.getMmReference();
		  		if(expRef!=null)
		  		{
		  			expRefCite=expRef.getCitation();
		  			expRefFull=expRef.getFullReference();
		  			
		  		}
		  	}
		  	expRefFull=(expRefFull==null)?"":expRefFull;
		  	%>
		  	
		  		<table>
		  		<tr><th colspan=2>Study: <%= expName %></th></tr>
		  		<tr><td class="TdField">Type</td><td class="TdValue"><%= expType %></td></tr>
		  		<tr><td class="TdField">Citation</td><td class="TdValue"><%= expRefCite %></td></tr>
		  		<tr><td class="TdField">Full Reference</td><td class="TdValue"><%= expRefFull %></td></tr>
		  		<tr><td class="TdField">Part</td><td class="TdValue"><%= expRefPart %></td></tr>
		  		
		  		
		  		
		  			
		  <%
		  		//get attributes and mearsurements
		  		
		  		
		  		Set attrs=exp.getMmExperimentStudyAttrCvtermAssocs();
		  		if(attrs.size()>0)
		  		{
		  			out.write("<tr><td class='TdField'>Attributes</td><td class='TdValue'>");
		  			
		  			out.write("<table>");
			  		for(Iterator it1=attrs.iterator();it1.hasNext();)
					{
					  	
					  	MmExperimentStudyAttrCvtermAssoc assoc=(MmExperimentStudyAttrCvtermAssoc)it1.next();
					  	MmCvTerm t=assoc.getMmCvTerm();
					  	String tr="<tr><td class='TdField'>"+
					  					t.getName() + "</td><td class='TdValue'>" +
					  					assoc.getValue()+"</td></tr>";
					  	out.write(tr);
					  }
					 out.write("</table></td></tr>");
				}
				
				
			
		  		Set values=exp.getMmExperimentValues();
		  		if(values.size()>0)
		  		{
		  			out.write("<tr><td class='TdField'>Measurements</td><td class='TdValue'>");
		  			
		  			out.write("<table>");
			  		for(Iterator it1=values.iterator();it1.hasNext();)
					{
					  	
					  	MmExperimentValue assoc=(MmExperimentValue)it1.next();
					  	MmCvTerm t=assoc.getMmCvTerm();
					  	String tr="<tr><td class='TdField'>"+
					  					t.getName() + "</td><td class='TdValue'>" +
					  					assoc.getValue()+"</td></tr>";
					  	out.write(tr);
					  }
					 out.write("</table></td></tr>");  
				}
		  		out.write("</table>");
		  }
		  
		  
		  %>	
		  	
		  		
		</div></div>
	</div></div>
<%
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





