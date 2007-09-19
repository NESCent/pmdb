<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>
<%@ page import="org.nescent.mmdb.hibernate.dao.*" %>
<%@ page import="org.nescent.mmdb.util.NoCache" %>
<%@ page import="org.nescent.mmdb.util.Fields" %>
<%@ page import="org.hibernate.Query" %>
<%@ page import="org.hibernate.Session" %>
<%@ page import="org.nescent.mmdb.hibernate.HibernateSessionFactory" %>

<h3>Species Descriptor</h3>

<%
NoCache.nocache(response);

Session sess=HibernateSessionFactory.getSession();
String sql="FROM MmCvTerm term where term.namespace='species_attribute'";
Query q=sess.createQuery(sql);
List cvterms=q.list();
if(cvterms!=null)
{
	out.write("<script language='javascript'>");
	out.write("var attrs=new Array();\n");
	out.write("var attrs_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		if(term.getNamespace().equals("species_attribute"))
		{
			String s="attrs["+i+"]='"+term.getName()+"';";
			out.write(s);
			s="attrs_desc["+i+"]='"+term.getDescription()+"';";
			out.write(s);
		}
	}
	
	out.write("</script>");
}
sess.close();
MmMatingSystemStudy study=(MmMatingSystemStudy)request.getAttribute("descriptor");
if(study==null)
	out.write("No species descriptor specified.");
else
{
	
	//add cancel function
	out.write("<script language ='javascript'>\n");
	out.write("function cancelEditing()\n");
	out.write("{\n");
	out.write("    var url='descriptor.go?id="+study.getMatingSystemStudyOid()+"';\n");
	out.write("	   document.location=url;\n");
	out.write("}\n");
	out.write("</script>\n");
	
		
	MmSpecies mmSpecies=study.getMmSpecies();
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
			<td valign="top" width=30%><table>
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
			<tr>
				<td class='TdField'>Latitude</td>
				<td class='TdValue'>
					<span  class='editableValue' id=<%= latitudeid %> 
						onmouseout='onValueMouseOut(<%= latitudeid %>)' 
						onmouseover='onValueMouseOver(<%= latitudeid %>)' 
						onclick='onValueClicked(<%= latitudeid %>,"Latitude")'><%= 	study.getLatitude() %></span>
				</td>
			</tr>		
			</table></td>
			<td valign="top"><table>
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
			</td>
		</table>
<h4>Attributes</h4>
	<table>
	<tr>
		<th>Attribute</th>
		<th>Value</th>
		<th>Attribute Description</th>
	</tr>
<%
	String attr="";
	String value="";
	String type="";
	String desc="";
	Integer cvid=null;
	String trclass="TrOdd";
	Set mmCvTermsAssocs=study.getMmSpeciesAttrCvtermAssocs();
	
	Hashtable terms=new Hashtable();
	for(Iterator it=mmCvTermsAssocs.iterator();it.hasNext();)
	{
		MmSpeciesAttrCvtermAssoc cvAssoc=(MmSpeciesAttrCvtermAssoc)it.next();
		
		
		String key=cvAssoc.getMmCvTerm().getName();
		ArrayList values=new ArrayList();
		if(terms.containsKey(key))
			values=(ArrayList)terms.get(key);
		else
		{
			terms.put(key, values);
		}
		
		values.add(cvAssoc);
	}
	
	List keys=new ArrayList(terms.keySet());
	
	Collections.sort(keys);
	
	int i=0;	
	for(i=0;i<keys.size();i++)
	{
		String key=(String)keys.get(i);
		ArrayList values=(ArrayList)terms.get(key);
		
		for(int j=0;j<values.size();j++)
		{
			MmSpeciesAttrCvtermAssoc cvAssoc=(MmSpeciesAttrCvtermAssoc)values.get(j);
			value=cvAssoc.getValue();
			
			MmCvTerm term=cvAssoc.getMmCvTerm();
			cvid=term.getCvtermOid();
			desc=term.getDescription();
			attr=term.getName();
			type=term.getValueType();
			
			if(i%2==0)
				trclass="TrEven";
			else
				trclass="TrOdd";
	
			String tr="<tr class='"+trclass+"'>";
							
			tr+="<td>"+attr+"</td>";
			tr+="<td><span  class='editableValue' id="+cvAssoc.getMsacaOid()+" onmouseout='onValueMouseOut("+cvAssoc.getMsacaOid()+")' onmouseover='onValueMouseOver("+cvAssoc.getMsacaOid()+")' onclick='onValueClicked("+cvAssoc.getMsacaOid()+",\""+attr+"\")'>"+value+"</span></td>";
			tr+="<td>"+desc+"</td>";
			tr+="</tr>";
			
			
			out.write(tr);	
		}
	}
	//add new attr
	String newTr="<tr class='TrNew'>";
	String newFid=Fields.getFieldId("NewDescriptorAttribute");
	String newVid=Fields.getFieldId("NewDescriptorValue");
	String newDid=Fields.getFieldId("NewDescriptorDesc");
	newTr+="<td><span id="+newFid+" onmouseout='onFieldMouseOut("+newFid+")' onmouseover='onFieldMouseOver("+newFid+")' onclick='onFieldClicked("+newFid+",\"NewDescriptorAttribute\")'>(add new record)</span></td>";
	newTr+="<td><span id="+newVid+"></span></td>";
	newTr+="<td><span id="+newDid+"></span></td>";
	
	newTr+="</tr>";
	out.write(newTr);	
		
	%>
	
	</table>
<%
}
%>
<br/>

	<table>
		<tr>
			<td><input type="submit" value="save"/></td>
			<td class="TdAction"><a href="javascript:cancelEditing()">Cancel</a></td>
		</tr>
	</table>

</form>
Note: empty attributes will be deleted if you save the data.



