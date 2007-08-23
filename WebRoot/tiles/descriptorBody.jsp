<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<%@ page import="java.util.*" %>
<%@ page import="org.nescent.mmdb.hibernate.dao.*" %>
<%@ page import="org.nescent.mmdb.util.NoCache" %>


<h3>Species Descriptor</h3>
<form action="savedescriptor.go" method="post" name="Edit_Descriptor_Foem">
<%

NoCache.nocache(response);
MmMatingSystemStudy study=(MmMatingSystemStudy)request.getAttribute("descriptor");
if(study==null)
	out.write("No species descriptor specified.");
else
{
	out.write("<input type='hidden' name='id' value='"+study.getMatingSystemStudyOid()+"' />");		
	MmSpecies mmSpecies=study.getMmSpecies();
	
	String str="<table width='90%'><tr><td class='TdField'>Species<td class='TdValue'>"+mmSpecies.getGenus()+" "+mmSpecies.getSpecies()+" ("+mmSpecies.getFamily()+")</td></tr>";
	str+="<tr><td class='TdField'>Latitude<td class='TdValue'>"+study.getLatitude()+"</td></tr>";
	MmReferencePart part=study.getMmReferencePart();
	MmReference ref=part.getMmReference();
	str+="<tr><td class='TdField'>Reference<td class='TdValue'>"+part.getName()+" <i>in</i> "+ref.getFullReference()+"</td></tr>";
	str+="</table>";
	out.write(str);	
%>
<br/><br/>
<h4>Attributes</h4>
	<table width=90%>
	<tr>
		<th>Attribute</th>
		<th>Value</th>
		<th>Value Type</th>
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
	/*
	int i=0;
	for(Iterator it=mmCvTermsAssocs.iterator();it.hasNext();)
	{
		i++;
		MmSpeciesAttrCvtermAssoc cvAssoc=(MmSpeciesAttrCvtermAssoc)it.next();
	
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
			tr+="<td><span id="+cvAssoc.getMsacaOid()+" onclick='valueClicked("+cvAssoc.getMsacaOid()+")'>"+value+"</span></td>";
			tr+="<td>"+type+"</td>";
			tr+="<td>"+desc+"</td>";
			tr+="</tr>";
			
			out.write(tr);	
	}
	*/
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
	for(i=0;i<keys.size();i++) //Iterator it=terms.iterator();it.hasNext();)
	{
//		i++;
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
			tr+="<td><span id="+cvAssoc.getMsacaOid()+" onmouseout='onValueMouseOut("+cvAssoc.getMsacaOid()+")' onmouseover='onValueMouseOver("+cvAssoc.getMsacaOid()+")' onclick='onValueClicked("+cvAssoc.getMsacaOid()+",\""+attr+"\")'>"+value+"</span></td>";
			tr+="<td>"+type+"</td>";
			tr+="<td>"+desc+"</td>";
			tr+="</tr>";
			
			out.write(tr);	
		}
		
		
		
		
	}
	
	
%>
	</table>
<%
}
%>
<br/>
<input type="submit" value="save"/>
</form>

