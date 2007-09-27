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

//created term array for inbreed attributes
String sql="FROM MmCvTerm term where term.namespace = 'inbreed_exp_attribute'";
Session sess=HibernateSessionFactory.getSession();
Query q=sess.createQuery(sql);
List cvterms=q.list();
out.write("<script language='javascript'>");
if(cvterms!=null)
{
	out.write("var inbreed_pop_attributes=new Array();\n");
	out.write("var inbreed_pop_attributes_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="inbreed_pop_attributes["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="inbreed_pop_attributes_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
}

//created term array for environmental study  attributes
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

//created term array for species attributes
sql="FROM MmCvTerm term where term.namespace = 'species_attribute'";
q=sess.createQuery(sql);
cvterms=q.list();
if(cvterms!=null)
{
	out.write("var species_attributes=new Array();\n");
	out.write("var species_attributes_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="species_attributes["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="species_attributes_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
}
//created term array for study types
sql="FROM MmCvTerm term where term.namespace = 'study type ontology'";
q=sess.createQuery(sql);
cvterms=q.list();
if(cvterms!=null)
{
	out.write("var study_types=new Array();\n");
	out.write("var study_types_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="study_types["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="study_types_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
}
//created term array for outcrossing attributes
sql="FROM MmCvTerm term where term.namespace = 'outcrossing_attribute'";
q=sess.createQuery(sql);
cvterms=q.list();
if(cvterms!=null)
{
	out.write("var outcrossing_attributes=new Array();\n");
	out.write("var outcrossing_attributes_desc=new Array();\n");
	for(int i=0;i<cvterms.size();i++)
	{
		MmCvTerm term=(MmCvTerm)cvterms.get(i);
		String s="outcrossing_attributes["+i+"]=\""+term.getName()+"\";\n";
		out.write(s);
		s="outcrossing_attributes_desc["+i+"]=\""+term.getDescription()+"\";\n";
		out.write(s);
	}
}
out.write("</script>");
sess.close();
%>
<script language="javascript">
function studyTypeChanged()
{
	var study=document.Search_Data_Form.study_type;
	var study_value=study.value;
	var attr=document.Search_Data_Form.measurement_attribute;
	attr.options[0]=new Option("","");
	
	attr.options.length=1;
	attr.options[0]=new Option("","");
	if(study_value.indexOf("outcross")!=-1)
	{
		for(var i=0;i<outcrossing_attributes.length;i++)
		{
			attr.options[i+1]=new Option(outcrossing_attributes[i],outcrossing_attributes[i]);
		}
	}
	if(study_value.indexOf("inbreed")!=-1)
	{
		for(var i=0;i<inbreed_pop_attributes.length;i++)
		{
			attr.options[i+1]=new Option(inbreed_pop_attributes[i],inbreed_pop_attributes[i]);
		}
	}
	if(study_value.indexOf("pollination")!=-1)
	{
		for(var i=0;i<pollination_attributes.length;i++)
		{
			attr.options[i+1]=new Option(pollination_attributes[i],pollination_attributes[i]);
		}
	}
	

}
</script>
<h2>Search Data</h2>
<h3>Taxon Name</h3>
<form action="searchData.go" method="post" name="Search_Data_Form">
<table>
<tr>
	<td class="TdField">Family</td>
	<td class="TdValue"><input name="family" />
</tr>
<tr>	
	<td class="TdField">Genus</td>
	<td class="TdValue"><input name="genus" />
</tr>
<tr>
	<td class="TdField">Species</td>
	<td class="TdValue"><input name="species" />
</tr>
</table>
<!-- by attributes -->
<!--
<h3>Population</h3>
<table>
<tr>
	<td class="TdField ">Name</td>
	<td class="TdValue"><input name="populationName" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Geographic Location</td>
	<td class="TdValue"><input name="geographicLocation" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Environment</td>
	<td class="TdValue"><input name="environment" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Population</td>
	<td class="TdValue"><input name="population" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Year</td>
	<td class="TdValue"><input name="year" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Comments</td>
	<td class="TdValue"><input name="comments" type="text" size="50"/></td>
</tr>	
<tr>	
</table>
<h3>Reference</h3>

<table>
<tr>
	<td class="TdField ">Citation</td>
	<td class="TdValue"><input name="citation" type="text" size="50"/></td>
</tr>	
<tr>
	<td class="TdField">Full Reference</td>
	<td class="TdValue"><textarea name="fullreference" cols="50" rows="3"></textarea></td>
</tr>	
<tr>
	<td class="TdField">Part</td>
	<td class="TdValue"><input name="part" type="text"  size="50"/></td>
</tr>	
</table>
-->
<h3>Species Attributes</h3>
<table>
<tr>
	<th>Attribute</th>
	<th>Operator</th>
	<th>Value</th>
</tr>
<tr class="TrOdd">
	<td>
		<select name=species_attribute>
			<option value=""></option>
			<script language="javascript">
				for(var i=0;i<species_attributes.length;i++)
				{
					document.write("<option value='"+species_attributes[i]+"'>"+species_attributes[i]+"</option>");
				}
			</script>
		</select>
	</td>
	<td>
		<select name=species_attribute_opt>
			<option value="=">=</option>
			<option value="!=">!=</option>
			<option value="like">like</option>
		</select>
	</td>
	<td>
		<input name="species_attribute_value" />
	</td>
</tr>
</table>
<h3>Measurements</h3>
<table>
<tr>
	<th>Study</th>
	<th>Attribute</th>
	<th>Operator</th>
	<th>Value</th>
</tr>
<tr class="TrOdd">
	<td>
		<select name="study_type" onchange="studyTypeChanged();">
			<option value=""></option>
			<script language="javascript">
				for(var i=0;i<study_types.length-1;i++)
				{
					document.write("<option value='"+study_types[i]+"'>"+study_types[i]+"</option>");
				}
			</script>
		</select>
	</td>
	<td>
		<select name=measurement_attribute>
						
		</select>
	</td>
	<td>
		<select name=measurement_attribute_opt>
			<option value="=">=</option>
			<option value="!=">!=</option>
			<option value="like">like</option>
		</select>
	</td>
	<td>
		<input name="measurement_attribute_value" />
	</td>
</tr>
</table>
<table>
<tr>
	<td><input type="submit" value="Search"/></td>
</tr>	
</table>
</form>